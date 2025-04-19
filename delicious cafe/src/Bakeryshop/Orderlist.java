package Bakeryshop;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Orderlist extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/deliciouscafe"; // Update your DB URL
    private static final String DB_USER = "root"; // Update your DB username
    private static final String DB_PASSWORD = ""; // Update your DB password

    // Create a field for the main panel and deleted items
    private JPanel mainPanel;
    private ArrayList<String[]> deletedItems; // Store deleted items information

    public Orderlist() {
        setTitle("Order List");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        deletedItems = new ArrayList<>(); // Initialize the deleted items list

        // Header Panel to hold Back Button and Title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(158, 151, 85));

        // Back Arrow Button
        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Back button clicked"));
        headerPanel.add(backButton, BorderLayout.WEST);

        // Title Label
        JLabel titleLabel = new JLabel("Order List", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBackground(new Color(158, 151, 85)); // Olive color background

        // Adding each item with circular image
        mainPanel.add(createOrderItem("Chocolate Cake", "Rs.3500.00", "C:\\Users\\Administrator\\Pictures\\choco.jpg"));
        mainPanel.add(createOrderItem("Fish Rolls", "Rs.100.00", "C:\\Users\\Administrator\\Pictures\\rolls.jpg"));
        mainPanel.add(createOrderItem("Sausage Bun", "Rs.90.00", "C:\\Users\\Administrator\\Pictures\\sausage.jpg"));
        mainPanel.add(createOrderItem("Mango Juice", "Rs.960.00", "C:\\Users\\Administrator\\Pictures\\mango.jpg"));
        mainPanel.add(createOrderItem("Watermelon Juice", "Rs.300.00", "C:\\Users\\Administrator\\Pictures\\water.jpg"));

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createOrderItem(String itemName, String itemPrice, String imagePath) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout(10, 10));
        itemPanel.setBackground(new Color(158, 151, 85));

        // Load and set circular image
        ImageIcon icon = getCircularImageIcon(imagePath, 60);
        JLabel imageLabel = new JLabel(icon);
        itemPanel.add(imageLabel, BorderLayout.WEST);

        // Item name and price
        JLabel nameLabel = new JLabel("<html><b>" + itemName + "</b><br>" + itemPrice + "</html>");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        itemPanel.add(nameLabel, BorderLayout.CENTER);

        // Quantity selector
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout());

        JButton minusButton = new JButton("-");
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        JButton plusButton = new JButton("+");

        quantityPanel.add(minusButton);
        quantityPanel.add(quantitySpinner);
        quantityPanel.add(plusButton);
        itemPanel.add(quantityPanel, BorderLayout.EAST);

        // Action listeners for quantity buttons
        minusButton.addActionListener(e -> {
            int currentQty = (int) quantitySpinner.getValue();
            if (currentQty > 1) {
                quantitySpinner.setValue(currentQty - 1);
            }
        });

        plusButton.addActionListener(e -> {
            int currentQty = (int) quantitySpinner.getValue();
            quantitySpinner.setValue(currentQty + 1);
        });

        // Panel for Delete and Update Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteItem(itemName, itemPanel, nameLabel));
        buttonPanel.add(deleteButton);

        // Restore Button
        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(e -> restoreItem(itemName, itemPrice, imagePath, itemPanel));
        buttonPanel.add(restoreButton);

        // Update Button
        JButton updateButton = new JButton("Update Price");
        updateButton.addActionListener(e -> {
            String newPriceStr = JOptionPane.showInputDialog("Enter new price:");
            if (newPriceStr != null && !newPriceStr.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(newPriceStr);
                    updateItemPrice(itemName, newPrice, nameLabel);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price format.");
                }
            }
        });
        buttonPanel.add(updateButton);

        itemPanel.add(buttonPanel, BorderLayout.SOUTH);

        return itemPanel;
    }

    private ImageIcon getCircularImageIcon(String imagePath, int diameter) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circularImage.createGraphics();
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
            g2.drawImage(originalImage, 0, 0, diameter, diameter, null);
            g2.dispose();

            return new ImageIcon(circularImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteItem(String itemName, JPanel itemPanel, JLabel nameLabel) {
        String deleteQuery = "DELETE FROM `order` WHERE item_name = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(deleteQuery)) {
            statement.setString(1, itemName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Item deleted successfully.");
                // Save item details for restoration
                String itemPrice = nameLabel.getText().replaceAll("<html><b>" + itemName + "</b><br>", "").replaceAll("</html>", "");
                deletedItems.add(new String[]{itemName, itemPrice});
                // Remove the item panel from the main panel
                mainPanel.remove(itemPanel);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Item not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting item: " + ex.getMessage());
        }
    }

    private void restoreItem(String itemName, String itemPrice, String imagePath, JPanel itemPanel) {
        String restoreQuery = "INSERT INTO `order` (item_name, price) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(restoreQuery)) {
            statement.setString(1, itemName);
            statement.setString(2, itemPrice.replace("Rs.", "").trim()); // Convert price string to double
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Item restored successfully.");
                // Optionally, re-add the item to the interface
                mainPanel.add(createOrderItem(itemName, itemPrice, imagePath));
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Error restoring item.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error restoring item: " + ex.getMessage());
        }
    }

    private void updateItemPrice(String itemName, double newPrice, JLabel nameLabel) {
        String updateQuery = "UPDATE `order` SET price = ? WHERE item_name = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = con.prepareStatement(updateQuery)) {
            statement.setDouble(1, newPrice);
            statement.setString(2, itemName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Item updated successfully.");
                // Update the displayed price in the UI
                nameLabel.setText("<html><b>" + itemName + "</b><br>Rs." + newPrice + "</html>");
            } else {
                JOptionPane.showMessageDialog(null, "Item not found.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating item: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Orderlist::new);
    }
}
