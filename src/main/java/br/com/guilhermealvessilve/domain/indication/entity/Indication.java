package br.com.guilhermealvessilve.domain.indication.entity;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Indication {

    private final Student indicator;
    private final Student indicated;
    private final LocalDateTime date;

    public Indication(Student indicator, Student indicated, LocalDateTime date) {
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
}
