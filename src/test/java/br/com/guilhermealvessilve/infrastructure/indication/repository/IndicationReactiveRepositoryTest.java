package br.com.guilhermealvessilve.infrastructure.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture;
import br.com.guilhermealvessilve.infrastructure.testcontainer.config.repository.IndicationReactiveRepositoryCreator;
import br.com.guilhermealvessilve.infrastructure.testcontainer.MySQLTestcontainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(MySQLTestcontainer.class)
class IndicationReactiveRepositoryTest {

    private final IndicationReactiveRepositoryCreator creator;

    @Inject
    IndicationReactiveRepositoryTest(final IndicationReactiveRepositoryCreator creator) {
        this.creator = creator;
    }

    @Test
    void shouldGetAllIndications() {

        saveIndication(IndicationFixture.createIndication());

        final var repository = creator.createRepository();

        saveIndication(IndicationFixture.createIndication2());

        final var Indications = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(2, Indications.size());
    }

    @Test
    void shouldFindByCPF() {

        final var repository = creator.createRepository();

        final var indication = IndicationFixture.createIndication();

        saveIndication(indication);

        final var optSavedIndication = repository.findByIndicatorCPF(indication.getIndicator().getCpf().getDocument())
                .toCompletableFuture()
                .join();

        assertTrue(optSavedIndication.isPresent());

        final var saved = optSavedIndication.get();

        assertAll(
                () -> assertEquals("null", null, "Indication.cpf")
        );
    }

    @Test
    void shouldSaveWithSuccess() {

        final var result = saveIndication(IndicationFixture.createIndication());
        assertTrue(result);
    }

    private boolean saveIndication(final Indication indication) {

        final var repository = creator.createRepository();

        return repository.save(indication)
                .toCompletableFuture()
                .join();
    }
}