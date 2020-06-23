package br.com.guilhermealvessilve.academic.domain.student.event;

import br.com.guilhermealvessilve.shared.vo.CPF;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class MatriculatedStudentEvent implements Event {

    private final CPF studentCpf;

    private final LocalDateTime moment;

    public MatriculatedStudentEvent(CPF studentCpf, LocalDateTime moment) {
        this.studentCpf = Objects.requireNonNull(studentCpf, "studentCpf cannot be null");
        this.moment = Objects.requireNonNull(moment, "moment cannot be null");
    }

    public MatriculatedStudentEvent(final CPF studentCpf) {
        this(studentCpf, LocalDateTime.now());
    }
}
