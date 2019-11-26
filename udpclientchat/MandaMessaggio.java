/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class MandaMessaggio implements Runnable {

    DatagramSocket clientSocket;
    InetAddress indirizzo;
    int porta;

    MandaMessaggio(DatagramSocket socket, InetAddress address, int port) {
        this.clientSocket = socket;
        this.indirizzo = address;
        this.porta = port;
    }

    /**
     *
     */
    @Override
    public void run() {

        byte[] buffer;
        String messaggio;
        Scanner input = new Scanner(System.in);
        DatagramPacket userDatagram;

        try {
            System.out.print("> ");
            do {

                messaggio = input.nextLine();

                buffer = messaggio.getBytes("UTF-8");

                userDatagram = new DatagramPacket(buffer, buffer.length, indirizzo, porta);

                clientSocket.send(userDatagram);
            } while (messaggio.compareTo("esci") != 0);
        } catch (Exception ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
