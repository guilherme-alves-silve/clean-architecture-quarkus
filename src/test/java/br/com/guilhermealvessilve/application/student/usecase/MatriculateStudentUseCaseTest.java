package br.com.guilhermealvessilve.application.student.usecase;

import br.com.guilhermealvessilve.application.fixture.StudentDTOFixture;
import br.com.guilhermealvessilve.application.student.matriculate.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.application.student.matriculate.dto.StudentDTO;
import br.com.guilhermealvessilve.application.student.matriculate.usecase.MatriculateStudentUseCase;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.domain.student.service.EncrypterService;
import br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@QuarkusTest
class MatriculateStudentUseCaseTest {

    @Inject
    MatriculateStudentUseCase useCase;

    @InjectMock
    StudentDTOConverter mockConverter;

    @InjectMock
    StudentRepository mockRepository;

    @InjectMock
    EncrypterService mockEncrypterService;

    @Test
    void shouldMatriculateStudent() {

        final var student = StudentFixture.createStudent();
        final var encryptedPassword = "returnedencryptedpassword";

        when(mockEncrypterService.encrypt(eq(student.getPassword())))
                .thenReturn(CompletableFuture.completedFuture(encryptedPassword));

        when(mockConverter.convert(any(StudentDTO.class), eq(encryptedPassword)))
                .thenReturn(StudentFixture.createStudent());

        when(mockRepository.save(eq(student)))
                .thenReturn(CompletableFuture.completedFuture(true));

        final var result = useCase.execute(StudentDTOFixture.createStudentDTO())
            .toCompletableFuture()
            .join();

        assertTrue(result);

        verify(mockEncrypterService).encrypt(anyString());
        verify(mockConverter).convert(any(), anyString());
        verify(mockRepository).save(any());
    }
}