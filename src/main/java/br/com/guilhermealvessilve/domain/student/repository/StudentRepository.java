package br.com.guilhermealvessilve.domain.student.repository;

import br.com.guilhermealvessilve.domain.student.entity.Student;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface StudentRepository {

    CompletionStage<List<Student>> getAll();

    CompletionStage<Optional<Student>> findByCPF(final String cpf);

    CompletionStage<Boolean> save(final Student student);
}
