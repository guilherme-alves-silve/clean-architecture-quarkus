package br.com.guilhermealvessilve.gamification.infrastructure.seal.repository;

import br.com.guilhermealvessilve.gamification.domain.seal.entity.Seal;
import br.com.guilhermealvessilve.gamification.domain.seal.vo.SealType;
import br.com.guilhermealvessilve.shared.util.db.CollectionUtils;
import br.com.guilhermealvessilve.shared.util.db.DbConverter;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class SealDbConverter implements DbConverter {

    List<Seal> getSeals(RowSet<Row> rows) {

        return StreamSupport.stream(rows.spliterator(), false)
                .map(this::rowToSeal)
                .collect(toList());
    }

    Optional<Seal> getOptionalSeal(RowSet<Row> rows) {

        final var singleton = getSeals(rows);
        return CollectionUtils.getOptionalFromSingleton(singleton);
    }

    private Seal rowToSeal(Row row) {

        return Seal.withStudentCPFNameAndType(
                row.getString("student_cpf"),
                row.getString("name"),
                SealType.valueOf(row.getString("type"))
        );
    }
}
