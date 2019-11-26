/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus_X555LD
 */
public class ClientUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        new Runnable() {
            @Override
            public void run() {
                try {
                    new ClientClasse();
                } catch (Exception ex) {
                    Logger.getLogger(ClientClasse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.run();

    }

}
