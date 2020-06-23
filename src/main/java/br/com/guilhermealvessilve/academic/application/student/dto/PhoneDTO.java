package br.com.guilhermealvessilve.academic.application.student.dto;

import lombok.*;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    private String code;

    private String number;
}
