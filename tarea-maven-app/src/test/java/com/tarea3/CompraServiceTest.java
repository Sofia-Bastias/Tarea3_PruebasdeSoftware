package com.tarea3;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompraServiceTest {
    private ClienteService clienteService = new ClienteService();
    private CompraService compraService = new CompraService(clienteService);

    @Test
    public void testCalcularPuntosBronce() {
        Cliente cliente = new Cliente("1", "Juan", "juan@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
        
        Compra compra = new Compra("C1", "1", 450, LocalDate.now());
        int puntos = compraService.calcularPuntos(compra);
        assertEquals(4, puntos); // 450 / 100 = 4.5 → redondeo a 4
    }

    @Test
    public void testCalcularPuntosPlata() {
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com", 600, Nivel.PLATA, 0);
        clienteService.agregarCliente(cliente);
        
        Compra compra = new Compra("C1", "1", 500, LocalDate.now());
        int puntos = compraService.calcularPuntos(compra);
        assertEquals(6, puntos); // 500 / 100 = 5 → 5 × 1.2 (Plata) = 6
    }
}