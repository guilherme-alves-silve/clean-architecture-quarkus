package br.com.guilhermealvessilve.application.indication.indicate.converter;

import br.com.guilhermealvessilve.application.indication.indicate.dto.IndicationDTO;
import br.com.guilhermealvessilve.application.student.matriculate.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.domain.indication.entity.Indication;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class IndicationDTOConverter {

    private final StudentDTOConverter studentDTOConverter;

    @Inject
    public IndicationDTOConverter(final StudentDTOConverter studentDTOConverter) {
        this.studentDTOConverter = studentDTOConverter;
    }

    public Indication convert(final IndicationDTO indicationDTO) {

        return Indication.withIndicatorAndIndicated(
                studentDTOConverter.convert(indicationDTO.getIndicator()),
                studentDTOConverter.convert(indicationDTO.getIndicated())
        );
    }
}
