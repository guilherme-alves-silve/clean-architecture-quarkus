package br.com.guilhermealvessilve.academic.application.student.matriculate.usecase;

import br.com.guilhermealvessilve.academic.application.fixture.StudentDTOFixture;
import br.com.guilhermealvessilve.academic.application.student.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.academic.application.student.dto.StudentDTO;
import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.academic.domain.student.exception.StudentMaxOfThreePhonesException;
import br.com.guilhermealvessilve.academic.domain.student.publisher.MatriculatedStudentPublisher;
import br.com.guilhermealvessilve.academic.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.academic.domain.student.service.EncrypterService;
import br.com.guilhermealvessilve.academic.infrastructure.fixture.StudentFixture;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
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

    @InjectMock
    MatriculatedStudentPublisher mockPublisher;

    @Test
    void shouldMatriculateStudent() {

        final var student = StudentFixture.createStudent();
        final var encryptedPassword = "returnedencryptedpassword";

        when(mockEncrypterService.encrypt(eq(student.getPassword().getBytes())))
                .thenReturn(CompletableFuture.completedFuture(encryptedPassword));

        when(mockConverter.convert(any(StudentDTO.class), eq(encryptedPassword)))
                .thenReturn(StudentFixture.createStudent());

        when(mockRepository.save(eq(student)))
                .thenReturn(CompletableFuture.completedFuture(true));

        final var result = useCase.execute(StudentDTOFixture.createStudentDTO())
            .toCompletableFuture()
            .join();

        assertTrue(result);

        verify(mockEncrypterService).encrypt(any(byte[].class));
        verify(mockConverter).convert(any(), anyString());
        verify(mockRepository).save(any());
        verify(mockPublisher).submit(any(MatriculatedStudentEvent.class));
    }

    @Test
    void shouldThrowMaxOfThreeNumbersExceptionWhenTryingToMatriculateStudent() {

        when(mockEncrypterService.encrypt(any(byte[].class)))
                .thenReturn(CompletableFuture.completedFuture(StringUtils.EMPTY));

        when(mockConverter.convert(any(StudentDTO.class), anyString()))
                .thenThrow(StudentMaxOfThreePhonesException.class);

        assertAll(
                () -> assertThrows(StudentMaxOfThreePhonesException.class, () -> {
                    try {
                        useCase.execute(StudentDTOFixture.createStudentDTO())
                                .toCompletableFuture()
                                .join();
                    } catch (final CompletionException ex) {
                        throw ex.getCause();
                    }
                }, "students.phones StudentMaxOfThreePhonesException"),
                () -> verify(mockEncrypterService).encrypt(any(byte[].class)),
                () -> verify(mockConverter).convert(any(), anyString()),
                () -> verify(mockRepository, never()).save(any()),
                () -> verify(mockPublisher, never()).submit(any())
        );
    }
}