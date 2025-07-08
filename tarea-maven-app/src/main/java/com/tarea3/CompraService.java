package com.tarea3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompraService {
    private ClienteService clienteService;
    private List<Compra> compras = new ArrayList<>();

    public CompraService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    public List<Compra> getCompras() {
        return Collections.unmodifiableList(compras);
    }

    public List<Compra> getComprasPorCliente(String idCliente) {
    return compras.stream()
            .filter(c -> c.getIdCliente().equals(idCliente))
            .toList();
    }

    public int calcularPuntos(Compra compra) {
        Cliente cliente = clienteService.buscarCliente(compra.getIdCliente());
        double monto = compra.getMonto();
        int puntosBase = (int) (monto / 100);
        double multiplicador = cliente.getNivel().getMultiplicador();
        return (int) (puntosBase * multiplicador);
    }

    // Bonus de 3 compras en un día (+10 pts)
    public void aplicarBonusStreak(String idCliente, LocalDate fecha) {
        long comprasHoy = compras.stream()
                .filter(c -> c.getIdCliente().equals(idCliente) && c.getFecha().equals(fecha))
                .count();
        if (comprasHoy >= 3) {
            Cliente cliente = clienteService.buscarCliente(idCliente);
            cliente.setPuntos(cliente.getPuntos() + 10);
        }
    }
}