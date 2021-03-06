package br.com.guilhermealvessilve.academic.infrastructure.student.publisher;

import br.com.guilhermealvessilve.academic.infrastructure.student.subscriber.MatriculatedStudentSubscriber;
import br.com.guilhermealvessilve.academic.infrastructure.student.subscriber.eventbus.MatriculatedStudentEventBusSubscriber;
import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.academic.domain.student.publisher.MatriculatedStudentPublisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.SubmissionPublisher;

@ApplicationScoped
public class MatriculatedStudentPublisherStream extends SubmissionPublisher<MatriculatedStudentEvent> implements MatriculatedStudentPublisher {

    @Inject
    public MatriculatedStudentPublisherStream(
            final MatriculatedStudentSubscriber matriculatedStudentSubscriber,
            final MatriculatedStudentEventBusSubscriber matriculatedStudentEventBusSubscriber
    ) {
        this.subscribe(matriculatedStudentSubscriber);
        this.subscribe(matriculatedStudentEventBusSubscriber);
    }

}