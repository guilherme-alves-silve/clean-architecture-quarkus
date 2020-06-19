package br.com.guilhermealvessilve.infrastructure.student.repository;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture;
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
class StudentReactiveRepositoryTest {

    private final StudentReactiveRepository repository;

    @Inject
    StudentReactiveRepositoryTest(final StudentReactiveRepository repository) {
        this.repository = repository;
    }

    @Test
    void shouldGetAllStudents() {

        saveStudent(StudentFixture.createStudent());

        saveStudent(StudentFixture.createStudent2());

        final var students = repository.getAll()
                .toCompletableFuture()
                .join();

        assertEquals(2, students.size());
    }

    @Test
    void shouldFindByCPF() {

        final var student = StudentFixture.createStudent2();

        saveStudent(student);

        final var optSavedStudent = repository.findByCPF(student.getCpf().getDocument())
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
    void shouldSaveWithSuccess() {

        final var result = saveStudent(StudentFixture.createStudent());
        assertTrue(result);
    }

    private boolean saveStudent(final Student student) {

        return repository.save(student)
                .toCompletableFuture()
                .join();
    }
}