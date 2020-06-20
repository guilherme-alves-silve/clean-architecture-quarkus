package br.com.guilhermealvessilve.domain.indication.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture.createIndication;
import static br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture.createIndication2;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent2;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndicationTest {

    @Test
    void shouldCreateValidIndication() {

        final var indication = createIndication();
        assertAll(
                () -> assertNotEquals(indication.getIndicator(), createIndication2(), "equals and hashCode different"),
                () -> assertEquals(indication, createIndication(), "equals and hashCode"),
                () -> assertNotNull(indication.getIndicator(), "indication.indicator"),
                () -> assertNotNull(indication.getIndicated(), "indication.indicated"),
                () -> assertNotNull(indication.getDate(), "indication.date")
        );
    }

    @Test
    void shouldThrowExceptionWhenCreatingInvalidIndication() {

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Indication(null, null, null), "indication"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(null, createStudent(), LocalDateTime.now()), "indication.indicator null"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(createStudent(), null, LocalDateTime.now()), "indication.indicated null"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(createStudent(), createStudent2(), null), "indication.date null")
        );
    }
}