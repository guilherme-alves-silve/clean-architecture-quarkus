package br.com.guilhermealvessilve.academic.domain.student.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Phone {

    private static final Pattern CODE_PATTERN;
    private static final Pattern NUMBER_PATTERN;

    static {
        CODE_PATTERN = Pattern.compile("\\d{2}");
        NUMBER_PATTERN = Pattern.compile("\\d{8,}");
    }

    private final String code;
    private final String number;

    public Phone(final String code, final String number) {

        validate(code, number);
        this.code = code;
        this.number = number;
    }

    private void validate(final String code, final String number) {

        if (!CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Invalid phone.code: " + code);
        }

        if (!NUMBER_PATTERN.matcher(number).matches()) {
            throw new IllegalArgumentException("Invalid phone.number: " + number);
        }
    }

    public String getFormattedPhoneNumber() {
        return String.format("(%s) %s", code, number);
    }

    @Override
    public String toString() {
        return getFormattedPhoneNumber();
    }
}
