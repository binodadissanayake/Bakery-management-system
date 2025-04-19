package JuiceMenu;

import javax.swing.*;
import Exploremore.Product;
import java.awt.*;
import java.util.List;

public class juiceview extends JFrame {
    private JPanel backgroundPanel;
    private Image backgroundImage; // Field to hold the current background image

    public juiceview() {
        setTitle("Explore More");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout()); 
        
        // Initialize the background panel
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) { // Check if the background image is set
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        backgroundPanel.setLayout(new BorderLayout());
        
        // Set initial background image (if any)
        backgroundImage = new ImageIcon("C:\\Users\\Administrator\\Downloads\\juice1.png").getImage();
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 228, 196));

        JLabel titleLabel = new JLabel("Explore More", JLabel.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        String[] categories = {"Cake", "Muffins", "Breads", "Juices", "Pizza", "Favorite"};
        Color buttonColor = new Color(255, 200, 200);
        Color textColor = Color.BLACK;

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setBackground(buttonColor);
            categoryButton.setForeground(textColor);
            categoryButton.setPreferredSize(new Dimension(80, 30));

            // Add action listener for the Cake button
            if (category.equals("Cake")) {
                categoryButton.addActionListener(e -> {
                    // Change the background image when the Cake button is clicked
                    backgroundImage = new ImageIcon("C:\\Users\\Administrator\\Downloads\\juice1.png").getImage();
                    backgroundPanel.repaint(); // Repaint to show the new background image
                });
            }

            categoryPanel.add(categoryButton);
        }

        categoryPanel.setOpaque(false);
        headerPanel.add(categoryPanel, BorderLayout.SOUTH);
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        add(backgroundPanel);
        setVisible(true);
    }

    public void displayProducts(List<Product> products) {
        // Assuming you will implement product display functionality here
    }

    public void displayjuice(Object juice) {
        // Implementation for displaying juice if needed
    }
}
