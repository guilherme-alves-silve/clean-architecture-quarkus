package br.com.guilhermealvessilve.shared.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class CPF {

    private static final Pattern CPF_PATTERN;

    static {
        final String regex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";
        CPF_PATTERN = Pattern.compile(regex);
    }

    private final String document;

    public CPF(final String document) {

        validate(document);
        this.document = document;
    }

    private void validate(final String document) {
        if (!CPF_PATTERN.matcher(Objects.requireNonNull(document))
                .matches()) {
            throw new IllegalArgumentException("Invalid cpf.document: " + document);
        }
    }

    @Override
    public String toString() {
        return document;
    }
}
