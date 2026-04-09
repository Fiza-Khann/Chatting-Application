package chatting.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessPortal implements ActionListener {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    AccessPortal() {
        loginFrame = new JFrame("Access Portal");
        loginFrame.setSize(470, 700);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);

        ImageIcon backgroundIcon1 = new ImageIcon(ClassLoader.getSystemResource("background.jpg"));
        Image backgroundImg1 = backgroundIcon1.getImage().getScaledInstance(470, 650, Image.SCALE_DEFAULT);
        ImageIcon backgroundImage1 = new ImageIcon(backgroundImg1);
        JLabel background1 = new JLabel(backgroundImage1);
        background1.setBounds(0, 0, 470, 650);
        loginFrame.getContentPane().add(background1);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 220, 100, 35);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Impact", Font.PLAIN, 15));
        background1.add(usernameLabel);

        ImageIcon welcomeIcon = new ImageIcon(ClassLoader.getSystemResource("icons/rfblogo.png"));
        Image welcomeImg = welcomeIcon.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        JLabel welcomeLabel = new JLabel(new ImageIcon(welcomeImg));
        welcomeLabel.setBounds(110, 50, 250, 150);  // Adjust the size and position as needed
        background1.add(welcomeLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 220, 190, 30);
        background1.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 270, 200, 35);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Impact", Font.PLAIN, 15));
        background1.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 270, 190, 30);
        background1.add(passwordField);

        loginButton = new JButton("Sign-in");
        loginButton.setBounds(170, 360, 130, 35);
        loginButton.addActionListener(this);
        background1.add(loginButton);

        JLabel sloganLabel = new JLabel("Your best chatting platform from RFB programmers");
        sloganLabel.setBounds(40, 410, 450, 60);
        sloganLabel.setForeground(Color.BLACK);
        sloganLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        background1.add(sloganLabel);

        loginFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("rfb") && password.equals("bse2c")) {
                loginFrame.setVisible(false);
                showChatInterface();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showChatInterface() {
        loginFrame.dispose();
        new ChatWindow();
    }

    public static void main(String[] args) {
        new AccessPortal();
    }
}
