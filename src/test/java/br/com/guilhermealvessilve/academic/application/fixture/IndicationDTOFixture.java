package br.com.guilhermealvessilve.academic.application.fixture;

import br.com.guilhermealvessilve.academic.application.indication.indicate.dto.IndicationDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IndicationDTOFixture {

    public static IndicationDTO createIndicationDTO() {
        return IndicationDTO.builder()
                .indicator(StudentDTOFixture.createStudentDTO())
                .indicated(StudentDTOFixture.createStudentDTO2())
                .build();
    }

}
