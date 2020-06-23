package br.com.guilhermealvessilve.academic.domain.student.repository;

import br.com.guilhermealvessilve.academic.domain.student.entity.Student;
import br.com.guilhermealvessilve.shared.vo.CPF;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface StudentRepository {

    CompletionStage<List<Student>> getAll();

    CompletionStage<Optional<Student>> findByCPF(final CPF cpf);

    CompletionStage<Boolean> save(final Student student);

    CompletionStage<Boolean> delete(Student student);
}
