package br.com.guilhermealvessilve.infrastructure.testutil.db;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.domain.indication.repository.IndicationRepository;
import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import lombok.experimental.UtilityClass;
import org.jboss.logging.Logger;

import java.util.function.Supplier;
import java.util.stream.Stream;

@UtilityClass
public class RepositoryUtil {

    private static final Logger LOGGER = Logger.getLogger(RepositoryUtil.class);

    public static boolean saveStudents(final StudentRepository repository, final Student... students) {
        return tryExecution(() ->
                Stream.of(students)
                    .allMatch(student -> repository.save(student)
                            .toCompletableFuture()
                            .join())
        );
    }

    public static boolean deleteStudents(final StudentRepository repository, final Student... students) {
        return tryExecution(() ->
                Stream.of(students)
                    .allMatch(student -> repository.delete(student)
                            .toCompletableFuture()
                            .join())
        );
    }

    public static boolean saveIndications(final IndicationRepository repository, final Indication... indications) {
        return tryExecution(() ->
                Stream.of(indications)
                    .allMatch(indication -> repository.save(indication)
                            .toCompletableFuture()
                            .join())
        );
    }

    public static boolean deleteIndications(final IndicationRepository repository, final Indication... indications) {
        return tryExecution(() ->
                Stream.of(indications)
                    .allMatch(indication -> repository.delete(indication)
                            .toCompletableFuture()
                            .join())
        );
    }

    private static boolean tryExecution(Supplier<Boolean> block) {
        try {
            return block.get();
        } catch (Exception ex) {
            LOGGER.error("Error: " + ex.getMessage(), ex);
            return false;
        }
    }
}
