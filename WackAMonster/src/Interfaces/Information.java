package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Information extends Remote{
    public String sendIP() throws RemoteException;
    public String sendIPMulticast() throws RemoteException;
    public int sendTcpSocket() throws RemoteException;
    public int sendMulticastSocket() throws RemoteException;
}
