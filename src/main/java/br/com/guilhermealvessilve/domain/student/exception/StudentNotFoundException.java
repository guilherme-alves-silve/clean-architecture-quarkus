package br.com.guilhermealvessilve.domain.student.exception;

import br.com.guilhermealvessilve.domain.student.vo.CPF;

public class StudentNotFoundException extends DomainException {

    public StudentNotFoundException(final CPF cpf) {
        super(String.format("The student with CPF %s was not found!", cpf.getDocument()));
    }
}
