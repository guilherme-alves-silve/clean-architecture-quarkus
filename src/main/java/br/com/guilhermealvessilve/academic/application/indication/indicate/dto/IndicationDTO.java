package br.com.guilhermealvessilve.academic.application.indication.indicate.dto;

import br.com.guilhermealvessilve.academic.application.student.dto.StudentDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndicationDTO {

    private StudentDTO indicator;
    private StudentDTO indicated;
}
