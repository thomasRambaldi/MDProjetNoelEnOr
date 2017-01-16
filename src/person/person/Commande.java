package person.person;

import java.util.ArrayList;
import java.util.List;

import magasin.stock.Product;

public class Commande {
	private List<Product> listProducts;
	private double price;
	
	public Commande(){
		listProducts = new ArrayList<>();
	}

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
