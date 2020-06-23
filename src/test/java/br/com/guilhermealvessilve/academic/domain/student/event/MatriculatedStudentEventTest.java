package br.com.guilhermealvessilve.academic.domain.student.event;

import br.com.guilhermealvessilve.shared.vo.CPF;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class MatriculatedStudentEventTest {

    @Test
    void shouldCreateEvent() {

        final var event = new MatriculatedStudentEvent(new CPF("11111111111"));

        assertAll(
                () -> assertEquals(event.getStudentCpf(), new CPF("11111111111"), "event.studentCpf"),
                () -> assertEquals(event.getMoment().toLocalDate(), LocalDate.now(), "event.moment")
        );
    }
}
