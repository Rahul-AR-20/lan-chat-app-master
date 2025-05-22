package chatapplication;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public SignUpPage() {
        setTitle("Sign Up for ChatApp");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("New Username:");
        userLabel.setBounds(30, 30, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 30, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(30, 70, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 70, 150, 25);
        add(passwordField);

        registerButton = new JButton("Sign Up");
        registerButton.setBounds(140, 110, 150, 30);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/chatdb", "chatuser", "password");


            // Check if user already exists
            PreparedStatement check = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            } else {
                // Insert new user
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Sign up successful!");
                this.dispose();
                new LoginPage().setVisible(true); // go to login screen
            }

            rs.close();
            check.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new SignUpPage().setVisible(true);
    }
}
