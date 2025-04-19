package Bakeryshop;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeliciouscafeApp extends JFrame {

    private String dbUrl = "jdbc:mysql://localhost:3306/deliciouscafe"; // Change as needed
    private String dbUser = "root"; // Change as needed
    private String dbPassword = ""; // Change as needed

    public DeliciouscafeApp() {
        setTitle("Register - Delicious Cafe777");
        setSize(800, 565);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Registration Form", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Load and scale background image to fit the window size
        ImageIcon originalImageIcon = new ImageIcon("C:\\Users\\Administrator\\Pictures\\shorties.png");
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(800, 565, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        // Set the scaled image as background
        JLabel background = new JLabel(scaledImageIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Register panel setup
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new GridBagLayout());

        // UI Components
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(Color.BLACK);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setForeground(Color.BLACK);

        JTextField emailField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.BLACK);

        JPasswordField passwordField = new JPasswordField(15);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmPasswordLabel.setForeground(Color.BLACK);

        JPasswordField confirmPasswordField = new JPasswordField(15);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setForeground(new Color(0, 128, 0));

        // GridBagLayout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        registerPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        registerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        registerPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);

        // Add the panel to the frame
        add(registerPanel);

        // Event handling for registration
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Input Validations
                if (!validateInputs(email, password, confirmPassword)) {
                    return; // Registration will not proceed if validation fails
                }

                // Check if the email already exists
                if (emailExists(email)) {
                    JOptionPane.showMessageDialog(null, "This email address is already registered. Please use a different email.", "Registration Error", JOptionPane.WARNING_MESSAGE);
                    return; // Prevent registration if email exists
                }

                // Insert into database
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

                    String insertQuery = "INSERT INTO user (email, password) VALUES (?, ?)";
                    PreparedStatement insertStatement = con.prepareStatement(insertQuery);

                    insertStatement.setString(1, email);
                    insertStatement.setString(2, password);

                    int rowsAffected = insertStatement.executeUpdate();
                    con.close();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration Failed.\nPlease try again or contact support.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    // Method to check if email already exists in the database
    private boolean emailExists(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            con.close();

            return count > 0; // Return true if email exists
        } catch (Exception e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false;
        }
    }

    // Method to validate inputs
    private boolean validateInputs(String email, String password, String confirmPassword) {
        String trimmedEmail = email != null ? email.trim() : "";
        String trimmedPassword = password != null ? password.trim() : "";
        String trimmedConfirmPassword = confirmPassword != null ? confirmPassword.trim() : "";

        if (trimmedEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.\nEnsure it follows the format: example@domain.com", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (trimmedPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (trimmedPassword.length() < 8 || 
                   !trimmedPassword.matches(".*[A-Z].*") || 
                   !trimmedPassword.matches(".*[a-z].*") || 
                   !trimmedPassword.matches(".*[0-9].*") || 
                   !trimmedPassword.matches(".*[!@#$%^&()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long,\ninclude at least one uppercase letter,\none lowercase letter,\none number, and one special character.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (trimmedConfirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please confirm your password.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!trimmedPassword.equals(trimmedConfirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true; // If all validations pass
    }

    public static void main(String[] args) {
        new DeliciouscafeApp().setVisible(true);
    }
}
