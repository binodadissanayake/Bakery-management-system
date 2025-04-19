package Login;



public class LoginMain {

	public static void main(String[] args) {
		// Create the model (replace with actual user details)
        LoginModel model = new LoginModel();

        // Create the view
        LoginView view = new LoginView();       

        // Create the controller
        LoginController controller = new LoginController(model,view);
        
       
         view.setVisible(true);
        
    }
}
		
	


