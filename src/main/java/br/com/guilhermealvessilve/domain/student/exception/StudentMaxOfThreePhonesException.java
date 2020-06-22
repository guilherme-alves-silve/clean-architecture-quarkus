package br.com.guilhermealvessilve.domain.student.exception;

public class StudentMaxOfThreePhonesException extends DomainException {

    public static final int MAX_NUMBER_OF_PHONES = 3;

    public StudentMaxOfThreePhonesException() {
        super("The student must have at max, two phone numbers");
    }
}
