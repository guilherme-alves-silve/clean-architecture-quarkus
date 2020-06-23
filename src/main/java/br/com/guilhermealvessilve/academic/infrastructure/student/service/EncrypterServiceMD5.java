package br.com.guilhermealvessilve.academic.infrastructure.student.service;

import br.com.guilhermealvessilve.academic.domain.student.service.EncrypterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class EncrypterServiceMD5 implements EncrypterService {

    private final ManagedExecutor managedExecutor;

    @Inject
    public EncrypterServiceMD5(ManagedExecutor managedExecutor) {
        this.managedExecutor = managedExecutor;
    }

    @Override
    public CompletionStage<String> encrypt(byte[] password) {
        return managedExecutor.supplyAsync(() -> encryptBlocking(password));
    }

    @Override
    public CompletionStage<Boolean> checkEncrypt(byte[] password, String hashPassword) {
        return managedExecutor.supplyAsync(() -> checkEncryptBlocking(password, hashPassword));
    }

    private String encryptBlocking(byte[] password) {
        final var encryptedByte = DigestUtils.md5(password);

        final var sb = new StringBuilder();
        for (byte b : encryptedByte) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private boolean checkEncryptBlocking(byte[] password, String passwordHash) {
        return passwordHash.equalsIgnoreCase(encryptBlocking(password));
    }
}
