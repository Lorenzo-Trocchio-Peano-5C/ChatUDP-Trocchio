/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Asus_X555LD
 */
class Clients {

    int port;
    InetAddress addr;
    String IP_address = "127.0.0.1";

    public Clients(InetAddress addr, int port) throws UnknownHostException {
        this.port = port;
        addr = InetAddress.getByName(IP_address);
    }

    Clients(int port) {
        this.port = port;
    }

}

public class ServerChat extends JFrame implements ActionListener {

    Clients client = new Clients(9999);

    private JTextArea toclient = new JTextArea(1, 1);
    private JTextArea display = new JTextArea(1,1);
    JButton send = new JButton("Send/Start Server");
    DatagramSocket Client, server;
    ServerSocket socket;
    byte[] buffer, buffer1;
    HashMap<String, Clients> clients = new HashMap<String, Clients>();
    String username;
    String messaggio;

    public ServerChat(int port) throws SocketException, UnknownHostException {
        JPanel input = new JPanel();
        input.setLayout(new GridLayout());
        input.setBorder(new TitledBorder("Enter Message"));
        input.add(toclient, BorderLayout.CENTER);
        input.add(send, BorderLayout.EAST);

        JPanel output = new JPanel();
        output.setLayout(new GridLayout());
        output.setBorder(new TitledBorder("Conversation"));
        output.add(display);

        JPanel gabung = new JPanel();
        gabung.setLayout(new GridLayout(2, 1));
        gabung.add(input);
        gabung.add(output);
        buffer = new byte[1024];
        buffer1 = new byte[1024];

        this.getContentPane().add(gabung,new GridLayout(3, 3));
        send.addActionListener(this);

        setTitle("Chat Server");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new DatagramSocket();
                    socket = new ServerSocket();
                    while (true) {
                        socket.accept();
                        DatagramPacket datapack = new DatagramPacket(buffer, buffer.length);
                        server.receive(datapack);
                        String msg = new String(datapack.getData());
                        display.append("\nServer:" + msg);
                    }
                } catch (Exception e) {
                }
            }
        }).start();
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            try {
                String messaggio = toclient.getText();
                buffer = messaggio.getBytes();
                DatagramPacket invio = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 9999);
                server.send(invio);
                display.append("\n Myself:" + messaggio);
                toclient.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
