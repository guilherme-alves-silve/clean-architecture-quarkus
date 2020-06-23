package br.com.guilhermealvessilve.academic.infrastructure.student.service;

import br.com.guilhermealvessilve.academic.infrastructure.student.service.EncrypterServiceArgon2;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EncrypterServiceArgon2Test {

    private final EncrypterServiceArgon2 encrypterService;

    @Inject
    EncrypterServiceArgon2Test(final EncrypterServiceArgon2 encrypterService) {
        this.encrypterService = encrypterService;
    }

    @Test
    void shouldCheckArgonParameters() {

        final var password = "asfasr312f2q21r21f21fsafra";
        final var hashPassword = generateHashPassword(password);
        assertTrue(hashPassword.contains("$argon2i$v=19$m=65536,t=2,p="), "parameters didn't changed");
    }

    @Test
    void shouldEncryptPassword() {

        final var password = "asfasr312f2q21r21f21fsafra";
        final var hashPassword = generateHashPassword(password);

        final var isTheSame = encrypterService.checkEncrypt(password.getBytes(), hashPassword)
                .toCompletableFuture()
                .join();

        assertTrue(isTheSame);
    }

    @Test
    void shouldCheckIfItsADifferentPassword() {

        final var password = "asfasr312f2q21r21f21fsafra";
        final var hashPassword = generateHashPassword(password);

        final var isDifferent = encrypterService.checkEncrypt("safasffsafasfsagfdmuykythasdgjmfxgrjyghnbdsztgrfgnvc".getBytes(), hashPassword)
                .toCompletableFuture()
                .join();

        assertFalse(isDifferent);
    }

    private String generateHashPassword(final String password) {
        return encrypterService.encrypt(password.getBytes())
                .toCompletableFuture()
                .join();
    }
}