package Cliente;

import Interfaces.Information;

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
    private static int multicastSocket;

    //Elementos del tablero de juego
    private static JFrame frame = new JFrame("Wack-a-Monster");
    private static JLabel title = new JLabel("Golpea al monstruo cuando aparezca!");
    private static JCheckBox m1, m2, m3, m4, m5, m6, m7, m8, m9;


    public static void tablero(){
        //Instancia la ventana de juego

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        //Titulo de la ventana de juego
        title.setBounds(125,25,250,50);
        frame.add(title);
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
    }
    public static void main(String[] args) {

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
            /*TODO,
               1. Clase jugador para registrar al cliente (int IdJugador, int puntaje)
               2. Conectar el cliente al servidor
               3. Envio de mosntruos multicast
               4. Respuesta del cliente (envia la clase jugador para que el servidor pueda sumarle el punto)
               5. Servidor recibe respuestas y asigan puntos (synchronized)
               6. While en servidor hasta n puntos.
               7. Estresador
             */

            //Crea el tablero de juego
            tablero();

            //TODO AQUI VA TODO EL DESASTRE

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
