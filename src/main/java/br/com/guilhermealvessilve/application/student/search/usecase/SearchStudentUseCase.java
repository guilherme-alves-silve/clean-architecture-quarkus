package br.com.guilhermealvessilve.application.student.search.usecase;

import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.domain.student.exception.StudentNotFoundException;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.domain.student.vo.CPF;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class SearchStudentUseCase {

    private final StudentRepository repository;

    @Inject
    public SearchStudentUseCase(final StudentRepository repository) {
        this.repository = repository;
    }

    public CompletionStage<Student> execute(final CPF cpf) {

        return repository.findByCPF(cpf)
                .thenCompose(optStudent -> optStudent.map(CompletableFuture::completedStage)
                            .orElseGet(() -> CompletableFuture.failedStage(new StudentNotFoundException(cpf))));
    }
}

