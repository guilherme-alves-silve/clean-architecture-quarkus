package br.com.guilhermealvessilve.domain.student.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("asgasgas@"), "Invalid email.address: asgasgas@"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("@sadasdasdsa"), "Invalid email.address: @sadasdasdsa"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("1111"), "Invalid email.address: 1111"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("1111111111111"), "Invalid email.address: 1111111111111")
        );
    }

    @Test
    void shouldAcceptValidEmails() {
        assertAll(
                () -> assertEquals(new Email("email@email.link"), new Email("email@email.link")),
                () -> assertEquals(new Email("email@email.link").getAddress(), "email@email.link"),
                () -> assertEquals(new Email("john@john.com").getAddress(), "john@john.com")
        );
    }
}