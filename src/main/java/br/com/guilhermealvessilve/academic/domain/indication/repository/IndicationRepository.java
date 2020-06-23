package br.com.guilhermealvessilve.academic.domain.indication.repository;

import br.com.guilhermealvessilve.academic.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.shared.vo.CPF;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface IndicationRepository {

    CompletionStage<List<Indication>> getAll();

    CompletionStage<Optional<Indication>> findByIndicatorCPF(final CPF cpf);

    CompletionStage<Boolean> save(final Indication indication);

    CompletionStage<Boolean> delete(final Indication indication);
}
