package br.com.guilhermealvessilve.infrastructure.student.repository;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.vo.Phone;
import br.com.guilhermealvessilve.infrastructure.util.db.CollectionUtils;
import br.com.guilhermealvessilve.infrastructure.util.db.DbConverter;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class StudentDbConverter implements DbConverter {

    List<Student> getStudents(RowSet<Row> rows) {

        final var cpfAndStudent = new HashMap<String, Student>();
        return StreamSupport.stream(rows.spliterator(), false)
                .map(studentPhoneRow -> {
                    final var cpf = studentPhoneRow.getString("cpf");
                    final var student = cpfAndStudent.computeIfAbsent(cpf, cpfKey -> rowToStudent(studentPhoneRow));
                    student.add(rowToPhone(studentPhoneRow));
                    return student;
                })
                .distinct()
                .collect(toList());
    }

    Optional<Student> getOptionalStudent(RowSet<Row> rows) {

        final var singleton = getStudents(rows);
        return CollectionUtils.getOptionalFromSingleton(singleton);
    }

    private Student rowToStudent(Row row) {

        return Student.withCPFNameAndEmail(
                row.getString("cpf"),
                row.getString("name"),
                row.getString("email")
        );
    }

    private Phone rowToPhone(Row row) {

        return new Phone(
                row.getString("code"),
                row.getString("number")
        );
    }
}
