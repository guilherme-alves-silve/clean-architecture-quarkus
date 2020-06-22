package br.com.guilhermealvessilve.domain.student.entity;

import br.com.guilhermealvessilve.domain.student.exception.StudentMaxOfThreePhonesException;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import br.com.guilhermealvessilve.domain.student.vo.Email;
import br.com.guilhermealvessilve.domain.student.vo.Phone;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void shouldCreateValidStudent() {

        final var student = createStudent();
        assertAll(
                () -> assertNotEquals(student, createStudent2(), "student equals and hashCode different"),
                () -> assertEquals(student, createStudent(), "student equals and hashCode"),
                () -> assertEquals(student.getCpf(), new CPF("11111111111"), "student.cpf"),
                () -> assertEquals(student.getName(), "Joao Pedro", "student.name"),
                () -> assertEquals(student.getEmail(), new Email("joao.pedro@gmail.com"), "student.email"),
                () -> assertEquals(student.getPhones(), List.of(new Phone("11", "11111111"), new Phone("33", "33333333")), "student.phones"),
                () -> assertEquals(student.toString(), "{\"cpf\":\"11111111111\",\"name\":\"Joao Pedro\",\"email\":\"joao.pedro@gmail.com\",\"phones\":[\"(11) 11111111\",\"(33) 33333333\"]}")
        );
    }

    @Test
    void shouldCreateValidStudentWithoutPhone() {

        final var student = createStudentWithoutPhone();
        assertAll(
                () -> assertNotEquals(student, createStudent2(), "student equals and hashCode different"),
                () -> assertEquals(student, createStudent(), "student equals and hashCode"),
                () -> assertEquals(student.getCpf(), new CPF("11111111111"), "student.cpf"),
                () -> assertEquals(student.getName(), "Joao Pedro", "student.name"),
                () -> assertEquals(student.getEmail(), new Email("joao.pedro@gmail.com"), "student.email"),
                () -> assertTrue(student.getPhones().isEmpty(), "student.phones"),
                () -> assertEquals(student.toString(), "{\"cpf\":\"11111111111\",\"name\":\"Joao Pedro\",\"email\":\"joao.pedro@gmail.com\",\"phones\":[]}")
        );
    }

    @Test
    void shouldThrowExceptionWhenFieldsAreNull() {

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> Student.withCPFNameEmailAndPassword(null, null, null, null), "student"),
                () -> assertThrows(NullPointerException.class, () -> Student.withCPFNameEmailAndPassword(null, "José","joao.pedro.marcos@gmail.com", null), "student.cpf null"),
                () -> assertThrows(NullPointerException.class, () -> Student.withCPFNameEmailAndPassword("11111111111", null,"joao.pedro.marcos@gmail.com", null), "student.name null"),
                () -> assertThrows(NullPointerException.class, () -> Student.withCPFNameEmailAndPassword("11111111111", "José", null, "someencryptedpassword"), "student.email null"),
                () -> assertThrows(NullPointerException.class, () -> Student.withCPFNameEmailAndPassword("11111111111", "José", "joao.pedro.marcos@gmail.com", null), "student.password null")
        );
    }

    @Test
    void shouldThrowExceptionWhenAddingMoreThanThreePhoneNumbers() {

        assertThrows(StudentMaxOfThreePhonesException.class, () -> {
            Student.withCPFNameEmailAndPassword(
                    "11111111111",
                    "José",
                    "joao.pedro.marcos@gmail.com",
                    "someencryptedpassword"
            )
            .addPhone("11", "11111111")
            .addPhone("22", "22222222")
            .addPhone("33", "33333333")
            .addPhone("44", "44444444");
        }, "student.phones");
    }
}