package Interfaces;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private int puntaje;
    private int numeroDeJuego;
    private int ronda;


    public Jugador(){
        this.nombre = null;
        this.puntaje = 0;
        this.numeroDeJuego = 0;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
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

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                ", nombre='" + nombre + '\'' +
                ", puntaje=" + puntaje +
                '}';
    }
}
