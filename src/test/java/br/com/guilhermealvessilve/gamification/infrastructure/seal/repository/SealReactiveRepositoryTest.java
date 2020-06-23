package br.com.guilhermealvessilve.gamification.infrastructure.seal.repository;

import br.com.guilhermealvessilve.test.testcontainer.MySQLTestcontainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.gamification.infrastructure.fixture.SealFixture.createSeal;
import static br.com.guilhermealvessilve.gamification.infrastructure.fixture.SealFixture.createSeal2;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.deleteSeals;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.saveSeals;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(MySQLTestcontainer.class)
class SealReactiveRepositoryTest {

    private final SealReactiveRepository repository;

    @Inject
    SealReactiveRepositoryTest(final SealReactiveRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpEach() {
        saveSeals(repository, createSeal(), createSeal2());
    }

    @AfterEach
    public void tearDownEach() {
        deleteSeals(repository, createSeal(), createSeal2());
    }

    @Test
    void shouldGetAllSeals() {

        final var Seals = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(2, Seals.size());
    }

    @Test
    void shouldFindByStudentCPF() {

        final var seal = createSeal();

        final var optSavedSeal = repository.findByStudentCPF(seal.getStudentCPF())
                .toCompletableFuture()
                .join();

        assertTrue(optSavedSeal.isPresent());

        final var saved = optSavedSeal.get();

        assertAll(
                () -> assertEquals(seal.getStudentCPF(), saved.getStudentCPF(), "seal.studentCpf"),
                () -> assertEquals(seal.getName(), saved.getName(), "seal.name"),
                () -> assertEquals(seal.getType(), saved.getType(), "seal.type")
        );
    }

    @Test
    void shouldSaveWithSuccess() {

        deleteSeals(repository, createSeal());
        final var result = saveSeals(repository, createSeal());
        assertTrue(result);
    }

    @Test
    void shouldDeleteWithSuccess() {

        final var seal = createSeal();
        final var result = repository.delete(seal)
                .toCompletableFuture()
                .join();
        assertTrue(result);

        final var optSavedStudent = repository.findByStudentCPF(seal.getStudentCPF())
                .toCompletableFuture()
                .join();

        assertFalse(optSavedStudent.isPresent());
    }
}