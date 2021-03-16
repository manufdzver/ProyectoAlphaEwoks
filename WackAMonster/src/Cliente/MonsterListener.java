package Cliente;

import Interfaces.Jugador;
import Interfaces.Monstruo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MonsterListener extends Thread{

    private JCheckBox monsters[];
    private String[] monsterArr;
    private int selectedPos = -1;
    private Monstruo monstruo;
    private Jugador jugador;
    private JLabel ganador;

    private String ipMulticast;
    private int multicastSocket;
    private String ipTCP;
    private int socketTCP;

    public MonsterListener(JCheckBox pos[], String ip, int socket, Jugador jug, String ipTCP, int socketTCP, JLabel ganador ){
        this.monsters = pos;
        this.ipMulticast = ip;
        this.multicastSocket = socket;
        this.jugador =jug;
        this.ipTCP = ipTCP;
        this.socketTCP = socketTCP;
        this.monstruo = new Monstruo();
        this.ganador = ganador;
    }

    @Override
    public void run() {
        //Inicia el jeugo
        ActionListener golpe = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strPos =  (e.getActionCommand()).substring(e.getActionCommand().length()-1);
                int pos = Integer.parseInt(strPos);
                if(monsters[pos-1].isSelected()){
                    System.out.println("Le diste al que no era");
                    monsters[pos-1].setSelected(false);
                }
                else{

                    System.out.println("Mataste al monstruo");
                    int ronda = Integer.parseInt(monsterArr[2].trim());
                    int juego = Integer.parseInt(monsterArr[1].trim());
                    System.out.println("Ronda: "+(monsterArr[2].trim()) +" Juego: "+(monsterArr[1]));
                    monstruo.setRonda(ronda);
                    monstruo.setNumeroDeJuego(juego);
                    monstruo.setUnJugador(jugador);
                    sendGolpe(monstruo);
                }

            }
        };

        for (int i=0; i<9; i++){
            monsters[i].addActionListener(golpe);
        }

        MulticastSocket s = null;

        try {
            InetAddress group = InetAddress.getByName(ipMulticast); // destination multicast group
            s = new MulticastSocket(multicastSocket);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

            while(true) {
                s.receive(messageIn);//Es bloqueante
                //Recibe mosntruo en el formato: "posicion,numeroJuego,numeroRonda"
                String newMonster = new String(messageIn.getData());
                monsterArr = newMonster.split(",");

                if(monsterArr[0].equals("10")) {
                    monsters[selectedPos-1].setSelected(false);
                    ganador.setText("El ganador es: " + monsterArr[1]);
                    Thread.sleep(2000);
                    ganador.setText("Iniciando nuevo juego...");
                    Thread.sleep(2000);
                    ganador.setText("");
                }
                else {
                    setMonster(monsterArr[0]);
                }

            }

            //s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (s != null)
                s.close();
        }
    }

    public void setMonster(String monster){
        int newPos = Integer.parseInt(monster);

        if(selectedPos != -1){
            monsters[selectedPos-1].setSelected(false);
        }
        monsters[newPos-1].setSelected(true);
        selectedPos = newPos;

    }

    public void sendGolpe(Monstruo mons){
        Socket s = null;
        try {
            int serverPort = socketTCP;
            s = new Socket(ipTCP, serverPort);
            //s = new Socket("127.0.0.1", serverPort);

            //Recibe los "caminos" de la conexion que encontro con el servidor
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeObject(mons);
            System.out.println("Message sent");

            //Respuesta:
            //Monstruo mons2 = (Monstruo)in.readObject(); //Casteamos a persona, porque recibe un objeto...
            //System.out.println("Received: " + mons2);
        }
        catch (UnknownHostException e) {
            System.out.println("Sock:"+e.getMessage());
        }
        catch (EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IO:"+e.getMessage());
        } finally {
            if(s!=null)
                try {
                    s.close();
                } catch (IOException e){
                    System.out.println("close:"+e.getMessage());}
        }
    }
}
