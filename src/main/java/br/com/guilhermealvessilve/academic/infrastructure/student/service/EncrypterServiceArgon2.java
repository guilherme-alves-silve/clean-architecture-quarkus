package br.com.guilhermealvessilve.academic.infrastructure.student.service;

import br.com.guilhermealvessilve.shared.util.PriorityUtils;
import br.com.guilhermealvessilve.academic.domain.student.service.EncrypterService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.quarkus.arc.AlternativePriority;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Argon2 Encrypter
 * References:
 * https://github.com/phxql/argon2-jvm
 * https://github.com/P-H-C/phc-winner-argon2/blob/master/argon2-specs.pdf
 */
@ApplicationScoped
@AlternativePriority(value = PriorityUtils.HIGHEST_PRIORITY)
public class EncrypterServiceArgon2 implements EncrypterService {

    private static final int HASH_GENERATION_MEMORY_COST = 65536;
    private static final int HASH_GENERATION_PARALLELISM = 1;
    private static final int HASH_GENERATION_OPTIMAL_ITERATIONS = 2;

    private final ManagedExecutor managedExecutor;

    @Inject
    public EncrypterServiceArgon2(ManagedExecutor managedExecutor) {
        this.managedExecutor = managedExecutor;
    }

    @Override
    public CompletionStage<String> encrypt(byte[] password) {
        return managedExecutor.supplyAsync(() -> encryptBlocking(password));
    }

    @Override
    public CompletionStage<Boolean> checkEncrypt(byte[] password, String passwordHash) {
        return managedExecutor.supplyAsync(() -> checkEncryptBlocking(password, passwordHash));
    }

    private String encryptBlocking(byte[] password) {

        final var argon2 = Argon2Factory.create();
        try {
            return hash(argon2, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    private boolean checkEncryptBlocking(byte[] password, String passwordHash) {

        final var argon2 = Argon2Factory.create();
        try {
            return argon2.verify(passwordHash, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    private String hash(final Argon2 argon2, byte[] password) {

        return argon2.hash(
                HASH_GENERATION_OPTIMAL_ITERATIONS,
                HASH_GENERATION_MEMORY_COST,
                HASH_GENERATION_PARALLELISM,
                password
        );
    }
}
