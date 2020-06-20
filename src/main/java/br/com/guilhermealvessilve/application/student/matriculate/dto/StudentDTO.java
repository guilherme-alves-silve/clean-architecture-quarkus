package br.com.guilhermealvessilve.application.student.matriculate.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private String cpf;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private String password;
}
