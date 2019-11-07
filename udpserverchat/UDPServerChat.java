/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserverchat;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class UDPServerChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        int c;
        
        
        Thread thread;
        try {

            ServerChat echoServer = new ServerChat(7);
            thread = new Thread(echoServer);

            thread.start();
            c = System.in.read();

            thread.interrupt();

            thread.join();
            System.out.println("sono il main");
            for (;;) {

            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
