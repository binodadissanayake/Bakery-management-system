package Bakeryshop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Exploremore.Product;
import Exploremore.productView;
import Login.LoginView;

public class Homepage extends JFrame {
    private GraphicsConfiguration opacity;

    public Homepage() {
        setTitle("Bakery Interface");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background image label setup
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Administrator\\Downloads\\Rectangle 10 (1).png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        // Title Label Configuration
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBounds(20, 20, 300, 100);

        JLabel titleLabel = new JLabel("<html>Sweet Treats,<br>Baked fresh</html>");
        titleLabel.setFont(new Font("Serif", Font.ITALIC, 40));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        backgroundLabel.add(titlePanel);

        // Buttons Panel Configuration
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBounds(20, 150, 200, 300);

        String[] buttonNames = { "Top products", "Explore more", "Menu", "Order List", "Payment" };
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(199, 158, 0));
            button.setForeground(Color.BLACK);

            // Add action listener for "Top products" button
            if (name.equals("Top products")) {
                button.addActionListener(e -> new productgrid1()); // Show the Top Products interface
            }
            // Add action listener for "Explore more" button
            else if (name.equals("Explore more")) {
                button.addActionListener(e -> {
                    productView productview = new productView(); // Create a new instance
                    productview.setVisible(true); // Ensure visibility
                });
            }

            // Add action listener for "Menu" button
            else if (name.equals("Menu")) {
                button.addActionListener(e -> new BakeryMenu()); // Show the Menu interface
            }

            // Add action listener for "Order List" button
            else if (name.equals("Order List")) {
                button.addActionListener(e -> new Orderlist()); // Show the Order List interface
            }

            // Add action listener for "Payment" button
            else if (name.equals("Payment")) {
                button.addActionListener(e -> new PaymentForm(opacity)); // Corrected to directly instantiate PaymentForm
            }

            buttonsPanel.add(button);
        }
        backgroundLabel.add(buttonsPanel);

        // Footer Label Configuration for Time
        JLabel footerLabel = new JLabel("Daily open 7 am - 8 pm", SwingConstants.LEFT);
        footerLabel.setFont(new Font("Georgia", Font.ITALIC, 18));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setBounds(20, 460, 200, 30);
        backgroundLabel.add(footerLabel);

        // Login and Register Buttons
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.setOpaque(false);
        loginPanel.setBounds(getWidth() - 200, 20, 180, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(199, 158, 0));
        loginButton.addActionListener(e -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true); // Ensure visibility
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(199, 158, 0));
        registerButton.addActionListener(e -> {
            DeliciouscafeApp registerView = new DeliciouscafeApp();
            registerView.setVisible(true); // Ensure the window is visible
        });

        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        backgroundLabel.add(loginPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Homepage::new);
    }
}
