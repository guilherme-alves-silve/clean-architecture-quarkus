package br.com.guilhermealvessilve.infrastructure.fixture;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class IndicationFixture {

    public static Indication createIndication() {
        return new Indication(
                StudentFixture.createStudent(),
                StudentFixture.createStudent2(),
                LocalDateTime.now()
        );
    }

    public static Indication createIndication2() {
        return new Indication(
                StudentFixture.createStudent2(),
                StudentFixture.createStudent(),
                LocalDateTime.now().minusDays(5)
        );
    }

    public static Indication createIndication3() {
        return new Indication(
                StudentFixture.createStudent3(),
                StudentFixture.createStudent2(),
                LocalDateTime.now().minusYears(4)
        );
    }

    public static Indication createIndication4() {
        return new Indication(
                StudentFixture.createStudent2(),
                StudentFixture.createStudent3(),
                LocalDateTime.now().minusMonths(1)
        );
    }
}
