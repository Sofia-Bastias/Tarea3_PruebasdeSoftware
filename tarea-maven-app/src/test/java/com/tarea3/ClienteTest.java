package com.tarea3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    @Test
    public void testValidarCorreo() {
        Cliente cliente = new Cliente("1", "Juan", "juan@mail.com", 0, Nivel.BRONCE, 0);
        assertTrue(cliente.validarCorreo());
        
        Cliente clienteInvalido = new Cliente("2", "Ana", "ana.mail.com", 0, Nivel.BRONCE, 0);
        assertFalse(clienteInvalido.validarCorreo());
    }
    @Test
    void setNivel_conNivelValido_actualizaCorrectamente() {
        Cliente cliente = new Cliente("1", "Juan", "juan@mail.com", 0, Nivel.BRONCE, 0);
        cliente.setNivel(Nivel.PLATA);
        assertEquals(Nivel.PLATA, cliente.getNivel());
    }

    @Test
    void setNivel_conNivelNulo_lanzaExcepcion() {
        Cliente cliente = new Cliente("1", "Juan", "juan@mail.com", 0, Nivel.BRONCE, 0);
        assertThrows(IllegalArgumentException.class, () -> cliente.setNivel(null));
    }

    @Test
    void setCorreo_conCorreoInvalido_lanzaExcepcion() {
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com", 100, Nivel.BRONCE, 0);
        assertThrows(IllegalArgumentException.class, () -> cliente.setCorreo("correo-invalido"));
    }

    @Test
    void actualizarStreak_conValorNegativo_lanzaExcepcion() {
        Cliente cliente = new Cliente("1", "Luis", "luis@mail.com", 100, Nivel.BRONCE, 0);
    
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> cliente.setStreakDias(-1));
    
        assertEquals("El streak no puede ser negativo", exception.getMessage());
    }
}