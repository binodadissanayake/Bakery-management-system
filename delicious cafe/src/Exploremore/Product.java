package Exploremore;

public class Product 
{
	private String name;
    private String category;
    private String imagePath;

    public Product(String name, String category, String imagePath) 
    {
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
    }

    public String getName()
    { 
    	return name; 
    }
    public String getCategory() 
    { 
    	return category;
    }
    public String getImagePath() 
    { 
    	return imagePath; 
    	
    } 
}

	


