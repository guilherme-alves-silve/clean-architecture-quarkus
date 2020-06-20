package br.com.guilhermealvessilve.application.student.matriculate.usecase;

import br.com.guilhermealvessilve.application.student.matriculate.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.application.student.matriculate.dto.StudentDTO;
import br.com.guilhermealvessilve.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.domain.student.service.EncrypterService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MatriculateStudentUseCase {

    private final StudentDTOConverter converter;
    private final StudentRepository repository;
    private final EncrypterService encrypterService;

    @Inject
    public MatriculateStudentUseCase(
            final StudentDTOConverter converter,
            final StudentRepository repository,
            final EncrypterService encrypterService
    ) {
        this.converter = converter;
        this.repository = repository;
        this.encrypterService = encrypterService;
    }

    public CompletionStage<Boolean> execute(final StudentDTO studentDTO) {

        return encrypterService.encrypt(studentDTO.getPassword())
                .thenApply(encryptedPassword -> converter.convert(studentDTO, encryptedPassword))
                .thenCompose(repository::save);
    }
}
