package br.com.guilhermealvessilve.academic.infrastructure.student.publisher;

import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.shared.vo.CPF;
import br.com.guilhermealvessilve.academic.infrastructure.student.subscriber.eventbus.MatriculatedStudentEventBusSubscriber;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class MatriculatedStudentPublisherStreamTest {

    @Inject
    MatriculatedStudentPublisherStream publisherStream;

    @Inject
    EventBus eventBus;

    @Test
    void shouldSubmitToAll() {

        final var iterator = eventBus.<MatriculatedStudentEvent>consumer(MatriculatedStudentEventBusSubscriber.EVENT_BUS_ADDRESS)
                .toBlockingIterable()
                .iterator();

        final int submitted = publisherStream.submit(new MatriculatedStudentEvent(new CPF("99999999999")));
        assertAll(
                () -> {
                    final int numberOfSubmittedItem = 1;
                    assertEquals(numberOfSubmittedItem, submitted, "number of subscribers");
                },
                () -> {

                    assertAll(
                            () -> assertTrue(iterator.hasNext(), "eventBus received"),
                            () -> {
                                final var eventFromEventBus = iterator.next().body();
                                assertEquals(eventFromEventBus.getStudentCpf().getDocument(), "99999999999", "studentEvent.cpf");
                            }
                    );
                }
        );
    }
}