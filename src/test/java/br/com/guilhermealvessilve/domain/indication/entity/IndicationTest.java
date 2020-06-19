package br.com.guilhermealvessilve.domain.indication.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture.createIndication;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent2;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndicationTest {

    @Test
    void shouldCreateValidIndication() {

        final var indication = createIndication();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(indication.getIndicator()),
                () -> Assertions.assertNotNull(indication.getIndicated()),
                () -> Assertions.assertNotNull(indication.getDate())
        );
    }

    @Test
    void shouldThrowExceptionWhenCreatingInvalidIndication() {

        Assertions.assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Indication(null, null, null), "indication"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(null, createStudent(), LocalDateTime.now()), "indication.indicator null"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(createStudent(), null, LocalDateTime.now()), "indication.indicated null"),
                () -> assertThrows(NullPointerException.class, () -> new Indication(createStudent(), createStudent2(), null), "indication.date null")
        );
    }
}