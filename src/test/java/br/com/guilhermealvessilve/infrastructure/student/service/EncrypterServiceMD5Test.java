package br.com.guilhermealvessilve.infrastructure.student.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class EncrypterServiceMD5Test {

    private final EncrypterServiceMD5 encrypterService;

    @Inject
    EncrypterServiceMD5Test(final EncrypterServiceMD5 encrypterService) {
        this.encrypterService = encrypterService;
    }

    @Test
    void shouldEncryptPassword() {

        final var hashPassword = encrypterService.encrypt("asfasr312f2q21r21f21fsafra".getBytes())
                .toCompletableFuture()
                .join();

        assertEquals("d0aaf36a285d63465a4b2b7634e4e847", hashPassword);
    }

    @Test
    void shouldCheckIfIsTheSameValue() {

        final var isTheSame = encrypterService.checkEncrypt("easfasggasfwgsdfsafgfsa".getBytes(), "00fec44866ac8d8149aa533c5680648a")
                .toCompletableFuture()
                .join();

        assertTrue(isTheSame);
    }

    @Test
    void shouldCheckIfItsADifferentValue() {

        final var isDifferent = encrypterService.checkEncrypt("safasffsafasfsagfdhfjdmuykythasdgjmfxgrjyghnbdsztgrfgnvc".getBytes(), "00fec44866ac8d8149aa533c5680648a")
                .toCompletableFuture()
                .join();

        assertFalse(isDifferent);
    }
}