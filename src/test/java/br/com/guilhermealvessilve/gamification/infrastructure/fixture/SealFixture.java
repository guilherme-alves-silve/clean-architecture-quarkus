package br.com.guilhermealvessilve.gamification.infrastructure.fixture;

import br.com.guilhermealvessilve.gamification.domain.seal.entity.Seal;
import br.com.guilhermealvessilve.gamification.domain.seal.vo.SealType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SealFixture {

    public static Seal createSeal() {

        return Seal.withStudentCPFNameAndType("11111111111", "Mathematics I", SealType.BEGINNER);
    }

    public static Seal createSeal2() {
        return Seal.withStudentCPFNameAndType("77777777777", "Mathematics for Seniors", SealType.SENIOR);
    }
}
