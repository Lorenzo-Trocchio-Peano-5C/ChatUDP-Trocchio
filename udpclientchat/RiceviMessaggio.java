/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class RiceviMessaggio implements Runnable {

    DatagramSocket clientSocket;

    RiceviMessaggio(DatagramSocket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[100];
        String received;
        DatagramPacket serverDatagram;

        try {

            serverDatagram = new DatagramPacket(buffer, buffer.length);

            while (!Thread.interrupted()) {
                clientSocket.receive(serverDatagram);
                received = new String(serverDatagram.getData(), 0, serverDatagram.getLength(), "ISO-8859-1");
                System.out.println("--> Server: " + received);
                System.out.print("--> ");
            }

        } catch (Exception ex) {
            Logger.getLogger(RiceviMessaggio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
