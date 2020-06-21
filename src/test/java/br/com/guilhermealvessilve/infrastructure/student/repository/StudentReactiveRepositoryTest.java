package br.com.guilhermealvessilve.infrastructure.student.repository;

import br.com.guilhermealvessilve.infrastructure.testcontainer.MySQLTestcontainer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.*;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.deleteStudents;
import static br.com.guilhermealvessilve.infrastructure.testutil.db.RepositoryUtil.saveStudents;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(MySQLTestcontainer.class)
class StudentReactiveRepositoryTest {

    private final StudentReactiveRepository repository;

    @Inject
    StudentReactiveRepositoryTest(final StudentReactiveRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpEach() {
        saveStudents(repository, createStudent(), createStudent2());
    }

    @AfterEach
    public void tearDownEach() {
        deleteStudents(repository, createStudent(), createStudent2());
    }

    @Test
    void shouldGetAllStudents() {

        final var students = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(2, students.size());
    }

    @Test
    void shouldFindByCPF() {

        final var student = createStudent2();
        final var optSavedStudent = repository.findByCPF(student.getCpf())
                .toCompletableFuture()
                .join();

        assertTrue(optSavedStudent.isPresent());

        final var saved = optSavedStudent.get();

        assertAll(
                () -> assertEquals(student.getCpf(), saved.getCpf(), "student.cpf"),
                () -> assertEquals(student.getEmail(), saved.getEmail(), "student.email"),
                () -> assertEquals(student.getName(), saved.getName(), "student.name"),
                () -> assertEquals(student.getPhones().size(), saved.getPhones().size(), "student.phones")
        );
    }

    @Test
    void shouldFindByCPFStudentWithoutPhone() {

        final var student = createStudentWithoutPhone();
        final var optSavedStudent = repository.findByCPF(student.getCpf())
                .toCompletableFuture()
                .join();

        assertTrue(optSavedStudent.isPresent());

        final var saved = optSavedStudent.get();

        assertAll(
                () -> assertEquals(student.getCpf(), saved.getCpf(), "student.cpf"),
                () -> assertEquals(student.getEmail(), saved.getEmail(), "student.email"),
                () -> assertEquals(student.getName(), saved.getName(), "student.name"),
                () -> assertTrue(student.getPhones().isEmpty(), "student.phones")
        );
    }

    @Test
    void shouldSaveWithSuccess() {

        final var result = saveStudents(repository, createStudent());
        assertTrue(result);
    }

    @Test
    void shouldDeleteWithSuccess() {

        final var student = createStudent();
        final var result = repository.delete(student)
                    .toCompletableFuture()
                    .join();
        assertTrue(result);

        final var optSavedStudent = repository.findByCPF(student.getCpf())
                .toCompletableFuture()
                .join();

        assertFalse(optSavedStudent.isPresent());
    }
}