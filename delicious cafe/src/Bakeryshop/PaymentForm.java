package Bakeryshop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

public class PaymentForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JLabel orderAmountLabel; // To display the order amount
    private JRadioButton cashButton;
    private JRadioButton cardButton;

    private List<Item> items; // List of bakery items
    private JPanel itemPanel; // Panel for item selection
    private List<JSpinner> quantitySpinners; // List to hold quantity spinners for each item

    public PaymentForm(GraphicsConfiguration gc) {
        super(gc);
        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // Initialize bakery items
        initializeItems();

        // Main content panel with BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(224, 198, 165)); // Beige background color

        // Title panel to center "Payment" in the middle
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(224, 198, 165)); // Match background color
        JLabel titleLabel = new JLabel("Payment");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel, BorderLayout.CENTER); // Center the title

        // Main form panel with vertical layout for form fields and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(224, 198, 165));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Add form fields
        nameField = createField("Name:", true);
        emailField = createField("Email:", true);
        mainPanel.add(nameField);
        mainPanel.add(emailField);

        // Add item selection panel
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(items.size(), 2)); // Grid layout for items
        quantitySpinners = new ArrayList<>(); // Initialize quantity spinner list

        for (Item item : items) {
            JCheckBox itemCheckBox = new JCheckBox(item.getName());
            itemCheckBox.setActionCommand(String.valueOf(item.getPrice())); // Set action command as price

            // Create a spinner for quantity
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)); // Allow 0 to 100
            quantitySpinners.add(quantitySpinner); // Add spinner to the list

            itemPanel.add(itemCheckBox); // Add item checkbox
            itemPanel.add(quantitySpinner); // Add corresponding quantity spinner
        }
        mainPanel.add(new JScrollPane(itemPanel)); // Scroll pane for the item selection

        // Display Order Amount as a read-only label
        orderAmountLabel = new JLabel("Order Amount: Rs0.00"); // Initial order amount
        orderAmountLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mainPanel.add(orderAmountLabel); // Add the order amount label to the panel

        // Payment method section
        JLabel paymentLabel = new JLabel("Payment Method");
        paymentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(paymentLabel);

        JPanel paymentMethodPanel = new JPanel();
        paymentMethodPanel.setBackground(new Color(224, 198, 165));
        ButtonGroup paymentGroup = new ButtonGroup();
        cashButton = new JRadioButton("Cash");
        cardButton = new JRadioButton("Card");
        paymentGroup.add(cashButton);
        paymentGroup.add(cardButton);
        paymentMethodPanel.add(cashButton);
        paymentMethodPanel.add(cardButton);
        mainPanel.add(paymentMethodPanel);

        mainPanel.add(Box.createVerticalStrut(20)); // Space before submit button

        // Submit Payment button
        JButton submitButton = new JButton("Submit Payment");
        submitButton.setBackground(new Color(189, 183, 107));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            String billDetails = updateOrderAmount(); // Update order amount and get bill details
            showBill(billDetails); // Show the bill with details
        }); // Action listener to show bill
        mainPanel.add(submitButton);

        // Add the main form panel to the bottom of the content panel
        contentPanel.add(mainPanel, BorderLayout.SOUTH);

        add(contentPanel);
        setVisible(true);
    }

    // Method to initialize bakery items
    private void initializeItems() {
        items = new ArrayList<>();
        items.add(new Item("Chocolate Cake", 3500.00)); // Add bakery items and their prices
        items.add(new Item("Rolls", 100.00));
        items.add(new Item("Orange Juice", 740.00));
        items.add(new Item("Bun", 100.00));
    }

    // Method to update the order amount
    private String updateOrderAmount() {
        double totalAmount = 0; // Total amount
        StringBuilder billDetails = new StringBuilder(); // For detailed bill summary

        for (int i = 0; i < items.size(); i++) {
            JCheckBox itemCheckBox = (JCheckBox) itemPanel.getComponent(i * 2); // Get the checkbox for the item
            int quantity = (Integer) quantitySpinners.get(i).getValue(); // Get the quantity from the spinner

            if (itemCheckBox.isSelected() && quantity > 0) { // Only calculate for selected items
                double itemTotal = items.get(i).getPrice() * quantity; // Calculate total for this item
                totalAmount += itemTotal; // Add to total amount
                billDetails.append(String.format("%s (Qty: %d): Rs%.2f\n", items.get(i).getName(), quantity, itemTotal)); // Append details
            }
        }

        orderAmountLabel.setText(String.format("Order Amount: Rs%.2f", totalAmount)); // Update label (ensure consistency here)
        return billDetails.toString(); // Return bill details for future use
    }

    // Method to show the bill after payment submission
    private void showBill(String billDetails) {
        String name = nameField.getText(); // Get the name from the text field
        String email = emailField.getText(); // Get the email from the text field

        // Create a dialog to display the bill
        String message = String.format("Customer Name: %s\nEmail: %s\n\nOrder Details:\n%s\nTotal Amount: RS%.2f",
                name, email, billDetails, getTotalAmount());
        JOptionPane.showMessageDialog(this, message, "Bill Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to get the total amount
    private double getTotalAmount() {
        String amountText = orderAmountLabel.getText();
        String[] parts = amountText.split("Rs");

        if (parts.length < 2) {
            System.err.println("Error: Invalid amount format in label.");
            return 0.0; // Return 0 or handle error as needed
        }
        // Trim and parse the total amount
        try {
            return Double.parseDouble(parts[1].trim()); // Extract the total amount as double
        } catch (NumberFormatException e) {
            System.err.println("Error: Unable to parse total amount.");
            return 0.0; // Return 0 or handle error as needed
        }
    }

    // Method to create a labeled text field
    private JTextField createField(String labelText, boolean isRequired) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    // Main method to run the PaymentForm
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentForm(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()));
    }
}
