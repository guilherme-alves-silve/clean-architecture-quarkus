package br.com.guilhermealvessilve.domain.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface IndicationRepository {

    CompletionStage<List<Indication>> getAll();

    CompletionStage<Optional<Indication>> findByIndicatorCPF(final String cpf);

    CompletionStage<Boolean> save(final Indication student);
}
