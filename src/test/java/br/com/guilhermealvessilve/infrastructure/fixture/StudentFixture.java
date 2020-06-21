package br.com.guilhermealvessilve.infrastructure.fixture;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentFixture {

    public static Student createStudentWithoutPhone() {
        return Student.withCPFNameEmailAndPassword("11111111111", "Joao Pedro", "joao.pedro@gmail.com", "someencryptedpassword");
    }

    public static Student createStudent() {
        return createStudentWithoutPhone()
                .addPhone("11", "11111111")
                .addPhone("33", "33333333");
    }

    public static Student createStudent2() {
        return Student.withCPFNameEmailAndPassword("22222222222", "Pedro Joao", "pedro.joao@gmail.com", "someencryptedpassword")
                .addPhone("11", "22222222")
                .addPhone("33", "44444444")
                .addPhone("11", "555555555");
    }

    public static Student createStudent3() {
        return Student.withCPFNameEmailAndPassword("33333333333", "Miriam Josefina", "miriam.josefina@gmail.com", "someencryptedpassword")
                .addPhone("33", "444444444444444");
    }
}
