
package com.tarea3;

public class Cliente {
    private final String id;  // Si el ID es inmutable
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;

    // Constructor
    public Cliente(String id, String nombre, String correo, int puntos, Nivel nivel, int streakDias) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = puntos;
        this.nivel = nivel;
        this.streakDias = streakDias;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }
    public int getStreakDias() { return streakDias; }

    //setters
    public void setNivel(Nivel nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("El nivel no puede ser nulo");
        }
        this.nivel = nivel;
    }

    // Setters con validación/lógica adicional
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido");
        }
        this.correo = correo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
        actualizarNivel();
    }

    public void setStreakDias(int streakDias) {
        if (streakDias < 0) {
            throw new IllegalArgumentException("El streak no puede ser negativo");
        }
        this.streakDias = streakDias;
    }

    private void actualizarNivel() {
        if (puntos >= 3000) {
            nivel = Nivel.PLATINO;
        } else if (puntos >= 1500) {
            nivel = Nivel.ORO;
        } else if (puntos >= 500) {
            nivel = Nivel.PLATA;
        } else {
            nivel = Nivel.BRONCE;
        }
    }

    // Validación de correo (opcional, si ya está en el setter)
    public boolean validarCorreo() {
        return correo != null && correo.contains("@");
    }
}