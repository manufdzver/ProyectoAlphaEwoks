package Servidor;

import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.Date;

public class ServidorMulticastTCP {

    private JuegoWackAMonster elJuego= new JuegoWackAMonster();

    public void main(String[] args) {
        SenderMoles Sm = new SenderMoles(6789, "228.5.6.7", elJuego);
        Sm.start();
    }
}

class SenderMoles extends Thread {
    private int port;
    private String address;
    private JuegoWackAMonster elJuego;

    public SenderMoles(int s, String group, JuegoWackAMonster unJuego) {
        this.port = s;
        this.address = group;
        this.elJuego = unJuego;
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
                Thread.sleep(5000);
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
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket, JuegoWackAMonster unJuego) {
        this.elJuego = unJuego;
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            in.read()
            addressBook = new AddressBook();
            int key = 0;
            while (key != 8) {
                key = in.readInt();
                if (key != 8) {
                    System.out.println("Request received from: " + clientSocket.getRemoteSocketAddress());
                    out.writeUTF(addressBook.getRecord(key).getName());
                }
            }
            System.out.println("OUT");
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}



