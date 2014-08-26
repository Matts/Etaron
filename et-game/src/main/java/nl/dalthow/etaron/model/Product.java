package nl.dalthow.etaron.model;

public class Product {

	String name;
	String description;
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	String price;
	
	boolean sale;


	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price) 
	{
		this.price = price;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public boolean isSale() 
	{
		return sale;
	}

	public void setSale(boolean sale)
	{
		this.sale = sale;
	}
}
