package br.com.guilhermealvessilve.domain.indication.service;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;

import java.util.concurrent.CompletionStage;

public interface MailerIndicationService {

    CompletionStage<Void> sendEmail(final Indication indication);
}