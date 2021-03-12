package Servidor;

import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.IOException;
import java.net.*;
import java.util.Date;

public class ServidorMulticastTCP {

    public static void main(String[] args) {
        SenderMoles Sm = new SenderMoles(6789, "228.5.6.7");
        Sm.start();
    }
}

class SenderMoles extends Thread {
    private int port;
    private String address;

    public SenderMoles(int s, String group) {
        this.port = s;
        this.address = group;
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
