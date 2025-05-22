package chatapplication;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        setTitle("Login to ChatApp");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 150, 25);
        add(passwordField);

// Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 100, 150, 30);
        add(loginButton);

// ✅ Connect the login button to the authentication logic
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser(); // <<-- Important!
            }
        });



// ✅ Sign Up Button (Add below login button)
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(120, 150, 150, 30);
        add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUpPage().setVisible(true);
            }
        });


    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/chatdb", "chatuser", "password");


            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM user WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose(); // Close login window
                new ChatApp().setVisible(true); // Open chat app
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}
