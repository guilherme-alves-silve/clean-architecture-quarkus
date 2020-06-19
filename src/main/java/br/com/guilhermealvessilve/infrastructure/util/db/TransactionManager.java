package br.com.guilhermealvessilve.infrastructure.util.db;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface TransactionManager {

    default CompletionStage<Boolean> rollbackResult(final TransactionContainer transactionContainer) {
        return rollback(transactionContainer)
                .thenApply(this::voidToFalse);
    }

    default CompletionStage<Void> rollback(final TransactionContainer transactionContainer) {
        return transactionContainer.get()
                .rollback()
                .subscribeAsCompletionStage();
    }

    default CompletionStage<Void> commit(final TransactionContainer transactionContainer) {
        return transactionContainer.get()
                .commit()
                .subscribeAsCompletionStage();
    }

    default CompletionStage<Boolean> rollbackFinally(final Throwable throwable, final TransactionContainer transactionContainer) {
        final var rollback = transactionContainer.get()
                .rollback()
                .subscribeAsCompletionStage()
                .toCompletableFuture();

        return rollback.isCompletedExceptionally()
                ? CompletableFuture.<Boolean>failedStage(throwable)
                : rollback.thenApply(this::voidToFalse);
    }

    default CompletionStage<Boolean> finallyHandleTransaction(final Throwable throwable, final TransactionContainer transactionContainer) {
        if (throwable != null) {
            return rollbackFinally(throwable, transactionContainer);
        }

        return CompletableFuture.completedStage(true);
    }

    default boolean voidToFalse(final Void aVoid) {
        return false;
    }
}
