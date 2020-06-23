package br.com.guilhermealvessilve.academic.domain.student.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Email {

    private static final Pattern EMAIL_PATTERN;

    static {
        final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@" +
                "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        EMAIL_PATTERN = Pattern.compile(regex);
    }


    private final String address;

    public Email(final String address) {

        validate(address);
        this.address = address;
    }

    private void validate(String address) {
        if (!EMAIL_PATTERN.matcher(Objects.requireNonNull(address))
                .matches()) {
            throw new IllegalArgumentException("Invalid email.address: " + address);
        }
    }

    @Override
    public String toString() {
        return address;
    }
}
