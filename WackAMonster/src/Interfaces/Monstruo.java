package Interfaces;

import java.io.Serializable;

public class Monstruo implements Serializable {
    private Jugador unJugador;
    private int numeroDeJuego;
    private int ronda;

    public Monstruo(int numeroDeJuego, int ronda, Jugador unJugador) {
        this.unJugador = null;
        this.numeroDeJuego = numeroDeJuego;
        this.ronda = ronda;
    }

    public Monstruo(){
        this.unJugador = null;
        this.numeroDeJuego = 0;
        this.ronda = 0;
    }

    public int getNumeroDeJuego() {
        return numeroDeJuego;
    }

    public void setNumeroDeJuego(int numeroDeJuego) {
        this.numeroDeJuego = numeroDeJuego;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public Jugador getUnJugador() {
        return unJugador;
    }

    public void setUnJugador(Jugador ganador) {
        this.unJugador = ganador;
    }

}
