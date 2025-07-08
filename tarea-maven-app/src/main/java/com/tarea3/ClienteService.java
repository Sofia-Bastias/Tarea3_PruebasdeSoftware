package com.tarea3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClienteService {
    // Lista inmutable para evitar modificaciones externas
    private final List<Cliente> clientes = new ArrayList<>();

    /**
     * Agrega un nuevo cliente validando sus datos
     * @param cliente El cliente a agregar
     * @throws IllegalArgumentException si el correo es inválido o el cliente ya existe
     */
    public void agregarCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        
        if (!validarCorreo(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo inválido");
        }
        
        if (existeCliente(cliente.getId())) {
            throw new IllegalArgumentException("Cliente ya existe");
        }
        
        clientes.add(cliente);
    }


    /**
     * @return Lista inmutable de clientes
     */
    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(clientes);
    }

    /**
     * Busca un cliente por ID
     * @param idCliente ID del cliente a buscar
     * @return El cliente encontrado
     * @throws IllegalArgumentException si el cliente no existe
     */
    public Cliente buscarCliente(String idCliente) {
        return clientes.stream()
                .filter(c -> c.getId().equals(idCliente))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    /**
     * Actualiza los puntos de un cliente y recalcula su nivel
     * @param idCliente ID del cliente
     * @param puntos Puntos a añadir (pueden ser negativos)
     */
    public void actualizarPuntos(String idCliente, int puntos) {
        Cliente cliente = buscarCliente(idCliente);
        cliente.setPuntos(cliente.getPuntos() + puntos);
        actualizarNivel(cliente);
    }

    /**
     * Elimina un cliente
     * @param idCliente ID del cliente a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarCliente(String idCliente) {
        return clientes.removeIf(c -> c.getId().equals(idCliente));
    }

    // Métodos de actualización específicos
    public void actualizarCorreo(String idCliente, String nuevoCorreo) {
        if (!validarCorreo(nuevoCorreo)) {
            throw new IllegalArgumentException("Correo inválido");
        }
        buscarCliente(idCliente).setCorreo(nuevoCorreo);
    }

    public void actualizarNombre(String idCliente, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        buscarCliente(idCliente).setNombre(nuevoNombre);
    }

    /**
     * Actualización combinada de varios campos
     */
    public void actualizarCliente(String idCliente, String nuevoNombre, String nuevoCorreo) {
        Cliente cliente = buscarCliente(idCliente);
        
        if (nuevoNombre != null) {
            actualizarNombre(idCliente, nuevoNombre);
        }
        
        if (nuevoCorreo != null) {
            actualizarCorreo(idCliente, nuevoCorreo);
        }
    }

    // Métodos auxiliares
    public boolean existeCliente(String idCliente) {
        return clientes.stream().anyMatch(c -> c.getId().equals(idCliente));
    }

    private boolean validarCorreo(String correo) {
        return correo != null && correo.contains("@");
    }

    public void actualizarNivel(Cliente cliente) {
        int puntos = cliente.getPuntos();
        Nivel nuevoNivel = determinarNivel(puntos);
        cliente.setNivel(nuevoNivel);
    }

    private Nivel determinarNivel(int puntos) {
        if (puntos >= 3000) return Nivel.PLATINO;
        if (puntos >= 1500) return Nivel.ORO;
        if (puntos >= 500) return Nivel.PLATA;
        return Nivel.BRONCE;
    }
}