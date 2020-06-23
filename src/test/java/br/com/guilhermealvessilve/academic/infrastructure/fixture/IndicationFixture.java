package br.com.guilhermealvessilve.academic.infrastructure.fixture;

import br.com.guilhermealvessilve.academic.domain.indication.entity.Indication;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class IndicationFixture {

    public static Indication createIndication() {
        return Indication.withIndicatorAndIndicated(
                StudentFixture.createStudent(),
                StudentFixture.createStudent2()
        );
    }

    public static Indication createIndication2() {
        return Indication.withIndicatorIndicatedAndDate(
                StudentFixture.createStudent2(),
                StudentFixture.createStudent(),
                LocalDateTime.now().minusDays(5)
        );
    }

    public static Indication createIndication3() {
        return Indication.withIndicatorIndicatedAndDate(
                StudentFixture.createStudent3(),
                StudentFixture.createStudent2(),
                LocalDateTime.now().minusYears(4)
        );
    }

    public static Indication createIndication4() {
        return Indication.withIndicatorIndicatedAndDate(
                StudentFixture.createStudent2(),
                StudentFixture.createStudent3(),
                LocalDateTime.now().minusMonths(1)
        );
    }
}
