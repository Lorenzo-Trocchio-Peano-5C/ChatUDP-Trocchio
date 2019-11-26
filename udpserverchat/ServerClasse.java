/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Asus_X555LD
 */
class Clients {

    int porta;
    InetAddress indirizzo;
    String IP = "127.0.0.1";

    public Clients(InetAddress addr, int port) throws UnknownHostException {
        this.porta = port;
        this.indirizzo = addr;
    }

    Clients(int port) {
        this.porta = port;
    }

}

public class ServerClasse extends JFrame implements ActionListener {

    Clients client = new Clients(InetAddress.getByName("127.0.0.1"), 8080);
    private JTextArea JTADisplay = new JTextArea();
    DatagramSocket socketClient, socketServer;
    byte[] buffer;
    ArrayList<String> mex = new ArrayList<>();
    String messaggio;
    private HashMap<InetAddress, Integer> HMclientPort = new HashMap<>();

    public ServerClasse(int port) throws SocketException, UnknownHostException {
        JTADisplay.setEditable(false);

        JPanel JPOutput = new JPanel();
        JPOutput.setLayout(new BorderLayout());
        JPOutput.setBorder(new TitledBorder("Cronologia"));
        JPOutput.add(JTADisplay, BorderLayout.NORTH);

        JPanel JPPannello = new JPanel();
        JPPannello.setLayout(new GridLayout(1, 1));
        JPPannello.add(JPOutput);
        buffer = new byte[1024];

        this.getContentPane().add(JPPannello, BorderLayout.CENTER);

        setTitle("Chat Server");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketServer = new DatagramSocket(8080);
                    while (true) {
                        DatagramPacket richiesta = new DatagramPacket(buffer, buffer.length);
                        socketServer.receive(richiesta);
                        String msg = new String(richiesta.getData());
                        StringTokenizer st = new StringTokenizer(msg, "/");
                        String messaggio = st.nextToken();
                        String user = st.nextToken();
                        JTADisplay.append("\nMessaggio ricevuto: " + messaggio + "; Username: " + user + " --> Da: " + richiesta.getAddress() + ":" + richiesta.getPort());
                        socketServer.send(new DatagramPacket(richiesta.getData(), richiesta.getLength(), richiesta.getAddress(), richiesta.getPort()));
                        JTADisplay.append("\nMessaggio inviato: " + messaggio + "; Username: " + user + " --> Da: " + richiesta.getAddress() + ":" + richiesta.getPort());
                        if (!HMclientPort.containsKey(richiesta.getAddress()) && !HMclientPort.containsValue(richiesta.getPort())) {
                            JTADisplay.append(" -> IP non presente. Salvataggio in corso.");
                            HMclientPort.put(richiesta.getAddress(), richiesta.getPort());
                            if (mex.size() > 10) {
                                for (int i = mex.size() - 11; i < mex.size(); i++) {
                                    byte[] buf = mex.get(i).getBytes();
                                    DatagramPacket listaMSG = new DatagramPacket(buf, buf.length, richiesta.getAddress(), richiesta.getPort());
                                    socketServer.send(listaMSG);
                                    JTADisplay.append("\nHo inviato:" + new String(buf) + " a: " + richiesta.getAddress() + " " + richiesta.getPort());
                                }
                            } else {
                                for (int i = 0; i < mex.size(); i++) {
                                    byte[] buf = mex.get(i).getBytes();
                                    DatagramPacket ultimiMSG = new DatagramPacket(buf, buf.length, richiesta.getAddress(), richiesta.getPort());
                                    socketServer.send(ultimiMSG);
                                    JTADisplay.append("\nOutput:" + new String(buf) + " a: " + richiesta.getAddress() + " " + richiesta.getPort());
                                }
                            }
                        }
                        mex.add(msg);
                    }
                } catch (Exception ex) {
                }
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
