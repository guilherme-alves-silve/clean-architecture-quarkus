package br.com.guilhermealvessilve.academic.infrastructure.indication.service;

import br.com.guilhermealvessilve.academic.domain.indication.service.MailerIndicationService;
import br.com.guilhermealvessilve.academic.domain.indication.entity.Indication;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * References:
 * https://quarkus.io/guides/mailer
 */
@ApplicationScoped
public class MailerIndicationReactiveService implements MailerIndicationService {

    private static final Logger LOGGER = Logger.getLogger(MailerIndicationReactiveService.class);

    private final ReactiveMailer mailer;

    @Inject
    public MailerIndicationReactiveService(final ReactiveMailer mailer) {
        this.mailer = mailer;
    }

    @Override
    public CompletionStage<Void> sendEmail(final Indication indication) {

        final var indicator = indication.getIndicator();
        final var indicated = indication.getIndicated();
        final var to = indicated.getEmail().getAddress();
        final var subject = "University of Masters Discount of 30%";
        final var text = String.format("You were indicated by %s to do any course in our school with 30 percent discount!",
                indicator.getName());

        LOGGER.info("Sending e-mail to: " + indicated.getEmail());

        return  mailer.send(Mail.withText(to, subject, text))
                .subscribeAsCompletionStage();
    }
}
