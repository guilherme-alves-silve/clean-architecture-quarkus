package br.com.guilhermealvessilve.infrastructure.student.service;

import br.com.guilhermealvessilve.domain.student.service.EncrypterService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class EncrypterServiceMD5ImplTest {

    private final EncrypterService encrypterService;

    @Inject
    EncrypterServiceMD5ImplTest(EncrypterService encrypterService) {
        this.encrypterService = encrypterService;
    }

    @Test
    void shouldEncryptTextValue() {

        final var encryptedValue = encrypterService.encrypt("asfasr312f2q21r21f21fsafra")
                .toCompletableFuture()
                .join();

        assertEquals("d0aaf36a285d63465a4b2b7634e4e847", encryptedValue);
    }

    @Test
    void shouldCheckIfIsTheSameValue() {

        final var isTheSame = encrypterService.checkEncrypt("easfasggasfwgsdfsafgfsa", "00fec44866ac8d8149aa533c5680648a")
                .toCompletableFuture()
                .join();

        assertTrue(isTheSame);
    }

    @Test
    void shouldCheckIfItsDifferentSameValue() {

        final var isDifferent = encrypterService.checkEncrypt("safasffsafasfsagfdhfjdmuykythasdgjmfxgrjyghnbdsztgrfgnvc", "00fec44866ac8d8149aa533c5680648a")
                .toCompletableFuture()
                .join();

        assertFalse(isDifferent);
    }
}