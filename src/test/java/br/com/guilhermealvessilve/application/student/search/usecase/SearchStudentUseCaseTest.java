package br.com.guilhermealvessilve.application.student.search.usecase;

import br.com.guilhermealvessilve.application.student.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.exception.StudentNotFoundException;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static br.com.guilhermealvessilve.application.fixture.StudentDTOFixture.createStudentDTO3;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class SearchStudentUseCaseTest {

    @Inject
    SearchStudentUseCase useCase;

    @InjectMock
    StudentRepository mockRepository;

    @InjectMock
    StudentDTOConverter mockConverter;

    @Test
    void shouldSearchStudent() {

        final var cpf = new CPF("11111111111");
        final var student = createStudent();
        when(mockRepository.findByCPF(eq(cpf)))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(student)));

        when(mockConverter.convert(eq(student)))
                .thenReturn(createStudentDTO3());

        final var studentDTO = useCase.execute(cpf)
                .toCompletableFuture()
                .join();

        assertAll(
                () -> assertEquals(studentDTO, createStudentDTO3(), "studentDTO equals"),
                () -> verify(mockRepository).findByCPF(any()),
                () -> verify(mockConverter).convert(any(Student.class))
        );
    }

    @Test
    void shouldThrowStudentNotFoundInSearchStudent() {

        final var cpf = new CPF("11111111111");
        final var student = createStudent();
        when(mockRepository.findByCPF(eq(cpf)))
                .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        when(mockConverter.convert(eq(student)))
                .thenReturn(createStudentDTO3());

        assertAll(
                () -> assertThrows(StudentNotFoundException.class, () -> {
                    try {
                        useCase.execute(cpf)
                                .toCompletableFuture()
                                .join();
                    } catch (final CompletionException ex) {
                        throw  ex.getCause();
                    }
                }, "student not found exception"),
                () -> verify(mockRepository).findByCPF(any()),
                () -> verify(mockConverter, never()).convert(any(Student.class))
        );
    }
}