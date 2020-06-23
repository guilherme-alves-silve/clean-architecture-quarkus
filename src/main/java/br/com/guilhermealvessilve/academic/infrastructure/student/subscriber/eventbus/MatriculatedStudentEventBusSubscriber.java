package br.com.guilhermealvessilve.academic.infrastructure.student.subscriber.eventbus;

import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.academic.infrastructure.student.subscriber.eventbus.codec.MatriculatedStudentEventMessageCodec;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.util.concurrent.Flow.Subscriber;
import static java.util.concurrent.Flow.Subscription;

@ApplicationScoped
public class MatriculatedStudentEventBusSubscriber implements Subscriber<MatriculatedStudentEvent> {

    private static final Logger LOGGER = Logger.getLogger(MatriculatedStudentEventBusSubscriber.class);

    public static final String EVENT_BUS_ADDRESS = "event.bus.matriculated.student.event";

    public final EventBus eventBus;

    private volatile Subscription subscription;

    @Inject
    public MatriculatedStudentEventBusSubscriber(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.registerCodec(new MatriculatedStudentEventMessageCodec());
    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(final MatriculatedStudentEvent event) {
        LOGGER.info("Sending event to bus address: " + EVENT_BUS_ADDRESS);

        final var deliveryOptions = new DeliveryOptions()
                .setCodecName(MatriculatedStudentEventMessageCodec.CODEC_NAME);

        eventBus.sendAndForget(EVENT_BUS_ADDRESS, event, deliveryOptions);
        subscription.request(1);
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
