/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclientchat;

import java.io.IOException;
import java.net.SocketException;

/**
 *
 * @author Asus_X555LD
 */
public class UDPClientChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        String IP_address;
        int UDP_port;
        String richiesta, risposta;
        ClientChat client;

        IP_address = "127.0.0.1";
        UDP_port = 7;
        richiesta = "sessa";

        client = new ClientChat();
        risposta = client.sendAndRecive(richiesta, IP_address, UDP_port);
        System.out.println("ho ricevuto in risposta: " + risposta);
        client.close_socket();
    }

}
