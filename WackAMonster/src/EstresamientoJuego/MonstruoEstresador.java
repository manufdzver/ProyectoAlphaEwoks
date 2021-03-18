package EstresamientoJuego;

import Interfaces.Jugador;

import java.io.Serializable;

public class MonstruoEstresador implements Serializable {
    private Jugador unJugador;
    private int numeroDeJuego;
    private int ronda;
    private long tiempoRecepcion;
    private long tiempoNacimiento;

    public MonstruoEstresador(int numeroDeJuego, int ronda, Jugador unJugador, long tmp, long nac) {
        this.unJugador = null;
        this.numeroDeJuego = numeroDeJuego;
        this.ronda = ronda;
        this.tiempoRecepcion = tmp;
        this.tiempoNacimiento = nac;
    }

    public long getTiempoNacimiento() {
        return tiempoNacimiento;
    }

    public void setTiempoNacimiento(long tiempoNacimiento) {
        this.tiempoNacimiento = tiempoNacimiento;
    }

    public MonstruoEstresador(){
        this.unJugador = null;
        this.numeroDeJuego = 0;
        this.ronda = 0;
    }

    public long getTiempoRecepcion() {
        return tiempoRecepcion;
    }

    public void setTiempoRecepcion(long tiempo) {
        this.tiempoRecepcion = tiempo;
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
