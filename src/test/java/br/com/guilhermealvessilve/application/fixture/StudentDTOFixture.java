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
}
