package br.com.guilhermealvessilve.domain.student.entity;

import br.com.guilhermealvessilve.domain.student.exception.StudentMaxOfThreePhonesException;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import br.com.guilhermealvessilve.domain.student.vo.Email;
import br.com.guilhermealvessilve.domain.student.vo.Phone;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode(of = "cpf", doNotUseGetters = true)
public class Student {

    private final CPF cpf;
    private final String name;
    private final Email email;
    private final List<Phone> phones;
    private final String password;

    protected Student(final CPF cpf, final String name, final Email email, final String password) {
        this.cpf = Objects.requireNonNull(cpf, "student.cpf cannot be null");
        this.name = Objects.requireNonNull(name, "student.name cannot be null");
        this.email = Objects.requireNonNull(email, "student.email cannot be null");
        this.password = Objects.requireNonNull(password, "student.password cannot be null");
        this.phones = new ArrayList<>();
    }

    public Student add(final Phone phone) {
        phones.add(Objects.requireNonNull(phone, "student.phone cannot be null"));
        return this;
    }

    public Student addPhone(final String code, final String number) {

        //Class invariant
        if (phones.size() == StudentMaxOfThreePhonesException.MAX_NUMBER_OF_PHONES) {
            throw new StudentMaxOfThreePhonesException();
        }

        phones.add(new Phone(code, number));
        return this;
    }

    public static Student withCPFNameAndEmail(final String cpf, final String name, final String email) {
        return new Student(new CPF(cpf), name, new Email(email), StringUtils.EMPTY);
    }

    public static Student withCPFNameEmailAndPassword(final String cpf, final String name, final String email, final String password) {
        return new Student(new CPF(cpf), name, new Email(email), password);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("cpf", cpf)
                .append("name", name)
                .append("email", email)
                .append("phones", phones.toArray())
                .toString();
    }
}
