package Cliente;

import Interfaces.Information;
import Interfaces.Jugador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClienteJugador {

    public static void main(String[] args) {

        //Crea el hilo del jugador
        JugadorThread jugador = new JugadorThread();
        jugador.start();
        JugadorThread jugador2 = new JugadorThread();
        jugador2.start();
            /*TODO,
               1. Revisar como jugar con más de un jugador en la misma compu
               2. Respuesta del cliente (envia la clase jugador para que el servidor pueda sumarle el punto)
               3. Servidor recibe respuestas y asigna puntos (synchronized)
               4. While en servidor hasta n puntos.
               5. Estresador
             */
    }
}

class JugadorThread extends Thread{

    //Direcciones IP y puertos para conectarse al servidor
    private String ipMulticast;
    private int multicastSocket;
    private String ip;
    private int tcpSocket;

    //Clase Jugador para el cliente:
    private Jugador jugador;

    public void getInformation(){
        System.setProperty("java.security.policy", "C:\\Users\\manuf\\OneDrive\\Escritorio\\ProyectoAlphaEwoks\\WackAMonster\\src\\Cliente\\client.policy");
        //System.setProperty("java.security.policy", "/Users/santiagoborobia/Documents/ITAM/Semestre_10/Sistemas_Distribuidos/ProyectoAlphaEwoks/WackAMonster/src/Servidor/server.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //Nombre del servidor RMI con la informacion para acceder el juego
        String name = "Information";
        Registry registry = null;
        try {
            //Contactar al RMI register
            registry = LocateRegistry.getRegistry("localhost");
            //Busca el los servicios desplegados en "Informacion" para poderlos usar
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

    public void run(){
        JFrame frame = new JFrame("Wack-a-Monster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        //Pide el nombre al jugador desde la GUI
        JLabel title  = new JLabel();
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

    public void tablero() {
        //Crea la ventana de juego
        JFrame frame = new JFrame("Wack-a-Monster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        //Titulo de la ventana de juego
        JLabel title  = new JLabel();
        JLabel lblJugador  = new JLabel();
        JLabel lblPuntaje  = new JLabel();

        lblJugador.setText("Jugador: " + jugador.getNombre());
        lblJugador.setBounds(125, 10, 300, 20);
        lblPuntaje.setText("Puntaje: " + jugador.getPuntaje());
        lblPuntaje.setBounds(125, 30, 200, 20);
        title.setText("¡Golpea al monstruo cuando aparezca!");
        title.setBounds(125, 80, 250, 20);
        frame.add(title);
        frame.add(lblJugador);
        frame.add(lblPuntaje);

        //Crea la ubicacion de los monstruos
        JCheckBox monsters[] = new JCheckBox[9];
        for(int i=0; i<9; i++){
            monsters[i] = new JCheckBox("M"+(i+1));
        }
        //Fija la posicion de los hoyos de los monstruos
        monsters[0].setBounds(120,100, 70,50);
        monsters[1].setBounds(120,150, 70,50);
        monsters[2].setBounds(120,200, 70,50);

        monsters[3].setBounds(220,100, 70,50);
        monsters[4].setBounds(220,150, 70,50);
        monsters[5].setBounds(220,200, 70,50);

        monsters[6].setBounds(320,100, 70,50);
        monsters[7].setBounds(320,150, 70,50);
        monsters[8].setBounds(320,200, 70,50);

        for(int i=0; i<9; i++){
            frame.add(monsters[i]);
        }
        frame.setLayout(null);
        frame.setVisible(true);

        MonsterListener multicastListener = new MonsterListener(monsters, ipMulticast, multicastSocket);
        multicastListener.start();
    }

}
