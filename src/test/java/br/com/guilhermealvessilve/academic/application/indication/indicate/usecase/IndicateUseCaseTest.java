package br.com.guilhermealvessilve.academic.application.indication.indicate.usecase;

import br.com.guilhermealvessilve.academic.application.fixture.IndicationDTOFixture;
import br.com.guilhermealvessilve.academic.application.indication.indicate.converter.IndicationDTOConverter;
import br.com.guilhermealvessilve.academic.domain.indication.repository.IndicationRepository;
import br.com.guilhermealvessilve.academic.domain.indication.service.MailerIndicationService;
import br.com.guilhermealvessilve.academic.infrastructure.fixture.IndicationFixture;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class IndicateUseCaseTest {

    @Inject
    IndicateUseCase useCase;

    @InjectMock
    IndicationDTOConverter mockConverter;

    @InjectMock
    IndicationRepository mockRepository;

    @InjectMock
    MailerIndicationService mockMailer;

    @Test
    void shouldIndicateStudent() {

        final var indicationDTO = IndicationDTOFixture.createIndicationDTO();
        final var indication = IndicationFixture.createIndication();
        when(mockConverter.convert(eq(indicationDTO)))
                .thenReturn(indication);

        when(mockRepository.save(eq(indication)))
                .thenReturn(CompletableFuture.completedFuture(true));

        when(mockMailer.sendEmail(eq(indication)))
                .thenReturn(CompletableFuture.completedStage(null));

        final var result = useCase.execute(indicationDTO)
                .toCompletableFuture()
                .join();

        assertTrue(result);

        verify(mockConverter).convert(any());
        verify(mockRepository).save(any());
        verify(mockMailer).sendEmail(any());
    }

    @Test
    void shouldNotIndicateStudentBecauseSavingFailed() {

        final var indicationDTO = IndicationDTOFixture.createIndicationDTO();
        final var indication = IndicationFixture.createIndication();
        when(mockConverter.convert(eq(indicationDTO)))
                .thenReturn(indication);

        when(mockRepository.save(eq(indication)))
                .thenReturn(CompletableFuture.completedFuture(false));

        when(mockMailer.sendEmail(eq(indication)))
                .thenReturn(CompletableFuture.completedStage(null));

        final var result = useCase.execute(indicationDTO)
                .toCompletableFuture()
                .join();

        assertFalse(result);

        verify(mockConverter).convert(any());
        verify(mockRepository).save(any());
        verify(mockMailer, never()).sendEmail(any());
    }
}