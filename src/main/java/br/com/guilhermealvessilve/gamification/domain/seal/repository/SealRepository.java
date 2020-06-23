package br.com.guilhermealvessilve.gamification.domain.seal.repository;

import br.com.guilhermealvessilve.gamification.domain.seal.entity.Seal;
import br.com.guilhermealvessilve.shared.vo.CPF;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface SealRepository {

    CompletionStage<List<Seal>> getAll();

    CompletionStage<Optional<Seal>> findByStudentCPF(final CPF cpf);

    CompletionStage<Boolean> save(final Seal seal);

    CompletionStage<Boolean> delete(Seal indication);
}
