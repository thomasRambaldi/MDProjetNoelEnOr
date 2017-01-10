package magasin.stock;

import java.util.ArrayList;

public class GiftPack {

	private String name;
	private Double price;
	private ArrayList<Product> products = new ArrayList<>();
	
	public GiftPack(){
			
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "GiftPack [name=" + name + ", price=" + price + ", products=" + products + "]";
	}
	
	
}
