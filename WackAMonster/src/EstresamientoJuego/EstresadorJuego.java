package EstresamientoJuego;


public class EstresadorJuego {

    public static void main(String[] args) {
        int n = 500; //Numero de clientes
        int rondas = 1;
        String nombre;
        for (int i=0; i<n; i++){
            nombre = "Jugador" + i;
            ClienteEstresador clientThread = new ClienteEstresador(rondas, n, nombre);
            clientThread.start();
        }
    }
}
