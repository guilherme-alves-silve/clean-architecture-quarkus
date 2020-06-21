package br.com.guilhermealvessilve.application.indication.indicate.usecase;

import br.com.guilhermealvessilve.application.indication.indicate.converter.IndicationDTOConverter;
import br.com.guilhermealvessilve.application.indication.indicate.dto.IndicationDTO;
import br.com.guilhermealvessilve.domain.indication.repository.IndicationRepository;
import br.com.guilhermealvessilve.domain.indication.service.MailerIndicationService;
import br.com.guilhermealvessilve.infrastructure.util.async.CompletableFutureUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class IndicateUseCase {

    private final IndicationDTOConverter converter;
    private final IndicationRepository repository;
    private final MailerIndicationService mailer;

    @Inject
    public IndicateUseCase(
            final IndicationDTOConverter converter,
            final IndicationRepository repository,
            final MailerIndicationService mailer
    ) {
        this.converter = converter;
        this.repository = repository;
        this.mailer = mailer;
    }

    public CompletionStage<Boolean> execute(final IndicationDTO indicationDTO) {

        final var indication = converter.convert(indicationDTO);
        return repository.save(indication)
                .thenCompose(result -> {
                    if (result) {
                        return mailer.sendEmail(indication)
                                .thenApply(CompletableFutureUtils::toSuccess);
                    }

                    return CompletableFutureUtils.failedCompletedStage();
                });
    }
}
