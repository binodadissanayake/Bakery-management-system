package Exploremore;

import javax.swing.*;

import JuiceMenu.juiceview;

import java.awt.*;
import java.util.List;

public class productView extends JFrame {
    private JPanel productPanel;
    private JLabel titleLabel;
    private JPanel categoryPanel;

    public productView() {
        setTitle("Explore More");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\Administrator\\Downloads\\cake.png");
                Image backgroundImage = backgroundIcon.getImage();
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };
        backgroundPanel.setBackground(new Color(214, 181, 72));
        backgroundPanel.setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 228, 196));

        titleLabel = new JLabel("Explore More", JLabel.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        String[] categories = {"Cake", "Muffins", "Breads", "Juices", "Pizza", "Favorite"};
        Color buttonColor = new Color(255, 200, 200);
        Color textColor = Color.BLACK;
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setBackground(buttonColor);
            categoryButton.setForeground(textColor);
            categoryPanel.add(categoryButton);
        }
        categoryPanel.setOpaque(false);
        
        headerPanel.add(categoryPanel, BorderLayout.SOUTH);
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        productPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        productPanel.setOpaque(false);
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(productPanel, BorderLayout.CENTER);

        // Add the forward arrow button
        JButton forwardButton = new JButton("→");
        forwardButton.setFont(new Font("Arial", Font.BOLD, 24));
        forwardButton.setBackground(new Color(199, 158, 0));
        forwardButton.setForeground(Color.BLACK);
        forwardButton.addActionListener(e -> {
            juiceview juiceMenu = new juiceview(); // Create a new instance of JuiceMenu
            juiceMenu.setVisible(true); // Show the JuiceMenu
            dispose(); // Close the current window if desired
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(forwardButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel at the bottom

        add(backgroundPanel);
        setVisible(true);
    }

    public void displayProducts(List<Product> products) {
        productPanel.removeAll();
        for (Product product : products) {
            JLabel productLabel = new JLabel(new ImageIcon(product.getImagePath()));
            productPanel.add(productLabel);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }
}
