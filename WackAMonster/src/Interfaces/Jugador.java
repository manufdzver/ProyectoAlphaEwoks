package Interfaces;

import java.io.Serializable;
import java.util.Objects;

public class Jugador implements Serializable {

    private String nombre;
    private int numeroDeJuego;
    private int ronda;


    public Jugador(){
        this.nombre = null;
        this.numeroDeJuego = 0;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.numeroDeJuego = 0;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public int getNumeroDeJuego() {
        return numeroDeJuego;
    }


    public void setNumeroDeJuego(int juego) {
        this.numeroDeJuego = numeroDeJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Jugador{" +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(nombre, jugador.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, numeroDeJuego, ronda);
    }
}
