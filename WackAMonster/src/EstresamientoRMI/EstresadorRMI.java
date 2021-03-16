package EstresamientoRMI;

public class EstresadorRMI {

    public static void main(String[] args) {
        int n = 500; //Numero de clientes
        int sol = 1000;
        for (int i=0; i<n; i++){
            ThreadRegistro clientThread = new ThreadRegistro(n, sol);
            clientThread.start();
        }
    }
}
