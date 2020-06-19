package br.com.guilhermealvessilve.infrastructure.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.domain.indication.repository.IndicationRepository;
import io.vertx.mutiny.sqlclient.Pool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class IndicationReactiveRepository implements IndicationRepository {

    private final Pool pool;
    private final IndicationDbConverter converter;

    @Inject
    public IndicationReactiveRepository(final Pool pool, IndicationDbConverter converter) {
        this.pool = Objects.requireNonNull(pool, "IndicationRepositoryReactive.pool cannot be null!");
        this.converter = Objects.requireNonNull(converter, "IndicationRepositoryReactive.converter cannot be null!");
    }

    @Override
    public CompletionStage<List<Indication>> getAll() {
        return null;
    }

    @Override
    public CompletionStage<Optional<Indication>> findByIndicatorCPF(String cpf) {
        return null;
    }

    @Override
    public CompletionStage<Boolean> save(Indication student) {
        return null;
    }
}
