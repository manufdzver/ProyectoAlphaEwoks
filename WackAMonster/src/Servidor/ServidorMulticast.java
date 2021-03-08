package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Date;

public class ServidorMulticast {

    public static void main(String[] args) {
        MulticastSocket s = null;
        InetAddress group = null;

        try {

            group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);

            while (true) {
                String monstruo = ""+(int)(Math.random()*9+1);
                byte[] m = monstruo.getBytes(); //Encapsula el tiempo en un mensaje
                DatagramPacket messageOut =
                        new DatagramPacket(m, m.length, group, 6789); //Es datagrama porque es UDP
                s.send(messageOut); //Envia el tiempo a los clientes en el grupo
                System.out.println("Heartbeat");
                Thread.sleep(2000);
            }

        }
        catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if(s != null) {
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
