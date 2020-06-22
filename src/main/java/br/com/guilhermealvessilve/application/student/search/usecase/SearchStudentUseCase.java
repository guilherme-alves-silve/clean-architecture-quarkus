package br.com.guilhermealvessilve.application.student.search.usecase;

import br.com.guilhermealvessilve.application.student.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.application.student.dto.StudentDTO;
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
    private final StudentDTOConverter converter;

    @Inject
    public SearchStudentUseCase(final StudentRepository repository, final StudentDTOConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public CompletionStage<StudentDTO> execute(final CPF cpf) {

        return repository.findByCPF(cpf)
                .thenCompose(optStudent -> optStudent.map(CompletableFuture::completedStage)
                            .orElseGet(() -> CompletableFuture.failedStage(new StudentNotFoundException(cpf))))
                .thenApply(converter::convert);
    }
}

