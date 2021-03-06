package br.com.guilhermealvessilve.academic.application.indication.indicate.converter;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.com.guilhermealvessilve.academic.application.fixture.IndicationDTOFixture.createIndicationDTO;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class IndicationDTOConverterTest {

    private final IndicationDTOConverter converter;

    @Inject
    IndicationDTOConverterTest(final IndicationDTOConverter converter) {
        this.converter = converter;
    }

    @Test
    void shouldConvertIndication() {

        final var indication = converter.convert(createIndicationDTO());
        final var indicator = indication.getIndicator();
        final var indicated = indication.getIndicated();
        assertAll(
                () -> assertEquals(indicator.getCpf().getDocument(), "33333333333", "indicator.cpf"),
                () -> assertEquals(indicator.getName(), "Jose Napoles", "indicator.name"),
                () -> assertEquals(indicator.getEmail().getAddress(), "jose.napoles@gmail.com", "indicator.email"),
                () -> assertNull(indicator.getPassword(), "indicator.password"),
                () -> assertTrue(indicator.getPhones().isEmpty(), "indicator.phones.isEmpty"),
                () -> assertEquals(indicated.getCpf().getDocument(), "77777777777", "indicated.cpf"),
                () -> assertEquals(indicated.getName(), "Joe Gans", "indicated.name"),
                () -> assertEquals(indicated.getEmail().getAddress(), "joe.gans@outlook.com", "indicated.email"),
                () -> assertNull(indicated.getPassword(), "indicated.password"),
                () -> assertTrue(indicated.getPhones().isEmpty(), "indicated.phones.isEmpty")
        );
    }
}