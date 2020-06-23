package br.com.guilhermealvessilve.gamification.domain.seal.entity;

import br.com.guilhermealvessilve.gamification.domain.seal.vo.SealType;
import br.com.guilhermealvessilve.shared.vo.CPF;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

@Getter
@EqualsAndHashCode(doNotUseGetters = true)
public class Seal {

    private final CPF studentCPF;

    private final String name;

    private final SealType type;

    private Seal(final CPF studentCPF, final String name, final SealType type) {
        this.studentCPF = studentCPF;
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.type = Objects.requireNonNull(type, "type cannot be null");
    }

    public static Seal withStudentCPFNameAndType(final String studentCPF, final String name, final SealType type) {
        final var cpf = new CPF(Objects.requireNonNull(studentCPF, "studentCPF cannot be null"));
        return new Seal(cpf, name, type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("studentCPF", studentCPF)
                .append("name", name)
                .append("type", type)
                .toString();
    }
}
