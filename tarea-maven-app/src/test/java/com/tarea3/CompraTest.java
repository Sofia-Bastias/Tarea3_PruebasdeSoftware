package com.tarea3;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CompraTest {
    // Datos de prueba reutilizables
    private final String ID_VALIDO = "COMP-001";
    private final String ID_CLIENTE_VALIDO = "CLI-001";
    private final double MONTO_VALIDO = 100.50;
    private final LocalDate FECHA_VALIDA = LocalDate.now();

    //Casos válidos
    @Test
    public void cuandoConstructorConDatosValidos_entoncesCreaInstancia() {
        Compra compra = new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);

        assertAll(
            () -> assertEquals(ID_VALIDO, compra.getIdCompra()),
            () -> assertEquals(ID_CLIENTE_VALIDO, compra.getIdCliente()),
            () -> assertEquals(MONTO_VALIDO, compra.getMonto()),
            () -> assertEquals(FECHA_VALIDA, compra.getFecha())
        );
    }

    // Casos inválidos
    @Test
    public void cuandoIdCompraEsNulo_entoncesLanzaExcepcion() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Compra(null, ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA)
        );
    }

    @Test
    public void cuandoMontoEsNegativo_entoncesLanzaExcepcion() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, -50.0, FECHA_VALIDA)
        );
    }

    @Test
    public void cuandoFechaEsFutura_entoncesLanzaExcepcion() {
        LocalDate fechaFutura = LocalDate.now().plusDays(1);
        assertThrows(
            IllegalArgumentException.class,
            () -> new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, MONTO_VALIDO, fechaFutura)
        );
    }

    //Prueba de igualdad y hashCode
    @Test
    public void cuandoComprasMismoId_entoncesSonIguales() {
        Compra compra1 = new Compra(ID_VALIDO, "CLI-001", 100.0, FECHA_VALIDA);
        Compra compra2 = new Compra(ID_VALIDO, "CLI-002", 200.0, FECHA_VALIDA.minusDays(1));

        assertEquals(compra1, compra2);
        assertEquals(compra1.hashCode(), compra2.hashCode());
    }

    @Test
    public void cuandoComprasDistintoId_entoncesNoSonIguales() {
        Compra compra1 = new Compra("COMP-001", ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);
        Compra compra2 = new Compra("COMP-002", ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);

        assertNotEquals(compra1, compra2);
    }

    // Prueba de toString
    @Test
    public void cuandoToString_entoncesFormatoCorrecto() {
        Compra compra = new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);
        String resultado = compra.toString();

        assertAll(
            () -> assertTrue(resultado.contains(ID_VALIDO)),
            () -> assertTrue(resultado.contains(ID_CLIENTE_VALIDO)),
            () -> assertTrue(resultado.contains(String.valueOf(MONTO_VALIDO))),
            () -> assertTrue(resultado.contains(FECHA_VALIDA.toString()))
        );
    }

    //Pruebas adicionales
    @Test
    public void cuandoComparaConNull_entoncesNoSonIguales() {
        Compra compra = new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);
        assertNotEquals(null, compra);
    }

    @Test
    public void cuandoComparaConOtraClase_entoncesNoSonIguales() {
        Compra compra = new Compra(ID_VALIDO, ID_CLIENTE_VALIDO, MONTO_VALIDO, FECHA_VALIDA);
        assertNotEquals("Texto", compra);
    }
}