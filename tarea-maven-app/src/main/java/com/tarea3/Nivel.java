package com.tarea3;

public enum Nivel {
    BRONCE(1.0),
    PLATA(1.2),
    ORO(1.5),
    PLATINO(2.0);

    private final double multiplicador;

    Nivel(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
}