package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class LoginModel {
    private String email;
    private String password;

    private String dbUrl = "jdbc:mysql://localhost:3306/deliciouscafe"; // Update if needed
    private String dbUser = "root"; // Update if needed
    private String dbPassword = ""; // Update if needed

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login() {
        // Input Validations
        if (!validateInputs()) {
            return false; // Authentication will not proceed if validation fails
        }

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Prepare the SQL query to check credentials
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true; // Authentication successful
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.", "Login Error", JOptionPane.WARNING_MESSAGE);
                    return false; // Authentication failed
                }
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            return false;
        }
    }

    private boolean validateInputs() {
        // Get trimmed inputs
        String trimmedEmail = email != null ? email.trim() : "";
        String trimmedPassword = password != null ? password.trim() : "";

        // Validate email
        if (trimmedEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.\nEnsure it follows the format: example@domain.com", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate password
        if (trimmedPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true; // If all validations pass
    }
}
