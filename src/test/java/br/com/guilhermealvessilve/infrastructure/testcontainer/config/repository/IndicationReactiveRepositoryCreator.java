package br.com.guilhermealvessilve.infrastructure.testcontainer.config.repository;

import br.com.guilhermealvessilve.infrastructure.indication.repository.IndicationDbConverter;
import br.com.guilhermealvessilve.infrastructure.indication.repository.IndicationReactiveRepository;
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
public class IndicationReactiveRepositoryCreator {

    private final MySQLTestcontainer mySQLTestcontainer;
    private final IndicationDbConverter converter;

    @Inject
    IndicationReactiveRepositoryCreator(final MySQLTestcontainer mySQLTestcontainer, final IndicationDbConverter converter) {
        this.mySQLTestcontainer = mySQLTestcontainer;
        this.converter = converter;
    }

    @Produces
    @AlternativePriority(value = 1)
    public IndicationReactiveRepository createRepository() {
        return new IndicationReactiveRepository(mySQLTestcontainer.getTestcontainersPool(), converter);
    }
}
