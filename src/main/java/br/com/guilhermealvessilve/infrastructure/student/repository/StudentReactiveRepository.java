package br.com.guilhermealvessilve.infrastructure.student.repository;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import br.com.guilhermealvessilve.infrastructure.util.async.CompletableFutureUtils;
import br.com.guilhermealvessilve.infrastructure.util.db.TransactionContainer;
import br.com.guilhermealvessilve.infrastructure.util.db.TransactionManager;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Tuple;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static br.com.guilhermealvessilve.infrastructure.util.async.CompletableFutureUtils.successCompletedStage;

@ApplicationScoped
public class StudentReactiveRepository implements StudentRepository, TransactionManager {

    private static final Logger LOGGER = Logger.getLogger(StudentReactiveRepository.class);

    private final Pool pool;
    private final StudentDbConverter converter;

    @Inject
    public StudentReactiveRepository(final Pool pool, final StudentDbConverter converter) {
        this.pool = Objects.requireNonNull(pool, "StudentReactiveRepository.pool cannot be null");
        this.converter = Objects.requireNonNull(converter, "StudentReactiveRepository.converter cannot be null");;
    }

    @Override
    public CompletionStage<List<Student>> getAll() {

        return pool.query("SELECT s.*, p.* FROM students s " +
                "LEFT JOIN phones p " +
                "ON s.cpf = p.student_cpf;")
                .execute()
                .subscribeAsCompletionStage()
                .thenApply(converter::getStudents);
    }

    @Override
    public CompletionStage<Optional<Student>> findByCPF(final CPF cpf) {

        Objects.requireNonNull(cpf, "cpf cannot be null");
        return pool.preparedQuery("SELECT s.*, p.* FROM students s " +
                "LEFT JOIN phones p " +
                "ON s.cpf = p.student_cpf " +
                "WHERE cpf = ?;")
                .execute(Tuple.of(cpf))
                .subscribeAsCompletionStage()
                .thenApply(converter::getOptionalStudent);
    }

    @Override
    public CompletionStage<Boolean> save(final Student student) {

        Objects.requireNonNull(student, "student cannot be null");
        final var transactionContainer = new TransactionContainer();

        return pool.begin()
        .subscribeAsCompletionStage()
        .thenApply(transactionContainer::config)
        .thenCompose(transaction -> transaction.get()
                .preparedQuery("INSERT INTO students (cpf, name, email, password) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = ?, email = ?;")
                .execute(
                        Tuple.of(
                                student.getCpf(),
                                student.getName(),
                                student.getEmail(),
                                student.getPassword(),
                                student.getName(),
                                student.getEmail()
                        )
                )
                .subscribeAsCompletionStage()
        ).thenCompose(rowSet -> {

            if (converter.failedOperation(rowSet)) {
                LOGGER.error("Rolling back");
                return rollback(transactionContainer);
            }

            return CompletableFuture.allOf(student.getPhones()
            .stream()
            .map(phone -> transactionContainer.get()
                    .preparedQuery("INSERT INTO phones(code, number, student_cpf) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE code = code, number = number;")
                    .execute(
                            Tuple.of(
                                    phone.getCode(),
                                    phone.getNumber(),
                                    student.getCpf()
                            )
                    )
                    .subscribeAsCompletionStage())
            .map(CompletionStage::toCompletableFuture)
            .toArray(CompletableFuture[]::new));
        })
        .thenCompose(ignore -> commit(transactionContainer)
                .thenApply(aVoid -> {
                    LOGGER.debug("Committed transaction");
                    return null;
                })
        )
        .handle((ignore, throwable) -> {
            if (throwable != null) {
                LOGGER.error("Error commit: " + throwable.getMessage());
                return rollbackFinally(throwable, transactionContainer);
            }

            return successCompletedStage();
        })
        .thenCompose(Function.identity());
    }

    @Override
    public CompletionStage<Boolean> delete(Student student) {

        Objects.requireNonNull(student, "student cannot be null");
        final var transactionContainer = new TransactionContainer();

        return pool.begin()
                .subscribeAsCompletionStage()
                .thenApply(transactionContainer::config)
                .thenCompose(transaction -> transaction.get()
                        .preparedQuery("DELETE FROM phones WHERE student_cpf = ?;")
                        .execute(Tuple.of(student.getCpf()))
                        .subscribeAsCompletionStage()
                ).thenCompose(rowSet -> {

                    if (converter.failedOperation(rowSet)) {
                        LOGGER.error("Rolling back...");
                        return rollbackResult(transactionContainer);
                    }

                    return transactionContainer.get()
                                    .preparedQuery("DELETE FROM students WHERE cpf = ?;")
                                    .execute(Tuple.of(student.getCpf()))
                                    .subscribeAsCompletionStage()
                                    .thenApply(converter::isSuccessOperation);
                })
                .thenCompose(result -> commit(transactionContainer)
                        .thenApply(aVoid -> {
                            LOGGER.debug("Committed transaction");
                            return result;
                        })
                )
                .handle((ignore, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error("Error commit: " + throwable.getMessage());
                        return rollbackFinally(throwable, transactionContainer);
                    }

                    return successCompletedStage();
                })
                .thenCompose(Function.identity());
    }
}
