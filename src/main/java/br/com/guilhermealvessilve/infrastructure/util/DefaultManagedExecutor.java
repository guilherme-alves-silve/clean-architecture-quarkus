package br.com.guilhermealvessilve.infrastructure.util;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class DefaultManagedExecutor {

    private final Integer maxAsync;

    public DefaultManagedExecutor(@ConfigProperty(name = "managed-executor.max-async", defaultValue = "5") Integer maxAsync) {
        this.maxAsync = maxAsync;
    }

    @Produces
    public ManagedExecutor managedExecutor() {
        return ManagedExecutor.builder()
                .maxAsync(maxAsync)
                .build();
    }
}
