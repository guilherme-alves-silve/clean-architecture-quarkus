package br.com.guilhermealvessilve.domain.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.domain.student.vo.CPF;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface IndicationRepository {

    CompletionStage<List<Indication>> getAll();

    CompletionStage<Optional<Indication>> findByIndicatorCPF(final CPF cpf);

    CompletionStage<Boolean> save(final Indication indication);

    CompletionStage<Boolean> delete(final Indication indication);
}
