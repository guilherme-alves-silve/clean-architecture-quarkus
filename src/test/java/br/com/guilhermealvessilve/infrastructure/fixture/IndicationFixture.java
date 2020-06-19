package br.com.guilhermealvessilve.infrastructure.fixture;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class IndicationFixture {

    public static Indication createIndication() {
        return new Indication(
            StudentFixture.createStudent(),
            StudentFixture.createStudent2(),
            LocalDate.now()
        );
    }

    public static Indication createIndication2() {
        return new Indication(
                StudentFixture.createStudent2(),
                StudentFixture.createStudent(),
                LocalDate.now().minusDays(5)
        );
    }
}
