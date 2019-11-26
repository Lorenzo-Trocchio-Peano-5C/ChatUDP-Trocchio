/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPServer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class ServerUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception, InterruptedException {
        // TODO code application logic here
        new Runnable() {
            @Override
            public void run() {
                try {
                    new ServerClasse(80);
                } catch (Exception ex) {
                    Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.run();
    }

}
