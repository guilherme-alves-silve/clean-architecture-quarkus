package br.com.guilhermealvessilve.academic.application.student.matriculate.usecase;

import br.com.guilhermealvessilve.academic.application.student.converter.StudentDTOConverter;
import br.com.guilhermealvessilve.academic.application.student.dto.StudentDTO;
import br.com.guilhermealvessilve.academic.domain.student.entity.Student;
import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.academic.domain.student.publisher.MatriculatedStudentPublisher;
import br.com.guilhermealvessilve.academic.domain.student.repository.StudentRepository;
import br.com.guilhermealvessilve.academic.domain.student.service.EncrypterService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MatriculateStudentUseCase {

    private final StudentDTOConverter converter;
    private final StudentRepository repository;
    private final EncrypterService encrypterService;
    private final MatriculatedStudentPublisher publisher;

    @Inject
    public MatriculateStudentUseCase(
            final StudentDTOConverter converter,
            final StudentRepository repository,
            final EncrypterService encrypterService,
            final MatriculatedStudentPublisher publisher
    ) {
        this.converter = converter;
        this.repository = repository;
        this.encrypterService = encrypterService;
        this.publisher = publisher;
    }

    public CompletionStage<Boolean> execute(final StudentDTO studentDTO) {

        return encrypterService.encrypt(studentDTO.getPassword().getBytes())
                .thenApply(encryptedPassword -> converter.convert(studentDTO, encryptedPassword))
                .thenCompose(student -> repository.save(student)
                            .thenApply(result -> {
                                notifyMatriculatedStudent(student);
                                return result;
                            }));
    }

    private void notifyMatriculatedStudent(final Student student) {
        publisher.submit(new MatriculatedStudentEvent(student.getCpf()));
    }
}
