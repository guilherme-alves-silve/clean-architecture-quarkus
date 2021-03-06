package br.com.guilhermealvessilve.academic.infrastructure.indication.repository;

import br.com.guilhermealvessilve.academic.domain.indication.entity.Indication;
import br.com.guilhermealvessilve.academic.domain.student.entity.Student;
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
public class IndicationDbConverter implements DbConverter {

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

        return Indication.withIndicatorIndicatedAndDate(
                indicator,
                indicated,
                row.getLocalDateTime("indication_date")
        );
    }
}
