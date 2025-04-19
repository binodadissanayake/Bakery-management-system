package Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

import Bakeryshop.Homepage;

public class LoginController {
    private LoginModel model;
    private LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        // Add action listener to login button
        this.view.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String email = view.getEmail();
            String password = view.getPassword();

            model.setEmail(email);
			model.setPassword(password);
			
			
			if(model.login()) {
				Homepage home = new Homepage();
	            
	            home.setVisible(true);
	            view.dispose();
			}
        
        }
    }
}
