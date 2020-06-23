package br.com.guilhermealvessilve.gamification.domain.seal.entity;

import br.com.guilhermealvessilve.gamification.domain.seal.vo.SealType;
import br.com.guilhermealvessilve.gamification.infrastructure.fixture.SealFixture;
import br.com.guilhermealvessilve.shared.vo.CPF;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SealTest {

    @Test
    void shouldCreateValidSeal() {

        final var seal = SealFixture.createSeal();
        assertAll(
                () -> Assertions.assertNotEquals(seal, SealFixture.createSeal2(), "seal equals and hashCode different"),
                () -> Assertions.assertEquals(seal, SealFixture.createSeal(), "seal equals and hashCode"),
                () -> assertEquals(seal.getStudentCPF(), new CPF("11111111111"), "seal.studentCpf"),
                () -> assertEquals(seal.getName(), "Mathematics I", "seal.name"),
                () -> assertEquals(seal.getType(), SealType.BEGINNER, "seal.type"),
                () -> assertEquals(seal.toString(), "{\"studentCPF\":\"11111111111\",\"name\":\"Mathematics I\",\"type\":\"BEGINNER\"}")
        );
    }

    @Test
    void shouldThrowExceptionWhenFieldsAreNull() {

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> Seal.withStudentCPFNameAndType(null, null, null), "seal"),
                () -> assertThrows(NullPointerException.class, () -> Seal.withStudentCPFNameAndType(null, "Physics I",SealType.TRAINER), "seal.cpf null"),
                () -> assertThrows(NullPointerException.class, () -> Seal.withStudentCPFNameAndType("11111111111", null,SealType.COMPLETE), "seal.name null"),
                () -> assertThrows(NullPointerException.class, () -> Seal.withStudentCPFNameAndType("11111111111", "Physics I", null), "seal.email null")
        );
    }
}