package br.com.guilhermealvessilve.shared.util.db;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public interface DbConverter {

    default boolean isSuccessOperation(final RowSet<Row> rowSet) {
        return rowSet.rowCount() > 0;
    }

    default boolean failedOperation(final RowSet<Row> rowSet) {
        return rowSet.rowCount() == 0;
    }
}
