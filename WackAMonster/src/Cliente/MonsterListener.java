package Cliente;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MonsterListener extends Thread{

    private JCheckBox posiciones[];
    private int selectedPos = -1;
    private String ipMulticast;
    private int multicastSocket;

    public MonsterListener(JCheckBox pos[], String ip, int socket ){
        this.posiciones = pos;
        this.ipMulticast = ip;
        this.multicastSocket = socket;
    }

    @Override
    public void run() {
        //Inicia el jeugo
        MulticastSocket s = null;

        try {
            InetAddress group = InetAddress.getByName(ipMulticast); // destination multicast group
            s = new MulticastSocket(multicastSocket);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

            while(true) {
                s.receive(messageIn);//Es bloqueante
                String newMonster = new String(messageIn.getData());
                newMonster = newMonster.trim();
                System.out.println("Monstruo recibido en la posicion: " + newMonster + " from: " + messageIn.getAddress());
                setMonster(newMonster);
            }

            //s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null)
                s.close();
        }
    }

    public void setMonster(String monster){
        int newPos = Integer.parseInt(monster);

        if(selectedPos != -1){
            posiciones[selectedPos-1].setSelected(false);
        }
        posiciones[newPos-1].setSelected(true);
        selectedPos = newPos;

    }
}
