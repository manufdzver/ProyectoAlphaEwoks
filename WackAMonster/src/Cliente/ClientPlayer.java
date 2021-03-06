package Cliente;

import Interfaces.Information;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientPlayer {
    private static String ipMulticast;
    private static String ip;
    private static int tcpSocket;
    private static int multicastSocket;

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "C:\\Users\\manuf\\OneDrive\\Escritorio\\ProyectoAlphaEwoks\\WackAMonster\\src\\Cliente\\client.policy");
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

            //TODO AQUI VA TODO EL DESASTRE


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
