package br.com.guilhermealvessilve.infrastructure.indication.service;

import br.com.guilhermealvessilve.infrastructure.fixture.IndicationFixture;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class MailIndicationReactiveServiceTest {

    private final MailIndicationReactiveService mailer;
    private final MockMailbox mailbox;

    @Inject
    MailIndicationReactiveServiceTest(final MailIndicationReactiveService mailer, final MockMailbox mailbox) {
        this.mailer = mailer;
        this.mailbox = mailbox;
    }

    @Test
    void shouldSendEmail() {

        final var indication = IndicationFixture.createIndication();
        Assertions.assertDoesNotThrow(() -> {
            mailer.sendEmail(indication)
                    .toCompletableFuture()
                    .join();
        });

        final var emails = mailbox.getMessagesSentTo("pedro.joao@gmail.com");

        assertAll(
                () -> assertEquals(1, emails.size(), "e-mail size"),
                () -> assertEquals(emails.get(0).getSubject(), "University of Masters Discount of 30%", "e-mail subject"),
                () -> assertEquals(emails.get(0).getText(), "You were indicated by Joao Pedro to do any course in our school with 30 percent discount!", "e-mail text")
        );
    }
}