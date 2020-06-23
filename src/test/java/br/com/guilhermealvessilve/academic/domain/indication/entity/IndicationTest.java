package br.com.guilhermealvessilve.academic.domain.indication.entity;

import br.com.guilhermealvessilve.academic.infrastructure.fixture.IndicationFixture;
import br.com.guilhermealvessilve.academic.infrastructure.fixture.StudentFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndicationTest {

    @Test
    void shouldCreateValidIndication() {

        final var indication = IndicationFixture.createIndication();
        assertAll(
                () -> Assertions.assertNotEquals(indication.getIndicator(), IndicationFixture.createIndication2(), "equals and hashCode different"),
                () -> Assertions.assertEquals(indication, IndicationFixture.createIndication(), "equals and hashCode"),
                () -> assertNotNull(indication.getIndicator(), "indication.indicator"),
                () -> assertNotNull(indication.getIndicated(), "indication.indicated"),
                () -> assertNotNull(indication.getDate(), "indication.date")
        );
    }

    @Test
    void shouldThrowExceptionWhenCreatingInvalidIndication() {

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> Indication.withIndicatorAndIndicated(null, null), "indication withIndicatorAndIndicated"),
                () -> assertThrows(NullPointerException.class, () -> Indication.withIndicatorIndicatedAndDate(null, null, null), "indication withIndicatorIndicatedAndDate"),
                () -> assertThrows(NullPointerException.class, () -> Indication.withIndicatorIndicatedAndDate(null, StudentFixture.createStudent(), LocalDateTime.now()), "indication.indicator null"),
                () -> assertThrows(NullPointerException.class, () -> Indication.withIndicatorIndicatedAndDate(StudentFixture.createStudent(), null, LocalDateTime.now()), "indication.indicated null"),
                () -> assertThrows(NullPointerException.class, () -> Indication.withIndicatorIndicatedAndDate(StudentFixture.createStudent(), StudentFixture.createStudent2(), null), "indication.date null")
        );
    }
}