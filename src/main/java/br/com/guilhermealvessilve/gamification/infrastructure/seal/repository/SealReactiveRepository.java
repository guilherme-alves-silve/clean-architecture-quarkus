package br.com.guilhermealvessilve.gamification.infrastructure.seal.repository;

import br.com.guilhermealvessilve.gamification.domain.seal.entity.Seal;
import br.com.guilhermealvessilve.gamification.domain.seal.repository.SealRepository;
import br.com.guilhermealvessilve.shared.vo.CPF;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class SealReactiveRepository implements SealRepository {

    private final Pool pool;
    private final SealDbConverter converter;

    @Inject
    public SealReactiveRepository(final Pool pool, final SealDbConverter converter) {
        this.pool = Objects.requireNonNull(pool, "SealRepositoryReactive.pool cannot be null!");
        this.converter = Objects.requireNonNull(converter, "SealRepositoryReactive.converter cannot be null!");
    }

    @Override
    public CompletionStage<List<Seal>> getAll() {

        return pool.preparedQuery("SELECT * FROM seals;")
                .execute()
                .subscribeAsCompletionStage()
                .thenApply(converter::getSeals);
    }

    @Override
    public CompletionStage<Optional<Seal>> findByStudentCPF(final CPF cpf) {

        Objects.requireNonNull(cpf, "cpf cannot be null");
        return pool.preparedQuery("SELECT * FROM seals WHERE student_cpf = ?")
                .execute(Tuple.of(cpf))
                .subscribeAsCompletionStage()
                .thenApply(converter::getOptionalSeal);
    }

    @Override
    public CompletionStage<Boolean> save(final Seal seal) {
        Objects.requireNonNull(seal, "seal cannot be null");
        return executeStatement("INSERT INTO seals(student_cpf, name, type) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = name;", Tuple.of(
                seal.getStudentCPF(),
                seal.getName(),
                seal.getType()
        ));
    }

    @Override
    public CompletionStage<Boolean> delete(final Seal seal) {
        Objects.requireNonNull(seal, "seal cannot be null");
        return executeStatement("DELETE FROM seals WHERE student_cpf = ?;", Tuple.of(seal.getStudentCPF()));
    }

    private CompletionStage<Boolean> executeStatement(final String sql, final Tuple tuple) {

        return pool.preparedQuery(sql)
                .execute(tuple)
                .subscribeAsCompletionStage()
                .thenApply(converter::isSuccessOperation);
    }
}