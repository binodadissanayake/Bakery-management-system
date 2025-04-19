package Bakeryshop;

import javax.swing.*;
import java.awt.*;

	public class productgrid1 extends JFrame {
	    public productgrid1() {
	        setTitle("Top Products");
	        setSize(600, 700);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        
	      JPanel backgroundPanel = new JPanel() {
	            
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.setColor(new Color(211, 201, 61)); 
	                g.fillRect(0, 0, getWidth(), getHeight());
	                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Administrator\\eclipse-workspace\\FinalProject\\src\\Images\\Group 495.png");
	                Image image = backgroundImage.getImage();
	                int imgWidth = image.getWidth(null);
	                int imgHeight = image.getHeight(null);
	                float imgAspect = (float) imgWidth / imgHeight;
	                
	                int panelWidth = getWidth();
	                int panelHeight = getHeight();
	                float panelAspect = (float) panelWidth / panelHeight;

	                int newWidth, newHeight;

	                
	                if (panelAspect > imgAspect) {
	                	
	                    
	                    newHeight = panelHeight;
	                    newWidth = (int) (newHeight * imgAspect);
	                } else {
	                    
	                    newWidth = panelWidth;
	                    newHeight = (int) (newWidth / imgAspect);
	                }

	                
	                int x = (panelWidth - newWidth) / 2;
	                int y = (panelHeight - newHeight) / 2;

	                g.drawImage(image, x, y, newWidth, newHeight, this); 
	            }
	                
	        
	        };
	        backgroundPanel.setLayout(new BorderLayout());

	       
	        JLabel titleLabel = new JLabel("Top Products", JLabel.CENTER);
	        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
	        titleLabel.setForeground(Color.BLACK); // Set a visible text color
	        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
	        backgroundPanel.add(titleLabel, BorderLayout.NORTH);
	        
	        JPanel productPanel = new JPanel(new GridLayout(2, 3, 10, 10));
	        productPanel.setBackground(new Color(230, 210, 170));
	        productPanel.setBackground(new Color(230, 210, 170));
	        productPanel.setOpaque(false); 
	        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


	        
	        backgroundPanel.add(titleLabel, BorderLayout.NORTH);
	        backgroundPanel.add(productPanel, BorderLayout.CENTER);

	        
	        add(backgroundPanel);

	        setVisible(true);
	    }

	    public static void main(String[] args) {
	        new productgrid1();
	    }
	}



