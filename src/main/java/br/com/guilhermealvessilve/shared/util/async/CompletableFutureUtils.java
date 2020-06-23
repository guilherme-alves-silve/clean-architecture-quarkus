package br.com.guilhermealvessilve.shared.util.async;

import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@UtilityClass
public class CompletableFutureUtils {

    public static CompletionStage<Boolean> successCompletedStage() {
        return CompletableFuture.completedStage(true);
    }

    public static CompletionStage<Boolean> failedCompletedStage() {
        return CompletableFuture.completedStage(false);
    }

    public static CompletionStage<Boolean> failedCompletedStage(final Throwable throwable) {
        return CompletableFuture.failedStage(throwable);
    }

    public static Boolean toSuccess(final Void aVoid) {
        return Boolean.TRUE;
    }
}
