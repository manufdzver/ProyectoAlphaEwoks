package Interfaces;

import Interfaces.Jugador;

import java.util.Hashtable;

public class JuegoWackAMonster {
    private int numeroDeJuego;
    private int ronda;
    private Jugador ganador;
    private Hashtable<String, Jugador> jugadores;

    public JuegoWackAMonster() {
        this.numeroDeJuego = 0;
        this.ganador = null;
        this.jugadores = new Hashtable<String, Jugador>();
        this.ronda = 0;
    }

    public synchronized int getRonda() {
        return ronda;
    }

    public synchronized void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public synchronized int getNumeroDeJuego() {
        return numeroDeJuego;
    }

    public synchronized void setNumeroDeJuego(int numeroDeJuego) {
        this.numeroDeJuego = numeroDeJuego;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public Hashtable<String, Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Hashtable<String, Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
