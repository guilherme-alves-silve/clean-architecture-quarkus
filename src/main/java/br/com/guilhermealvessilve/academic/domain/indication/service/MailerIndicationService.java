package br.com.guilhermealvessilve.academic.domain.indication.service;

import br.com.guilhermealvessilve.academic.domain.indication.entity.Indication;

import java.util.concurrent.CompletionStage;

public interface MailerIndicationService {

    CompletionStage<Void> sendEmail(final Indication indication);
}
