package br.com.guilhermealvessilve.infrastructure.indication.repository;

import br.com.guilhermealvessilve.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.domain.student.entity.Student;
import br.com.guilhermealvessilve.infrastructure.util.db.CollectionUtils;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class IndicationDbConverter {

    List<Indication> getIndications(RowSet<Row> rows) {

        return StreamSupport.stream(rows.spliterator(), false)
                .map(this::rowToIndication)
                .collect(toList());
    }

    Optional<Indication> getOptionalIndication(RowSet<Row> rows) {

        final var singleton = getIndications(rows);
        return CollectionUtils.getOptionalFromSingleton(singleton);
    }

    private Indication rowToIndication(Row row) {

        final var indicator = Student.withCPFNameAndEmail(
                row.getString("indicator_cpf"),
                row.getString("indicator_name"),
                row.getString("indicator_email")
        );

        final var indicated = Student.withCPFNameAndEmail(
                row.getString("indicated_cpf"),
                row.getString("indicated_name"),
                row.getString("indicated_email")
        );

        return new Indication(
                indicator,
                indicated,
                row.getLocalDate("indication_date")
        );
    }
}
