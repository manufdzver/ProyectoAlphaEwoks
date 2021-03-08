package Cliente;

import Interfaces.Information;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class ClientPlayer {
    //Direcciones IP y puertos para conectarse al servidor
    private static String ipMulticast;
    private static String ip;
    private static int tcpSocket;
    private static int multicastSocket = 0;

    //Elementos del tablero de juego
    private static JLabel title  = new JLabel();
    private static JLabel lblJugador  = new JLabel();
    private static JLabel lblPuntaje = new JLabel();
    private static JCheckBox m1, m2, m3, m4, m5, m6, m7, m8, m9;
    private static ActionListener golpeMonstruo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Todo: Mandar mensaje a servidor para competir por el punto
        }
    };

    //Nombre del juagdor:
    private static Jugador jugador;

    public static void tablero(){
        //Crea la ventana de juego
        JFrame frame = new JFrame("Wack-a-Monster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        //Titulo de la ventana de juego
        lblJugador.setText("Jugador: "+ jugador.getNombre());
        lblJugador.setBounds(125,10,300,20);
        lblPuntaje.setText("Puntaje: "+ jugador.getPuntaje());
        lblPuntaje.setBounds(125,30,200,20);
        title.setText("¡Golpea al monstruo cuando aparezca!");
        title.setBounds(125,80,250,20);
        frame.add(title);
        frame.add(lblJugador);
        frame.add(lblPuntaje);

        //Crea la ubicacion de los monstruos
        m1 = new JCheckBox("M1");
        m1.setBounds(120,100, 70,50);
        m2 = new JCheckBox("M2");
        m2.setBounds(120,150, 70,50);
        m3 = new JCheckBox("M3");
        m3.setBounds(120,200, 70,50);
        m4 = new JCheckBox("M4");
        m4.setBounds(220,100, 70,50);
        m5 = new JCheckBox("M5");
        m5.setBounds(220,150, 70,50);
        m6 = new JCheckBox("M6");
        m6.setBounds(220,200, 70,50);
        m7 = new JCheckBox("M7");
        m7.setBounds(320,100, 70,50);
        m8 = new JCheckBox("M8");
        m8.setBounds(320,150, 70,50);
        m9 = new JCheckBox("M9");
        m9.setBounds(320,200, 70,50);
        m1.setSelected(true);
        frame.add(m1);
        frame.add(m2);
        frame.add(m3);
        frame.add(m4);
        frame.add(m5);
        frame.add(m6);
        frame.add(m7);
        frame.add(m8);
        frame.add(m9);

        frame.setLayout(null);
        frame.setVisible(true);

        //Inicia el jeugo
        MulticastSocket s = null;

        try {
            InetAddress group = InetAddress.getByName(ipMulticast); // destination multicast group
            s = new MulticastSocket(multicastSocket);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
//Todo, resolver problema del ciclo con el JFrame
            s.receive(messageIn);//Es bloqueante
            String monster = new String(messageIn.getData());
            System.out.println("Monstruo recibido en la posicion: " + monster+ " from: "+ messageIn.getAddress());
            setMonster(monster);

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

    public static void setMonster(String mons){
        int pos = Integer.parseInt(mons.trim());
        m1.setSelected(false);
        m2.setSelected(false);
        m3.setSelected(false);
        m4.setSelected(false);
        m5.setSelected(false);
        m6.setSelected(false);
        m7.setSelected(false);
        m8.setSelected(false);
        m9.setSelected(false);
        switch (pos){
            case 1:
                m1.setSelected(true);
                break;
            case 2:
                m2.setSelected(true);
                break;
            case 3:
                m3.setSelected(true);
                break;
            case 4:
                m4.setSelected(true);
                break;
            case 5:
                m5.setSelected(true);
                break;
            case 6:
                m6.setSelected(true);
                break;
            case 7:
                m7.setSelected(true);
                break;
            case 8:
                m8.setSelected(true);
                break;
            case 9:
                m9.setSelected(true);
                break;
        }
    }

    public static void nuevoJugador(){
        JFrame frame = new JFrame("Wack-a-Monster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        //Pide el nombre al jugador desde la GUI
        title.setText("Ingresa tu nombre: ");
        title.setBounds(100,25,200,25);
        JTextField inputNombre = new JTextField();
        inputNombre.setBounds(225,25,125,25);
        frame.add(inputNombre);
        frame.add(title);

        //Registra al juagdor al hacer click en el boton
        JButton registrar = new JButton("Registrar jugador");
        registrar.setBounds(130,75,200,25);
        frame.add(registrar);
        registrar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jugador = new Jugador(inputNombre.getText());
                getInformation();
                frame.dispose();
                tablero();
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);

    }

    public static void getInformation(){
        //System.setProperty("java.security.policy", "C:\\Users\\manuf\\OneDrive\\Escritorio\\ProyectoAlphaEwoks\\WackAMonster\\src\\Cliente\\client.policy");
        System.setProperty("java.security.policy", "/Users/santiagoborobia/Documents/ITAM/Semestre_10/Sistemas_Distribuidos/ProyectoAlphaEwoks/WackAMonster/src/Servidor/server.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        //Quieres buscar al servicio con el nombre que metimos en servidor
        String name = "Information";
        Registry registry = null; // server's ip address
        try {
            //Contactar al RMI register y decirle que voy a buscar a compute y que me de el stub para contactarlo
            registry = LocateRegistry.getRegistry("localhost");
            //Tal cual lo buscamos
            Information info = (Information) registry.lookup(name);

            ipMulticast = info.sendIPMulticast();
            ip = info.sendIP();
            tcpSocket = info.sendTcpSocket();
            multicastSocket = info.sendMulticastSocket();

            System.out.println("Recibi todo" + ipMulticast);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

        //Crea al jugador y lo registra recibiendo las IP's y los puertos del servidor RMI
        nuevoJugador();

            /*TODO,
               4. Respuesta del cliente (envia la clase jugador para que el servidor pueda sumarle el punto)
               5. Servidor recibe respuestas y asigna puntos (synchronized)
               6. While en servidor hasta n puntos.
               7. Estresador
             */




            //TODO AQUI VA TODO EL DESASTRE



    }
}
