package com.tarea3;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompraServiceTest {
    private ClienteService clienteService = new ClienteService();
    private CompraService compraService = new CompraService(clienteService);


    // Tests para calcularPuntos
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

    @Test
    void testCalcularPuntos_BronceLevel() {
        ClienteService clienteServiceMock = mock(ClienteService.class);
        CompraService compraService = new CompraService(clienteServiceMock);
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com", 0, Nivel.BRONCE, 0);
        when(clienteServiceMock.buscarCliente("1")).thenReturn(cliente);
    
        Compra compra = new Compra("C1", "1", 500.0, LocalDate.now());
        assertEquals(5, compraService.calcularPuntos(compra)); // 500/100 * 1 (multiplicador BRONCE)
    }

    @Test
    void calcularPuntos_conMontoMinimoPositivo_devuelvePuntosBasicos() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        // Monto positivo más pequeño (1)
        Compra compra = new Compra("C1", "1", 1, LocalDate.now());
        assertEquals(0, compraService.calcularPuntos(compra)); // 1/100 = 0.01 → truncado a 0
    }

    @Test
    void calcularPuntos_conMontoNegativo_lanzaExcepcion() {
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);

        // Verifica que la creación de Compra con monto negativo lance excepción
        assertThrows(IllegalArgumentException.class, 
            () -> new Compra("C1", "1", -100.0, LocalDate.now()));
    }

    @Test
    void calcularPuntos_conMontoCero_lanzaExcepcion() {
        // Verifica que la creación de Compra con monto cero lance excepción
        assertThrows(IllegalArgumentException.class, 
            () -> new Compra("C1", "1", 0, LocalDate.now()));
    }



    // Tests para aplicarBonusStreak

    @Test
    void testAplicarBonusStreak_3ComprasMismoDia() {
        ClienteService clienteService = new ClienteService();
        CompraService compraService = new CompraService(clienteService);
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        LocalDate hoy = LocalDate.now();
        compraService.agregarCompra(new Compra("C1", "1", 100.0, hoy));
        compraService.agregarCompra(new Compra("C2", "1", 100.0, hoy));
        compraService.agregarCompra(new Compra("C3", "1", 100.0, hoy));
    
        compraService.aplicarBonusStreak("1", hoy);
        assertEquals(10, cliente.getPuntos()); // +10 pts por streak
    }

    @Test
    void aplicarBonusStreak_conMenosDe3Compras_noAplicaBonus() {
        Cliente cliente = new Cliente("1", "Carlos", "carlos@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        LocalDate hoy = LocalDate.now();
        compraService.agregarCompra(new Compra("C1", "1", 100.0, hoy));
        compraService.agregarCompra(new Compra("C2", "1", 100.0, hoy));
    
        compraService.aplicarBonusStreak("1", hoy);
        assertEquals(0, cliente.getPuntos()); // No debe aplicar bonus
    }

    @Test
    void aplicarBonusStreak_conComprasEnDiasDiferentes_noAplicaBonus() {
        Cliente cliente = new Cliente("1", "Maria", "maria@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        compraService.agregarCompra(new Compra("C1", "1", 100.0, LocalDate.now()));
        compraService.agregarCompra(new Compra("C2", "1", 100.0, LocalDate.now().minusDays(1)));
        compraService.agregarCompra(new Compra("C3", "1", 100.0, LocalDate.now().minusDays(2)));
    
        compraService.aplicarBonusStreak("1", LocalDate.now());
        assertEquals(0, cliente.getPuntos());
    }

    @Test
    void aplicarBonusStreak_con2ComprasMismoDia_noAplicaBonus() {
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        LocalDate hoy = LocalDate.now();
        compraService.agregarCompra(new Compra("C1", "1", 100.0, hoy));
        compraService.agregarCompra(new Compra("C2", "1", 100.0, hoy));
    
        compraService.aplicarBonusStreak("1", hoy);
        assertEquals(0, cliente.getPuntos()); // No cumple el mínimo de 3 compras
    }

    // Tests para getComprasPorCliente

    @Test
    void getComprasPorCliente_conClienteSinCompras_devuelveListaVacia() {
        Cliente cliente = new Cliente("1", "Pedro", "pedro@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente);
    
        assertTrue(compraService.getComprasPorCliente("1").isEmpty());
    }

    @Test
    void getComprasPorCliente_conMultiplesClientes_devuelveSoloComprasDelCliente() {
        // Configuración
        Cliente cliente1 = new Cliente("1", "Cliente1", "cliente1@mail.com", 0, Nivel.BRONCE, 0);
        Cliente cliente2 = new Cliente("2", "Cliente2", "cliente2@mail.com", 0, Nivel.BRONCE, 0);
        clienteService.agregarCliente(cliente1);
        clienteService.agregarCliente(cliente2);
    
        // Compras
        compraService.agregarCompra(new Compra("C1", "1", 100.0, LocalDate.now()));
        compraService.agregarCompra(new Compra("C2", "2", 200.0, LocalDate.now()));
        compraService.agregarCompra(new Compra("C3", "1", 300.0, LocalDate.now()));
    
        // Verificación
        assertEquals(2, compraService.getComprasPorCliente("1").size());
        assertEquals(1, compraService.getComprasPorCliente("2").size());
    }




}