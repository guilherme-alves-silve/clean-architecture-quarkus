package br.com.guilhermealvessilve.academic.domain.indication.entity;

import br.com.guilhermealvessilve.academic.domain.student.entity.Student;
import br.com.guilhermealvessilve.academic.domain.student.vo.CPF;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(of = {"indicator", "indicated"}, doNotUseGetters = true)
public class Indication {

    private final Student indicator;
    private final Student indicated;
    private final LocalDateTime date;

    private Indication(final Student indicator, final Student indicated, final LocalDateTime date) {
        this.indicator = Objects.requireNonNull(indicator, "Indication.indicator cannot be null!");
        this.indicated = Objects.requireNonNull(indicated, "Indication.indicated cannot be null!");
        this.date = Objects.requireNonNull(date, "Indication.date cannot be null!");
    }

    public CPF getIndicatorCpf() {
        return indicator.getCpf();
    }

    public CPF getIndicatedCpf() {
        return indicated.getCpf();
    }

    public static Indication withIndicatorAndIndicated(final Student indicator, final Student indicated) {
        return new Indication(indicator, indicated, LocalDateTime.now());
    }

    public static Indication withIndicatorIndicatedAndDate(final Student indicator, final Student indicated, final LocalDateTime date) {
        return new Indication(indicator, indicated, date);
    }
}
