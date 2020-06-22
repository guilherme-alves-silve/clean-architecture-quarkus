package br.com.guilhermealvessilve.application.student.converter;

import br.com.guilhermealvessilve.application.student.dto.PhoneDTO;
import br.com.guilhermealvessilve.domain.student.vo.Phone;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.application.fixture.StudentDTOFixture.createStudentDTO;
import static br.com.guilhermealvessilve.infrastructure.fixture.StudentFixture.createStudent;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentDTOConverterTest {

    private final StudentDTOConverter converter;

    @Inject
    StudentDTOConverterTest(final StudentDTOConverter converter) {
        this.converter = converter;
    }

    @Test
    void shouldConvertDTOToDomain() {

        final var student = converter.convert(createStudentDTO(), "someencryptedpassword");

        assertAll(
                () -> assertEquals(student.getCpf().getDocument(), "33333333333", "student.cpf"),
                () -> assertEquals(student.getName(), "Jose Napoles", "student.name"),
                () -> assertEquals(student.getEmail().getAddress(), "jose.napoles@gmail.com", "student.email"),
                () -> assertEquals(student.getPassword(), "someencryptedpassword", "student.password"),
                () -> {
                    final var phones = student.getPhones();
                    assertAll(
                            () -> assertEquals(phones.size(), 2, "student.phones.size"),
                            () -> assertEquals(phones.get(0), new Phone("77", "77777777"), "student.phones[0]"),
                            () -> assertEquals(phones.get(1), new Phone("66", "66666666"), "student.phones[1]")
                    );
                }
        );
    }

    @Test
    void shouldConvertDomainToDTO() {

        final var student = converter.convert(createStudent());

        assertAll(
                () -> assertEquals(student.getCpf(), "11111111111", "studentDTO.cpf"),
                () -> assertEquals(student.getName(), "Joao Pedro", "studentDTO.name"),
                () -> assertEquals(student.getEmail(), "joao.pedro@gmail.com", "studentDTO.email"),
                () -> assertNull(student.getPassword(), "studentDTO.password"),
                () -> {
                    final var phones = student.getPhones();
                    assertAll(
                            () -> assertEquals(phones.size(), 2, "studentDTO.phones.size"),
                            () -> assertEquals(phones.get(0), new PhoneDTO("11", "11111111"), "studentDTO.phones[0]"),
                            () -> assertEquals(phones.get(1), new PhoneDTO("33", "33333333"), "studentDTO.phones[1]")
                    );
                }
        );
    }
}