/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Asus_X555LD
 */
public class ChatClient extends JFrame implements ActionListener {

    private JTextArea toclient = new JTextArea(1, 1);
    private JTextArea display = new JTextArea(1, 1);
    private JButton send = new JButton("Send/Start Client");
    byte[] buffer, buffer1;
    DatagramSocket client;
    ServerSocket socket;
    String username;
    String messaggio;
    String IP_address = "127.0.0.1";
    InetAddress address = InetAddress.getByName(IP_address);

    public ChatClient() throws SocketException, UnknownHostException {
        JPanel input = new JPanel();
        input.setLayout(new BorderLayout());
        input.setBorder(new TitledBorder("Enter Message"));
        input.add(toclient, BorderLayout.CENTER);
        input.add(send, BorderLayout.EAST);

        JPanel output = new JPanel();
        output.setLayout(new BorderLayout());
        output.setBorder(new TitledBorder("Conversation"));
        output.add(display, BorderLayout.CENTER);

        JPanel gabung = new JPanel();
        gabung.setLayout(new GridLayout(2, 1));
        gabung.add(input);
        gabung.add(output);
        buffer = new byte[1024];
        buffer1 = new byte[1024];

        this.getContentPane().add(gabung, BorderLayout.NORTH);
        send.addActionListener(this);

        setTitle("Chat Client");
        setSize(500, 300);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new DatagramSocket();
                    socket = new ServerSocket();
                    while (true) {
                        DatagramPacket datapack = new DatagramPacket(buffer1, buffer1.length);
                        socket.accept();
                        client.receive(datapack);
                        String msg = new String(datapack.getData());
                        display.append("\nServer:" + msg);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            try {
                String messaggio = toclient.getText();
                buffer1 = messaggio.getBytes();
                DatagramPacket sendpack = new DatagramPacket(buffer1, buffer1.length, client.getInetAddress(), 9998);
                client.send(sendpack);
                display.append("\nIO:" + messaggio);
                toclient.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
