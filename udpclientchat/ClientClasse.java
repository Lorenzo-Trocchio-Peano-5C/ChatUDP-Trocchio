/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author Asus_X555LD
 */
public class ClientClasse extends JFrame implements ActionListener {

    private JTextField JTFmex = new JTextField();
    private JTextField JTFUsername = new JTextField();
    private JTextArea JTADisplay = new JTextArea();
    private JButton invio = new JButton("Invio - Client");
    byte[] buffer0, buffer1;
    DatagramSocket clientSocket;
    String messaggio;
    String IP = "127.0.0.1";
    InetAddress indirizzo = InetAddress.getByName(IP);

    public ClientClasse() throws Exception {
        JPanel clientInput = new JPanel();
        clientInput.setLayout(new GridLayout(1, 3));
        clientInput.add(JTFUsername);
        JTFUsername.setText("user");
        clientInput.add(JTFmex);
        JTFmex.setText("mex");
        clientInput.add(invio);

        JPanel clientOutput = new JPanel();
        clientOutput.setLayout(new GridLayout(1, 1));
        clientOutput.add(JTADisplay);

        JPanel JTFPannello = new JPanel();
        JScrollPane JSPScrollo = new JScrollPane(JTFPannello);
        JTFPannello.setLayout(new GridLayout(2, 3));
        this.setLayout(new GridLayout(2, 1));
        this.add(clientInput);
        JTFPannello.add(clientOutput);
        buffer0 = new byte[1024];
        buffer1 = new byte[1024];
        this.add(JSPScrollo);
        invio.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Chat Client");
        setSize(400, 100);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new DatagramSocket();
                    while (true) {
                        DatagramPacket datapack = new DatagramPacket(buffer1, buffer1.length);
                        clientSocket.receive(datapack);
                        String msg = new String(datapack.getData());
                        JTADisplay.append("\nServer:" + msg + "\n");
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(invio)) {
            try {
                String message = JTFmex.getText();
                String username1 = JTFUsername.getText();
                String messaggioFinale = message + "/" + username1;
                buffer1 = messaggioFinale.getBytes();
                DatagramPacket sendpack = new DatagramPacket(buffer1, buffer1.length, InetAddress.getLoopbackAddress(), 8080);
                clientSocket.send(sendpack);
                JTADisplay.append("\nOutput da " + username1 + " Il messaggio: " + message + "\n");
                JTFmex.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
