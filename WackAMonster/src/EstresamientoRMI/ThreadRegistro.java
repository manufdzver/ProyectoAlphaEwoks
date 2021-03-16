package EstresamientoRMI;

import Interfaces.Information;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ThreadRegistro extends Thread{
    private String ipMulticast;
    private int multicastSocket;
    private String ip;
    private int tcpSocket;

    int nClients;
    int mSol;
    public ThreadRegistro(int n, int s){
        this.nClients = n;
        this.mSol = s;
    }

    public void run(){

        getInformation(); // a traves de RMI

    }

    public void getInformation(){
        long startTime, spentTime;
        long times[] = new long[1000];
        double avgTime = 0;

        //System.setProperty("java.security.policy", "C:\\Users\\manuf\\OneDrive\\Escritorio\\ProyectoAlphaEwoks\\WackAMonster\\src\\Cliente\\client.policy");
        System.setProperty("java.security.policy", "/Users/santiagoborobia/Documents/ITAM/Semestre_10/Sistemas_Distribuidos/ProyectoAlphaEwoks/WackAMonster/src/Servidor/server.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {

            for (int i=0; i<mSol; i++){ //Para cada cliente, se realizan "m" solicitudes

                startTime = System.currentTimeMillis(); //Registra tiempo de entrada

                //Nombre del servidor RMI con la informacion para acceder el juego
                String name = "Information";
                Registry registry = null;


                //Contactar al RMI register
                registry = LocateRegistry.getRegistry("localhost");
                //Busca el los servicios desplegados en "Informacion" para poderlos usar
                Information info = (Information) registry.lookup(name);

                ipMulticast = info.sendIPMulticast();
                ip = info.sendIP();
                tcpSocket = info.sendTcpSocket();
                multicastSocket = info.sendMulticastSocket();

                spentTime = System.currentTimeMillis() - startTime; //Registra tiempo de consulta
                times[i] = spentTime;
                avgTime = avgTime + spentTime;
            }

            avgTime = avgTime/mSol;
            double stdDev = stdDev(times);
            System.out.println("Cltes,"+nClients+",Sols,"+mSol+",Avg. resp time (ms),"+avgTime+",std. Dev,"+stdDev);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
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
