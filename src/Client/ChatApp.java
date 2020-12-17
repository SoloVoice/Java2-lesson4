package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ChatApp extends JFrame {
    private ArrayList<JLabel> messages = new ArrayList<>();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private JTextField messageField = new JTextField();
    private JPanel chatWindow = new JPanel();
    private String userName;

    public ChatApp() {

        setTitle("Chat App Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 600);

        setLayout(new BorderLayout(10,10));

        chatWindow.setLayout(new BoxLayout(chatWindow, BoxLayout.Y_AXIS));
        JScrollPane chatWindowScr = new JScrollPane(chatWindow);

        chatWindowScr.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        messageField.setHorizontalAlignment(JTextField.RIGHT);

        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel message = new JLabel(messageField.getText());
                messages.add(message);
                for (JLabel i : messages) {
                    chatWindow.add(i);
                }
                messageField.setText("");
                messageField.requestFocus();
                setVisible(true);
            }
        });


        JPanel bottomMessagePanel = new JPanel();
        bottomMessagePanel.setLayout(new BorderLayout());
        bottomMessagePanel.setPreferredSize(new Dimension(0, 30));

        add(bottomMessagePanel, BorderLayout.SOUTH);
        add(chatWindowScr, BorderLayout.CENTER);
        for (JLabel i : messages) {
            chatWindow.add(i);
        }
        bottomMessagePanel.add(sendButton, BorderLayout.EAST);
        bottomMessagePanel.add(messageField, BorderLayout.CENTER);

        setVisible(true);

        connect();
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                while (true) {
                    try {
                        String msg = in.readUTF();
                        messages.add(new JLabel(msg));
                        for (JLabel i : messages) {
                            chatWindow.add(i);
                        }
                        setVisible(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            String str = messageField.getText();
            out.writeUTF(str);
            messageField.setText("");
            messageField.requestFocus();
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
