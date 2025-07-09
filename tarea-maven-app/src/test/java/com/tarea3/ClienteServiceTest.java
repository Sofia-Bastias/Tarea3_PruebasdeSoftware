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

    // Tests para agregarCliente
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
    void agregarCliente_conIdDuplicado_lanzaExcepcion() {
        service.agregarCliente(clienteValido);
        Cliente clienteDuplicado = new Cliente("1", "Ana", "ana@mail.com", 0, Nivel.BRONCE, 0);
        assertThrows(IllegalArgumentException.class, () -> service.agregarCliente(clienteDuplicado));
    }



    // Tests para actualizarPuntos

    @Test
    void actualizarPuntos_con600Puntos_actualizaNivelAPlata() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 0, Nivel.BRONCE, 0);
        service.agregarCliente(cliente);
        service.actualizarPuntos("1", 600); // Este método llama a actualizarNivel internamente
        assertEquals(Nivel.PLATA, cliente.getNivel());
    }

    @Test
    void actualizarPuntos_conPuntosNegativos_actualizaCorrectamente() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 100, Nivel.BRONCE, 0);
        service.agregarCliente(cliente);
        service.actualizarPuntos("1", -50); // 100 - 50 = 50 pts
        assertEquals(50, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel()); // Debe seguir en BRONCE
    }

    @Test
    void actualizarPuntos_con1500Puntos_actualizaNivelAOro() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 0, Nivel.BRONCE, 0);
        service.agregarCliente(cliente);
        service.actualizarPuntos("1", 1500);
        assertEquals(Nivel.ORO, cliente.getNivel());
    }

    @Test
    void actualizarPuntos_conClienteInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> service.actualizarPuntos("99", 100));
    }


    //test para eliminarCliente

    @Test
    void eliminarCliente_clienteExistente_eliminaCorrectamente() {
        service.agregarCliente(clienteValido);
        assertTrue(service.eliminarCliente("1"));
        assertEquals(0, service.listarClientes().size());
    }

    // Tests para actualizarCorreo

    @Test
    void actualizarCorreo_conCorreoValido_actualizaCorrectamente() {
        service.agregarCliente(clienteValido);
        service.actualizarCorreo("1", "nuevo@mail.com");
        assertEquals("nuevo@mail.com", clienteValido.getCorreo());
    }

    @Test
    void actualizarCorreo_conCorreoInvalido_lanzaExcepcion() {
        service.agregarCliente(clienteValido);
        assertThrows(IllegalArgumentException.class, 
            () -> service.actualizarCorreo("1", "correo-invalido"));
    }

    @Test
    void actualizarCorreo_conClienteInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, 
            () -> service.actualizarCorreo("99", "nuevo@mail.com"));
    }



    // Tests para actualizarNombre
    @Test
    void actualizarNombre_conNombreValido_actualizaCorrectamente() {
        service.agregarCliente(clienteValido);
        service.actualizarNombre("1", "Nuevo Nombre");
        assertEquals("Nuevo Nombre", clienteValido.getNombre());
    }

    @Test
    void actualizarNombre_conNombreVacio_lanzaExcepcion() {
        service.agregarCliente(clienteValido);
        assertThrows(IllegalArgumentException.class, 
            () -> service.actualizarNombre("1", ""));
    }

    @Test
    void actualizarNombre_conClienteInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, 
            () -> service.actualizarNombre("99", "Nombre"));
    }

    // Tests para actualizarCliente
    @Test
    void actualizarCliente_actualizarSoloNombre_correoNoCambia() {
        service.agregarCliente(clienteValido);
        service.actualizarCliente("1", "Nuevo Nombre", null);
        assertEquals("Nuevo Nombre", clienteValido.getNombre());
        assertEquals("luis@mail.com", clienteValido.getCorreo()); // Correo original se mantiene
    }

    @Test
    void actualizarCliente_actualizarSoloCorreo_nombreNoCambia() {
        service.agregarCliente(clienteValido);
        service.actualizarCliente("1", null, "nuevo@mail.com");
        assertEquals("Luis", clienteValido.getNombre()); // Nombre original se mantiene
        assertEquals("nuevo@mail.com", clienteValido.getCorreo());
    }


    //Test para buscarCliente
    @Test
    void buscarCliente_conClienteInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarCliente("99"));
    }
}