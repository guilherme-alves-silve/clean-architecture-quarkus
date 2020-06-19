package br.com.guilhermealvessilve.infrastructure.indication.repository;

import br.com.guilhermealvessilve.infrastructure.student.repository.StudentReactiveRepository;
import br.com.guilhermealvessilve.infrastructure.testcontainer.MySQLTestcontainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture.createIndication;
import static br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture.createIndication2;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent2;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.deleteIndications;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.deleteStudents;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.saveIndications;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.saveStudents;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(MySQLTestcontainer.class)
class IndicationReactiveRepositoryTest {

    private final IndicationReactiveRepository repository;
    private final StudentReactiveRepository studentRepository;

    @Inject
    IndicationReactiveRepositoryTest(
            final IndicationReactiveRepository repository,
            final StudentReactiveRepository studentRepository
    ) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @BeforeEach
    public void setUpEach() {
        saveStudents(studentRepository, createStudent(), createStudent2());
        saveIndications(repository, createIndication(), createIndication2());
    }

    @AfterEach
    public void tearDownEach() {
        deleteIndications(repository, createIndication(), createIndication2());
        deleteStudents(studentRepository, createStudent(), createStudent2());
    }

    @Test
    void shouldGetAllIndications() {

        final var indications = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(2, indications.size());
    }

    @Test
    void shouldFindByCPF() {

        final var indication = createIndication();

        final var optSavedIndication = repository.findByIndicatorCPF(indication.getIndicatorCpf())
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

        final var result = saveIndications(repository, createIndication());
        assertTrue(result);
    }

    @Test
    void shouldDeleteWithSuccess() {

        final var indication = createIndication();
        final var result = repository.delete(indication)
                .toCompletableFuture()
                .join();
        assertTrue(result);

        final var optSavedStudent = repository.findByIndicatorCPF(indication.getIndicatorCpf())
                .toCompletableFuture()
                .join();

        assertFalse(optSavedStudent.isPresent());
    }
}