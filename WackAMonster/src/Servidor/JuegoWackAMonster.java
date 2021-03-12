package Servidor;

import Cliente.Jugador;

import java.util.Hashtable;

public class JuegoWackAMonster {
    private int numeroDeJuego;
    private String ganador;
    private Hashtable<String, Jugador> jugadores;

    public JuegoWackAMonster() {
        this.numeroDeJuego = 0;
        this.ganador = null;
        this.jugadores = new Hashtable<String, Jugador>();
    }

    public int getNumeroDeJuego() {
        return numeroDeJuego;
    }

    public void setNumeroDeJuego(int numeroDeJuego) {
        this.numeroDeJuego = numeroDeJuego;
    }

    public synchronized String getGanador() {
        return ganador;
    }

    public synchronized void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public Hashtable<String, Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Hashtable<String, Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
