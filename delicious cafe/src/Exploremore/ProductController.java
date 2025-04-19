package Exploremore;

import Exploremore.Product;
import Exploremore.productView;




import java.util.ArrayList;
import java.util.List;

public class ProductController {
	private productView view;
    private List<Product> products;

    public ProductController(productView view) {
        this.view = view;
        this.products = loadProducts();
        this.view.displayProducts(products);
    }

    private List<Product> loadProducts() {
    	List<Product> products = new ArrayList<>();
        return products;
        
        // Add more products as needed
       
    }

    
}

