package br.com.guilhermealvessilve.infrastructure.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.domain.indication.repository.IndicationRepository;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Tuple;

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
    public IndicationReactiveRepository(final Pool pool, final IndicationDbConverter converter) {
        this.pool = Objects.requireNonNull(pool, "IndicationRepositoryReactive.pool cannot be null!");
        this.converter = Objects.requireNonNull(converter, "IndicationRepositoryReactive.converter cannot be null!");
    }

    @Override
    public CompletionStage<List<Indication>> getAll() {

        return pool.preparedQuery("SELECT " +
                "it.indicator_cpf, sit.name AS indicator_name, sit.email AS indicator_email, " +
                "it.indicated_cpf, sid.name AS indicated_name, sid.email AS indicated_email, " +
                "it.indication_date " +
                "FROM indications it " +
                "INNER JOIN students sit " +
                "ON it.indicator_cpf = sit.cpf " +
                "INNER JOIN students sid " +
                "ON it.indicated_cpf = sid.cpf;")
            .execute()
            .subscribeAsCompletionStage()
            .thenApply(converter::getIndications);
    }

    @Override
    public CompletionStage<Optional<Indication>> findByIndicatorCPF(final CPF cpf) {

        Objects.requireNonNull(cpf, "cpf cannot be null");
        return pool.preparedQuery("SELECT " +
                "it.indicator_cpf, sit.name AS indicator_name, sit.email AS indicator_email, " +
                "it.indicated_cpf, sid.name AS indicated_name, sid.email AS indicated_email, " +
                "it.indication_date " +
                "FROM indications it " +
                "INNER JOIN students sit " +
                "ON it.indicator_cpf = sit.cpf " +
                "INNER JOIN students sid " +
                "ON it.indicated_cpf = sid.cpf " +
                "WHERE it.indicator_cpf = ?;")
                .execute(Tuple.of(cpf))
                .subscribeAsCompletionStage()
                .thenApply(converter::getOptionalIndication);
    }

    @Override
    public CompletionStage<Boolean> save(final Indication indication) {
        return executeStatement("INSERT INTO indications(indicator_cpf, indicated_cpf) VALUES (?, ?);", indication);
    }

    @Override
    public CompletionStage<Boolean> delete(final Indication indication) {
        return executeStatement("DELETE FROM indications WHERE indicator_cpf = ? AND indicated_cpf = ?;", indication);
    }

    private CompletionStage<Boolean> executeStatement(final String sql, final Indication indication) {

        Objects.requireNonNull(indication, "indication cannot be null");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(
                        indication.getIndicatorCpf(),
                        indication.getIndicatedCpf()
                ))
                .subscribeAsCompletionStage()
                .thenApply(converter::isSuccessOperation);
    }
}
