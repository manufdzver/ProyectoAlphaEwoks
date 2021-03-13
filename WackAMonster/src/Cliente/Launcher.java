package Cliente;

public class Launcher {

    public static void main(String[] args) {

        for(int i=0; i<2; i++){
            ClienteJugador jug1 = new ClienteJugador();
            jug1.start();
        }
    }
}

