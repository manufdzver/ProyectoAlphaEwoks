package Servidor;

import Interfaces.Information;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI implements Information {

    public static void main(String[] args) {

        System.setProperty("java.security.policy", "C:\\Users\\manuf\\OneDrive\\Escritorio\\ProyectoAlphaEwoks\\WackAMonster\\src\\Servidor\\server.policy");
        //System.setProperty("java.security.policy", "/Users/santiagoborobia/Documents/ITAM/Semestre_10/Sistemas_Distribuidos/ProyectoAlphaEwoks/WackAMonster/src/Servidor/server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            //Hay que levantar el servicio
            LocateRegistry.createRegistry(1099);

            String name = "Information";

            //Creo una clase de mi mismo que metere en Registry pero me falta el "traje de servicio"
            ServidorRMI engine = new ServidorRMI();
            //Esto me permite tener el "traje de servicio". Esta listo para ser publicado
            Information stub = (Information) UnicastRemoteObject.exportObject(engine, 0);

            //Ahora hay que crear un vinculo con el registry
            //Aqui obtenemos la referencia al registry porque sabemos qie estamos dentro del ecosistema
            Registry registry = LocateRegistry.getRegistry();

            //Rebind = publicar
            registry.rebind(name, stub);
            System.out.println("Servicio de Información Desplegado");
            //Ya acabo ya lo desplego. Espera que alguien se lo pida

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendIP() throws RemoteException {
        return "localhost";
    }

    @Override
    public String sendIPMulticast() throws RemoteException {
        return "228.5.6.7";
    }

    @Override
    public int sendTcpSocket() throws RemoteException {
        return 7896;
    }

    @Override
    public int sendMulticastSocket() throws RemoteException {
        return 6789;
    }
}
