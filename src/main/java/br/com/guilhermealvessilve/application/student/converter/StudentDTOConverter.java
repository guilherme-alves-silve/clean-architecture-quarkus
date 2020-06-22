package br.com.guilhermealvessilve.application.student.converter;

import br.com.guilhermealvessilve.application.student.dto.StudentDTO;
import br.com.guilhermealvessilve.domain.student.entity.Student;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentDTOConverter {

    public Student convert(final StudentDTO studentDTO) {
        return Student.withCPFNameAndEmail(
                studentDTO.getCpf(),
                studentDTO.getName(),
                studentDTO.getEmail()
        );
    }

    public Student convert(final StudentDTO studentDTO, final String encryptedPassword) {
        final var student = Student.withCPFNameEmailAndPassword(
                studentDTO.getCpf(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                encryptedPassword
        );

        studentDTO.getPhones()
                .forEach(phone -> student.addPhone(phone.getCode(), phone.getNumber()));

        return student;
    }

}
