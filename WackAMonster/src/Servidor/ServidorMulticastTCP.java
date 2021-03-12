package Servidor;

import Interfaces.JuegoWackAMonster;
import Interfaces.Jugador;
import Interfaces.Monstruo;

import java.io.*;
import java.net.*;

public class ServidorMulticastTCP {

    private static JuegoWackAMonster elJuego= new JuegoWackAMonster();
    private static Monstruo elMonstruo = new Monstruo();

    public static void main(String[] args) {

        SenderMoles Sm = new SenderMoles(6789, "228.5.6.7", elJuego, elMonstruo);
        Sm.start();

        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                System.out.println("Waiting for connections...");
                Socket clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                Connection c = new Connection(clientSocket, elJuego);
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
            while (true) {
                String monstruo = "" + (int) (Math.random() * 9 + 1);
                byte[] m = monstruo.getBytes(); //Encapsula el tiempo en un mensaje
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789); //Es datagrama porque es UDP
                s.send(messageOut); //Envia el tiempo a los clientes en el grupo
                System.out.println("Heartbeat");
                Thread.sleep(2000);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    int numeroDeJuego;
    int ronda;

    public Connection(Socket aClientSocket, JuegoWackAMonster unJuego) {
        this.elJuego = unJuego;
        this.numeroDeJuego = 0;
        this.ronda = 0;
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
        try {
            numeroDeJuego = elJuego.getNumeroDeJuego();
            ronda = elJuego.getRonda();
            Jugador unJugador = (Jugador)in.readObject();
            if(unJugador.getNumeroDeJuego()==numeroDeJuego && unJugador.getRonda()==ronda){
                ronda = ronda+1;
                elJuego.setRonda(ronda);
                int puntaje = unJugador.getPuntaje()+1;
                unJugador.setPuntaje(puntaje);

                if(unJugador.getPuntaje() ==3){
                    elJuego.setGanador(unJugador);
                    numeroDeJuego = numeroDeJuego+1;
                    elJuego.setNumeroDeJuego(numeroDeJuego);
                }
            }
            out.writeObject(unJugador);

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



