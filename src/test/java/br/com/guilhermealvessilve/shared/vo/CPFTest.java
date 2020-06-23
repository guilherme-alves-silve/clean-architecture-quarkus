package br.com.guilhermealvessilve.shared.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {

    @Test
    void shouldThrowExceptionWhenCPFsDocumentsIsInvalid() {

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new CPF("aaaaaaaaaaaaaaaaaaaa"), "Invalid cpf.document: aaaaaaaaaaaaaaaaaaaa"),
                () -> assertThrows(IllegalArgumentException.class, () -> new CPF("3trf112t12f12f12f21f"), "Invalid cpf.document: 3trf112t12f12f12f21f"),
                () -> assertThrows(IllegalArgumentException.class, () -> new CPF("1111"), "Invalid cpf.document: 1111"),
                () -> assertThrows(IllegalArgumentException.class, () -> new CPF("1111111111111"), "Invalid cpf.document: 1111111111111")
        );
    }

    @Test
    void shouldAcceptValidCPFDocuments() {
        assertAll(
                () -> assertEquals(new CPF("491.921.240-20"), new CPF("491.921.240-20")),
                () -> assertEquals(new CPF("491.921.240-20").getDocument(), "491.921.240-20"),
                () -> assertEquals(new CPF("88873516009").getDocument(), "88873516009")
        );
    }
}