package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        setLayout(new FlowLayout());
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);

        setLocationRelativeTo(null); 
    }

    // Retourne le nom d'utilisateur saisi
    public String getUsername() {
        return usernameField.getText();
    }

    // Retourne le mot de passe saisi
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Ajoute un ActionListener au bouton de connexion
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    // Affiche un message d'erreur dans une fenêtre pop-up
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Ferme la fenêtre de login
    public void close() {
        this.setVisible(false);
    }
}


