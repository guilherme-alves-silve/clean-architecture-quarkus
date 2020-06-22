package br.com.guilhermealvessilve.application.student.search.usecase;

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

import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class SearchStudentUseCaseTest {

    @Inject
    SearchStudentUseCase useCase;

    @InjectMock
    StudentRepository mockRepository;

    @Test
    void shouldSearchStudent() {

        final var cpf = new CPF("11111111111");
        final var student = createStudent();
        when(mockRepository.findByCPF(eq(cpf)))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(student)));

        final var foundStudent = useCase.execute(cpf)
                .toCompletableFuture()
                .join();

        assertAll(
                () -> assertEquals(foundStudent, student, "found student equals"),
                () -> verify(mockRepository).findByCPF(any())
        );
    }

    @Test
    void shouldThrowStudentNotFoundInSearchStudent() {

        final var cpf = new CPF("11111111111");
        when(mockRepository.findByCPF(eq(cpf)))
                .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

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
                () -> verify(mockRepository).findByCPF(any())
        );
    }
}