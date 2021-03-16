package EstresamientoJuego;

import Interfaces.Jugador;
import Interfaces.Monstruo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ClienteEstresador extends Thread{


    private Jugador jugador;
    private String[] monsterArr;
    private Monstruo monstruo;
    private int rondas;
    private int nClients;

    public ClienteEstresador(int rondas, int cltes, String nom){
        this.jugador = new Jugador(nom);
        this.rondas = rondas;
        this.nClients = cltes;
        this.monstruo = new Monstruo();
    }

    @Override
    public void run() {

        long startTime, spentTime;
        long times[] = new long[1000];
        double avgTime = 0;

        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group
            s = new MulticastSocket(6789);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            int i = 0;

            while(i < rondas) {
                startTime = System.currentTimeMillis();
                s.receive(messageIn);//Es bloqueante
                String newMonster = new String(messageIn.getData());
                monsterArr = newMonster.split(",");

                if(monsterArr[0].equals("10")) {
                    //Alguien gano el juego y se inicia uno nuevo
                }
                else {
                    int ronda = Integer.parseInt(monsterArr[2].trim());
                    int juego = Integer.parseInt(monsterArr[1].trim());
                    monstruo.setRonda(ronda);
                    monstruo.setNumeroDeJuego(juego);
                    monstruo.setUnJugador(jugador);
                    sendGolpe(monstruo);
                }
                spentTime = System.currentTimeMillis() - startTime; //Registra tiempo de consulta
                times[i] = spentTime;
                avgTime = avgTime + spentTime;

                i++;
            }
            avgTime = avgTime/rondas;
            double stdDev = stdDev(times);
            System.out.println("Cltes,"+nClients+",Sols,"+rondas+",Avg. resp time (ms),"+avgTime+",std. Dev,"+stdDev);
            s.leaveGroup(group);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null)

                s.close();
        }
    }


    public void sendGolpe(Monstruo mons){


        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort);

            //Recibe los "caminos" de la conexion que encontro con el servidor
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeObject(mons);
            Monstruo respuesta = (Monstruo) in.readObject();

        }
        catch (UnknownHostException e) {
            System.out.println("Sock:"+e.getMessage());
        }
        catch (EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IO:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(s!=null)
                try {
                    s.close();
                } catch (IOException e){
                    System.out.println("close:"+e.getMessage());}
        }
    }

    private double stdDev(long[] list){
        double sum = 0.0;
        double num= 0.0;
        for (int i=0; i< list.length; i++)
            sum+=list[i];
        double mean = sum/list.length;
        for (int i=0; i<list.length; i++)
            num+=Math.pow((list[i] -mean),2);

        return Math.sqrt(num/list.length);
    }
}
