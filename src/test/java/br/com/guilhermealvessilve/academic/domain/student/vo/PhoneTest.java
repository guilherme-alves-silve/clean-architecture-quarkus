package br.com.guilhermealvessilve.academic.domain.student.vo;

import br.com.guilhermealvessilve.academic.domain.student.vo.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void shouldThrowExceptionWhenPhonesIsInvalid() {

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Phone("aa", "aaaaaaaaaaaaaaaa"), "Invalid phone.code: aa"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Phone("3333", "333333333333"), "Invalid phone.code: 3333"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Phone("11", "12321"), "Invalid phone.number: 12321"),
                () -> assertThrows(IllegalArgumentException.class, () -> new Phone("11", "aaaaaa"), "Invalid phone.number: aaaaaa")
        );
    }

    @Test
    void shouldAcceptValidPhones() {
        final var phone = new Phone("11", "11111111111111");
        final var phone2 = new Phone("33", "12345678");
        assertAll(
                () -> assertEquals(phone, new Phone("11", "11111111111111")),
                () -> assertEquals(phone.getCode(), "11"),
                () -> assertEquals(phone.getNumber(), "11111111111111"),
                () -> assertEquals(phone.getFormattedPhoneNumber(), "(11) 11111111111111"),
                () -> assertEquals(phone.toString(), "(11) 11111111111111"),
                () -> assertEquals(phone2, new Phone("33", "12345678")),
                () -> assertEquals(phone2.getCode(), "33"),
                () -> assertEquals(phone2.getNumber(), "12345678"),
                () -> assertEquals(phone2.getFormattedPhoneNumber(), "(33) 12345678"),
                () -> assertEquals(phone2.toString(), "(33) 12345678")
        );
    }
}