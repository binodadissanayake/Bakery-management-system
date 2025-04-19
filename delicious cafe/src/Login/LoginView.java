package Login;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

import Bakeryshop.DeliciouscafeApp;

import java.io.File;
import java.io.IOException;

public class LoginView extends JFrame {
    private JTextField emailTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JLabel backgroundLabel;
    private JLayeredPane layeredPanel;

    public LoginView() {
        // Set frame properties
        setTitle("Delicious Cafe - Login");
        setSize(800, 565);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Change to DISPOSE_ON_CLOSE to not exit the app
        setLayout(null);

        setLocationRelativeTo(null);




        // Layered Pane to Control Layering
        layeredPanel = new JLayeredPane();
        layeredPanel.setBounds(0, 0, 800, 565);
        add(layeredPanel);

        // Background Image Label
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 800, 565);
        layeredPanel.add(backgroundLabel, Integer.valueOf(0));

        // Set initial background image
        setBackgroundImage("C:\\Users\\Administrator\\Pictures\\shorties.png");

        // Panel for Login Form
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(424, 110, 300, 350);
        loginPanel.setOpaque(false);
        layeredPanel.add(loginPanel, Integer.valueOf(1));

        JLabel signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 25));
        signInLabel.setForeground(Color.BLACK);
        signInLabel.setBounds(10, 0, 280, 30);
        loginPanel.add(signInLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(10, 60, 280, 25);
        loginPanel.add(emailLabel);

        emailTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailTextField.setBounds(10, 90, 280, 35);
        emailTextField.setBackground(Color.WHITE);
        loginPanel.add(emailTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(10, 130, 280, 30);
        loginPanel.add(passwordLabel);

        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(10, 160, 280, 35);
        passwordField.setBackground(Color.WHITE);
        loginPanel.add(passwordField);

        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(10, 250, 280, 40);
        loginButton.setBackground(new Color(212, 218, 46));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBounds(10, 300, 280, 40);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(0, 128, 128));
        registerButton.setFocusPainted(false);
        loginPanel.add(registerButton);

        registerButton.addActionListener(e -> {
            new DeliciouscafeApp(); // Open the registration view
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setBackgroundImage("C:\\Users\\Administrator\\Pictures\\bakary1.png");
            }
        });
    }

    private void setBackgroundImage(String imagePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            int width = layeredPanel.getWidth();
            int height = layeredPanel.getHeight();
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledImage));
            backgroundLabel.setBounds(0, 0, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getEmail() {
        return emailTextField.getText();
    }

    public String getPassword() {
    	return new String(passwordField.getPassword());
    }


    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
