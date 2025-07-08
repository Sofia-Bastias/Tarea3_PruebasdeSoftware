package com.tarea3;

import java.time.LocalDate;
import java.util.Objects;

public class Compra {
    private final String idCompra;
    private final String idCliente;
    private final double monto;
    private final LocalDate fecha;

    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        if(idCompra == null || idCompra.isBlank()) {
            throw new IllegalArgumentException("ID de compra no válido");
        }
        if(idCliente == null || idCliente.isBlank()) {
            throw new IllegalArgumentException("ID de cliente no válido");
        }
        if(monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if(fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha no válida");
        }
        
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Getters
    public String getIdCompra() { return idCompra; }
    public String getIdCliente() { return idCliente; }
    public double getMonto() { return monto; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return idCompra.equals(compra.idCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompra);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra='" + idCompra + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                '}';
    }
}