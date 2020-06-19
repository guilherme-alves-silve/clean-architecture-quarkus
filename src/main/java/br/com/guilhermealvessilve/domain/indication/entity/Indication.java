package br.com.guilhermealvessilve.domain.indication.entity;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Indication {

    private final Student indicator;
    private final Student indicated;
    private final LocalDate date;

    public Indication(Student indicator, Student indicated, LocalDate date) {
        this.indicator = Objects.requireNonNull(indicator, "Indication.indicator cannot be null!");
        this.indicated = Objects.requireNonNull(indicated, "Indication.indicated cannot be null!");
        this.date = Objects.requireNonNull(date, "Indication.date cannot be null!");
    }
}
