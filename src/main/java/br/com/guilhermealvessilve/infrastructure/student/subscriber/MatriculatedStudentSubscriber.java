package br.com.guilhermealvessilve.infrastructure.student.subscriber;

import br.com.guilhermealvessilve.domain.student.event.MatriculatedStudentEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Flow;

import static java.util.concurrent.Flow.*;

@ApplicationScoped
public class MatriculatedStudentSubscriber implements Flow.Subscriber<MatriculatedStudentEvent> {

    private static final Logger LOGGER = Logger.getLogger(MatriculatedStudentSubscriber.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private volatile Subscription subscription;

    @Override
    public void onSubscribe(final Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(final MatriculatedStudentEvent event) {
        LOGGER.info(String.format(
                "The student %s was matriculated in the date and time: %s",
                event.getStudentCpf().getDocument(),
                FORMATTER.format(event.getMoment())
        ));
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(final Throwable throwable) {
        LOGGER.error("An error has occurred: " + throwable.getMessage(), throwable);
    }

    @Override
    public void onComplete() {
        LOGGER.info("Completed");
    }
}
