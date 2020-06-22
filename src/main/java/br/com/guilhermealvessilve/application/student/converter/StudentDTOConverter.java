package br.com.guilhermealvessilve.application.student.converter;

import br.com.guilhermealvessilve.application.student.dto.PhoneDTO;
import br.com.guilhermealvessilve.application.student.dto.StudentDTO;
import br.com.guilhermealvessilve.domain.student.entity.Student;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentDTOConverter {

    public Student convert(final StudentDTO studentDTO) {
        return Student.withCPFNameAndEmail(
                studentDTO.getCpf(),
                studentDTO.getName(),
                studentDTO.getEmail()
        );
    }

    public StudentDTO convert(final Student student) {

        final var phones = student.getPhones()
                .stream()
                .map(phone -> new PhoneDTO(phone.getCode(), phone.getNumber()))
                .collect(Collectors.toList());

        return StudentDTO.builder()
                .cpf(student.getCpf().getDocument())
                .name(student.getName())
                .email(student.getEmail().getAddress())
                .phones(phones)
                .build();
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
