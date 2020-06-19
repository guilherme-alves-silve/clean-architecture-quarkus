package br.com.guilhermealvessilve.infrastructure.student.service;

import br.com.guilhermealvessilve.domain.student.service.EncrypterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class EncrypterServiceMD5Impl implements EncrypterService {

    private final ManagedExecutor managedExecutor;

    @Inject
    public EncrypterServiceMD5Impl(ManagedExecutor managedExecutor) {
        this.managedExecutor = managedExecutor;
    }

    @Override
    public CompletionStage<String> encrypt(String value) {
        return managedExecutor.supplyAsync(() -> encryptBlocking(value));
    }

    @Override
    public CompletionStage<Boolean> checkEncrypt(String textValue, String encryptedValue) {
        return managedExecutor.supplyAsync(() -> checkEncryptBlocking(textValue, encryptedValue));
    }

    private String encryptBlocking(String value) {
        final var encryptedByte = DigestUtils.md5(value);

        final var sb = new StringBuilder();
        for (byte b : encryptedByte) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private boolean checkEncryptBlocking(String textValue, String encryptedValue) {
        return encryptedValue.equalsIgnoreCase(encryptBlocking(textValue));
    }
}
