package com.tarea3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ClienteServiceTest {
    private ClienteService service;
    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        service = new ClienteService();
        clienteValido = new Cliente("1", "Luis", "luis@mail.com", 0, Nivel.BRONCE, 0);
    }

    @Test
    void agregarCliente_conDatosValidos_clienteSeAgregaCorrectamente() {
        service.agregarCliente(clienteValido);
        assertEquals(1, service.listarClientes().size());
    }

    @Test
    void agregarCliente_conCorreoInvalido_lanzaExcepcion() {
        Cliente clienteInvalido = new Cliente("2", "Ana", "ana.mail.com", 0, Nivel.BRONCE, 0);
        assertThrows(IllegalArgumentException.class, () -> service.agregarCliente(clienteInvalido));
    }

    @Test
    void agregarCliente_clienteNulo_lanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> service.agregarCliente(null));
    }

    @Test
    void actualizarPuntos_con600Puntos_actualizaNivelAPlata() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 0, Nivel.BRONCE, 0);
        service.agregarCliente(cliente);
        service.actualizarPuntos("1", 600); // Este método llama a actualizarNivel internamente
        assertEquals(Nivel.PLATA, cliente.getNivel());
    }

    @Test
    void eliminarCliente_clienteExistente_eliminaCorrectamente() {
        service.agregarCliente(clienteValido);
        assertTrue(service.eliminarCliente("1"));
        assertEquals(0, service.listarClientes().size());
    }

    @Test
    void actualizarCorreo_conCorreoValido_actualizaCorrectamente() {
        service.agregarCliente(clienteValido);
        service.actualizarCorreo("1", "nuevo@mail.com");
        assertEquals("nuevo@mail.com", clienteValido.getCorreo());
    }
}