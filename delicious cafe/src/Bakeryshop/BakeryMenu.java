package Bakeryshop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BakeryMenu extends JFrame {

    // Menu items arrays
    private final String[] cakes = {"Chocolate - 3500.00", "Strawberry - 2500.00", "Oreo - 4000.00", "Cheesecake - 2000.00"};
    private final String[] breads = {"Sausage bread - 90.00", "Rolls - 100.00", "Burgers - 300.00", "Bun - 100.00", "Fishbun - 80.00"};
    private final String[] juices = {"Lime Straight - 590.00", "Orange - 740.00", "Pineapple - 890.00", "Mango - 960.00", "King coconut - 400.00", "Watermelon - 300.00"};

    // Constructor for BakeryMenu
    public BakeryMenu() {
        setTitle("Delicious Cafe Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        // Main panel with background color instead of an image
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(156, 118, 68)); // Soft beige color
        backgroundPanel.setLayout(new BorderLayout());

        // Title label with stylish font and color
        JLabel titleLabel = new JLabel("Delicious Cafe Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Main panel for menu categories
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3, 20, 20));
        backgroundPanel.add(menuPanel, BorderLayout.CENTER);

        // Font size for menu items
        int fontSize = 24;

        // Cakes Panel
        JPanel cakesPanel = createMenuSection("Cakes", fontSize, cakes);
        menuPanel.add(cakesPanel);

        // Breads Panel
        JPanel breadsPanel = createMenuSection("Breads", fontSize, breads);
        menuPanel.add(breadsPanel);

        // Juices Panel
        JPanel juicesPanel = createMenuSection("Juices", fontSize, juices);
        menuPanel.add(juicesPanel);

        // Search panel with text field and button
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(156, 118, 68));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        backgroundPanel.add(searchPanel, BorderLayout.SOUTH);

        // Search button action listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                boolean found = searchMenuItems(searchText);

                if (found) {
                    JOptionPane.showMessageDialog(null, "Item found: " + searchText);
                } else {
                    JOptionPane.showMessageDialog(null, "Item not found.");
                }
            }
        });

        setContentPane(backgroundPanel);
        setVisible(true); // Show the menu interface
    }

    // Method to search for an item in the menu
    private boolean searchMenuItems(String searchText) {
        for (String item : cakes) {
            if (item.toLowerCase().contains(searchText)) {
                return true;
            }
        }
        for (String item : breads) {
            if (item.toLowerCase().contains(searchText)) {
                return true;
            }
        }
        for (String item : juices) {
            if (item.toLowerCase().contains(searchText)) {
                return true;
            }
        }
        return false;
    }

    // Method to create a panel with a title and menu items
    private static JPanel createMenuSection(String title, int fontSize, String[] items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true), title,
                SwingConstants.CENTER, SwingConstants.TOP, new Font("Serif", Font.BOLD, 32), Color.DARK_GRAY));

        for (String item : items) {
            panel.add(createLabel(item, fontSize));
        }
        return panel;
    }

    // Creates label for menu items without the quantity input field
    private static JPanel createLabel(String labelText, int fontSize) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.DARK_GRAY);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        label.setBorder(new EmptyBorder(5, 5, 5, 5));

        panel.add(label);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BakeryMenu::new);
    }
}
