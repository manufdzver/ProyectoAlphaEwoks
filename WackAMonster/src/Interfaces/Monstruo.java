package Interfaces;

public class Monstruo {
    private int posicion;
    private Jugador ganador;
    private int numeroDeJuego;
    private int ronda;

    public Monstruo(int posicion, int numeroDeJuego, int ronda) {
        this.posicion = posicion;
        this.ganador = null;
        this.numeroDeJuego = numeroDeJuego;
        this.ronda = ronda;
    }

    public Monstruo(){
        this.posicion = 0;
        this.ganador = null;
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

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public void resetGanador(Jugador ganador) {
        this.ganador = null;
    }

}
