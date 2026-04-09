package chatting.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ChatBot implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JScrollPane scrollPane;
    static JFrame f = new JFrame();

    ChatBot() {
        f.setLayout(null);
        f.setTitle("RFB");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/chatbot1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/Dots.png"));
        Image i14 = i13.getImage().getScaledInstance(30, 23, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        morevert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                int confirm = JOptionPane.showConfirmDialog(f, "Are you sure you want to clear all messages?", "Clear Messages", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    clearMessages();
                }
            }
        });
        p1.add(morevert);

        JLabel name = new JLabel("Royal AI");
        name.setBounds(110, 15, 100, 25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Helvetica", Font.BOLD, 20));
        p1.add(name);

        JLabel status = new JLabel("Feel free to start the conversation");
        status.setBounds(110, 38, 250, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Helvetica", Font.PLAIN, 13));
        p1.add(status);

        a1 = new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
        a1.setBackground(Color.BLACK);
        
        scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5, 75, 440, 540);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        f.add(scrollPane);

        text = new JTextField();
        text.setBounds(1, 620, 330, 40);
        text.setFont(new Font("Helvetica", Font.PLAIN, 16));
        text.setText("Type a message");

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (text.getText().isEmpty()) {
                    text.setText("Type a message");
                }
            }

            public void keyTyped(KeyEvent e) {
                if (text.getText().equals("Type a message")) {
                    text.setText("");
                }
            }
        });

        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(330, 620, 70, 40);
        send.setBackground(Color.BLACK);
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Helvetica", Font.PLAIN, 14));
        f.add(send);

        JButton emojiButton = new JButton("😊");
        emojiButton.setBounds(400, 620, 50, 40);
        emojiButton.setBackground(Color.BLACK);
        emojiButton.setForeground(Color.WHITE);
        emojiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmojiPicker emojiPicker = new EmojiPicker(text);
                emojiPicker.setVisible(true);
            }
        });
        f.add(emojiButton);

        f.setSize(470, 700);
        f.setLocation(800, 20);
        f.getContentPane().setBackground(Color.BLACK);

        f.setVisible(true);

        loadMessageHistory();
    }

   private void loadMessageHistory() {
        try (Connection con = getConnection()) {
            if (con != null) {
                String query = "SELECT sender, message FROM messages";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String sender = rs.getString("sender");
                    String message = rs.getString("message");
                    JPanel panel = null;
                    if ("User".equals(sender)) {
                        panel = formatLabel(message, true); 
                    } else if("Bot".equals(sender)){
                        panel = formatLabel(message, false); 
                    }
                    JPanel right = new JPanel(new BorderLayout());
                    JPanel left = new JPanel(new BorderLayout());
                    right.setBackground(Color.BLACK);
                    left.setBackground(Color.BLACK);
                    if ("User".equals(sender)) {
                        right.add(panel, BorderLayout.LINE_END);
                        vertical.add(right);
                    } else if("Bot".equals(sender)) {
                        left.add(panel, BorderLayout.LINE_START);
                        vertical.add(left);
                    }
                }
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                f.validate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/chat_history"; 
            String user = "root"; 
            String password = "Chatapp@bse2c"; 
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Send")) {
            sendMessage();
        }
    }

    private void sendMessage() {
        try {
            String out = text.getText();
            addMessageToPanel("User", out);

            saveMessageToDatabase("User", out);

            String response = getResponse(out);
            addMessageToPanel("Bot", response);

            saveMessageToDatabase("Bot", response);

            text.setText("");

            f.revalidate();
            f.repaint();

            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToDatabase(String sender, String message) {
        try (Connection con = getConnection()) {
            if (con != null) {
                String query = "INSERT INTO messages (sender, message) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, sender);
                pstmt.setString(2, message);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMessageToPanel(String sender, String message) {
        JPanel panel = formatLabel(message, "User".equals(sender));
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.BLACK);
        if ("User".equals(sender)) {
            container.add(panel, BorderLayout.LINE_END);
        } else {
            container.add(panel, BorderLayout.LINE_START);
        }
        vertical.add(container);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        f.revalidate();
        f.repaint();
    }

    public static JPanel formatLabel(String out, boolean sent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Helvetica", Font.PLAIN, 16));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        if (sent) {
            output.setBackground(new Color(0, 150, 230));
            panel.setBackground(Color.BLACK);
        } else {
            output.setBackground(Color.WHITE);
            panel.setBackground(Color.BLACK);
        }

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static String getResponse(String message) {
        switch (message.toLowerCase()) {
            case "hi":
            case "hello":
                return "Hello! How can I assist you?";
            case "how are you?":
                return "I'm just a computer program, but thank you for asking!";
            case "what is your name?":
                return "I'm a Royal AI, here to help you with your queries.";
            case "what is the difference between java and javascript?":
                return "Java is a general-purpose programming language used for building applications, while JavaScript is a scripting language used primarily for creating interactive web content. Despite their similar names, they serve different purposes: Java is used for standalone and enterprise applications, whereas JavaScript is essential for web development.";
            case "what is the capital of pakistan?":
                return "The capital of Pakistan is Islamabad.";
            default:
                return "I'm sorry, I didn't understand that. Can you please rephrase?";
        }
    }
    
     private void clearMessages() {
        a1.removeAll();
        vertical.removeAll();
        a1.revalidate();
        a1.repaint();
        f.revalidate();
        f.repaint();

        try (Connection con = getConnection()) {
            String query = "DELETE FROM messages";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatBot();
    }
}
