package JuiceMenu;

import JuiceMenu.juice;
import JuiceMenu.juiceview;
import java.util.ArrayList;
import java.util.List;

public class juicecontroller {
	private juiceview view;
    private List<juice> products;
	private Object juice;

    public juicecontroller(juiceview view) {
        this.view = view;
        this.juice = loadjuice();
        this.view.displayjuice(juice);
    }

    private Object loadjuice() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<juice> loadjuice1() {
    	List<juice> juice = new ArrayList<>();
        return juice;
        
        // Add more products as needed
       
    }

}
