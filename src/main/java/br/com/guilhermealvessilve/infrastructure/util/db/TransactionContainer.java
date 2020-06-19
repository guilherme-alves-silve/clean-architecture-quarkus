package br.com.guilhermealvessilve.infrastructure.util.db;

import io.vertx.mutiny.sqlclient.Transaction;

public class TransactionContainer {

    private volatile Transaction transaction;

    public Transaction get() {
        return transaction;
    }

    public TransactionContainer config(final Transaction transaction) {
        this.transaction = transaction;
        return this;
    }
}