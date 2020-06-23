package br.com.guilhermealvessilve.academic.infrastructure.indication.repository;

import br.com.guilhermealvessilve.academic.infrastructure.student.repository.StudentReactiveRepository;
import br.com.guilhermealvessilve.test.testcontainer.MySQLTestcontainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.academic.infrastructure.fixture.IndicationFixture.*;
import static br.com.guilhermealvessilve.academic.infrastructure.fixture.StudentFixture.*;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.deleteIndications;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.deleteStudents;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.saveIndications;
import static br.com.guilhermealvessilve.test.db.RepositoryUtil.saveStudents;
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
        saveStudents(studentRepository, createStudent(), createStudent2(), createStudent3());
        saveIndications(repository, createIndication(), createIndication2(), createIndication3(), createIndication4());
    }

    @AfterEach
    public void tearDownEach() {
        deleteIndications(repository, createIndication(), createIndication2(), createIndication3(), createIndication4());
        deleteStudents(studentRepository, createStudent(), createStudent2(), createStudent3());
    }

    @Test
    void shouldGetAllIndications() {

        final var indications = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(4, indications.size());
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
                () -> assertEquals(indication.getIndicator(), saved.getIndicator(), "indicator"),
                () -> assertEquals(indication.getIndicated(), saved.getIndicated(), "indicated"),
                () -> assertNotNull(saved.getDate(), "indication.date")
        );
    }

    @Test
    void shouldSaveWithSuccess() {

        deleteIndications(repository, createIndication());
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