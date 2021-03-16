package EstresamientoJuego;

import Interfaces.JuegoWackAMonster;
import Interfaces.Jugador;
import Interfaces.Monstruo;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;

public class ServidorEstresamiento {

    private static JuegoWackAMonster elJuego= new JuegoWackAMonster();
    private static Monstruo elMonstruo = new Monstruo();
    private static HashMap<Jugador, Integer> puntajes = new HashMap<Jugador, Integer>();

    public static void main(String[] args) {

        SenderMoles Sm = new SenderMoles(6789, "228.5.6.7", elJuego, elMonstruo);
        Sm.start();

        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                //System.out.println("Waiting for connections...");
                Socket clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                Connection c = new Connection(clientSocket, elJuego, puntajes);
                c.start();
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

class SenderMoles extends Thread {
    private int port;
    private String address;
    private JuegoWackAMonster elJuego;
    private Monstruo unMonstruo;

    public SenderMoles(int s, String group, JuegoWackAMonster unJuego, Monstruo elMonstruo) {
        this.port = s;
        this.address = group;
        this.elJuego = unJuego;
        this.unMonstruo = elMonstruo;
    }

    public void run() {
        InetAddress group = null;
        MulticastSocket s = null;
        try {
            group = InetAddress.getByName(address);
            s = new MulticastSocket(port);
            s.joinGroup(group);
            String monstruo = null;
            while (true) {
                monstruo = null;
                if(elJuego.getGanador()!= null){
                    monstruo = "";
                    monstruo = "" + 10 +","+elJuego.getGanador().getNombre() + ", ";
                    elJuego.setGanador(null);

                }
                else{
                    monstruo = "";
                    monstruo = "" + (int) (Math.random() * 9 + 1)+","+elJuego.getNumeroDeJuego()+","+elJuego.getRonda();
                }
                byte[] m = monstruo.getBytes(); //Encapsula el tiempo en un mensaje
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789); //Es datagrama porque es UDP
                s.send(messageOut); //Envia el tiempo a los clientes en el grupo
                //System.out.println("Heartbeat");
                Thread.sleep(2000);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.leaveGroup(group); //Se sale del grupo y deja de escuchar el socket (Hay que salirse antes de cerrar el socket)
                } catch (IOException e) {
                    e.printStackTrace();
                }
                s.close();
            }
        }

    }
}

class Connection extends Thread {
    private JuegoWackAMonster elJuego;
    private HashMap<Jugador, Integer> puntajes;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket, JuegoWackAMonster unJuego, HashMap losPuntajes) {
        this.elJuego = unJuego;
        this.puntajes = losPuntajes;
        try {
            clientSocket = aClientSocket;
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        int puntos = 0;

        try {
            Monstruo unMonstruo = (Monstruo)in.readObject();
            Jugador unJugador = unMonstruo.getUnJugador();

            if(unMonstruo.getNumeroDeJuego() == elJuego.getNumeroDeJuego() && unMonstruo.getRonda()== elJuego.getRonda()){
                elJuego.setRonda(elJuego.getRonda()+1);
                if(!puntajes.containsKey(unJugador)){
                    puntajes.put(unJugador,0);
                }
                puntos = puntajes.get(unJugador);
                puntos = puntos+1;

                if(puntos>=3){
                    elJuego.setGanador(unJugador);
                    elJuego.setNumeroDeJuego(elJuego.getNumeroDeJuego()+1);
                    elJuego.setRonda(0);
                    puntajes.clear();
                    System.out.println("Ganador:" + unJugador.getNombre());
                }
                else{
                    puntajes.remove(unJugador);
                    puntajes.put(unJugador,puntos);
                }
            }
            out.writeObject(unMonstruo);

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}



