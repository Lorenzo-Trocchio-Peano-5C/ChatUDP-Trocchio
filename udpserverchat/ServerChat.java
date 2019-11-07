/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserverchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class ServerChat implements Runnable {

    private DatagramSocket socket;

    public ServerChat(int porta) throws SocketException {
        socket = new DatagramSocket(porta);
    }

    public void run() {
        DatagramPacket risposta;
        byte[] buffer = new byte[8192];
        // creo un un datagramma UDP a partire da buffer
        DatagramPacket richiesta = new DatagramPacket(buffer, buffer.length);
        while (!Thread.interrupted()) {
            try {
                socket.receive(richiesta);
                // prendo i dati ricevuti e costruisco la risposta
                risposta = new DatagramPacket(richiesta.getData(), richiesta.getLength(), richiesta.getAddress(), richiesta.getPort());
                socket.send(risposta);
                
            } catch (IOException ex) {
                Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
