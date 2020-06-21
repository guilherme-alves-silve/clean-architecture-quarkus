package br.com.guilhermealvessilve.application.fixture;

import br.com.guilhermealvessilve.application.student.matriculate.dto.PhoneDTO;
import br.com.guilhermealvessilve.application.student.matriculate.dto.StudentDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class StudentDTOFixture {

    public static StudentDTO createStudentDTO() {
        return StudentDTO.builder()
                .cpf("33333333333")
                .name("Jose Napoles")
                .email("jose.napoles@gmail.com")
                .phones(List.of(
                        new PhoneDTO("77", "77777777"),
                        new PhoneDTO("66", "66666666")
                ))
                .password("someencryptedpassword")
                .build();
    }

    public static StudentDTO createStudentDTO2() {
        return StudentDTO.builder()
                .cpf("77777777777")
                .name("Joe Gans")
                .email("joe.gans@outlook.com")
                .phones(List.of(
                        new PhoneDTO("11", "11111111"),
                        new PhoneDTO("88", "88888888")
                ))
                .password("someencryptedpassword2")
                .build();
    }
}
