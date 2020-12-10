import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatApp extends JFrame {
    protected ArrayList<JLabel> messages = new ArrayList<>();

    public ChatApp() {

        setTitle("Chat App Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 600);

        setLayout(new BorderLayout(10,10));

        JPanel chatWindow = new JPanel();
        chatWindow.setLayout(new BoxLayout(chatWindow, BoxLayout.Y_AXIS));
        JScrollPane chatWindowScr = new JScrollPane(chatWindow);
        chatWindowScr.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JTextField messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel message = new JLabel(messageField.getText());
                messages.add(message);
                for (JLabel i : messages) {
                    chatWindow.add(i);
                }
                messageField.setText("");
                setVisible(true);
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
    }

}
