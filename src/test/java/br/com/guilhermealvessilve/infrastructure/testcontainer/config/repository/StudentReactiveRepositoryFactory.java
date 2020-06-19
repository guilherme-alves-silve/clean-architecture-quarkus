package br.com.guilhermealvessilve.infrastructure.testcontainer.config.repository;

import br.com.guilhermealvessilve.infrastructure.student.repository.StudentDbConverter;
import br.com.guilhermealvessilve.infrastructure.student.repository.StudentReactiveRepository;
import br.com.guilhermealvessilve.infrastructure.testcontainer.MySQLTestcontainer;
import io.quarkus.arc.AlternativePriority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Class used to procu
 * References:
 * https://quarkus.io/blog/quarkus-dependency-injection/
 * https://quarkus.io/guides/getting-started-testing
 * https://quarkus.io/guides/cdi-reference
 */
@ApplicationScoped
public class StudentReactiveRepositoryFactory {

    private final MySQLTestcontainer mySQLTestcontainer;
    private final StudentDbConverter converter;

    @Inject
    StudentReactiveRepositoryFactory(final MySQLTestcontainer mySQLTestcontainer, final StudentDbConverter converter) {
        this.mySQLTestcontainer = mySQLTestcontainer;
        this.converter = converter;
    }

    @Produces
    @AlternativePriority(value = 1)
    public StudentReactiveRepository createRepository() {
        return new StudentReactiveRepository(mySQLTestcontainer.getTestcontainersPool(), converter);
    }
}
