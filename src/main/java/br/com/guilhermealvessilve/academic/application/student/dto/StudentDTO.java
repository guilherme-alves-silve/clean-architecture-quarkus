package br.com.guilhermealvessilve.academic.application.student.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "password")
public class StudentDTO {

    private String cpf;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private String password;
}
