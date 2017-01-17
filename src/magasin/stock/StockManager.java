package magasin.stock;

import java.util.ArrayList;

public class StockManager {
	private StockDAO stock;
	private ICalculPrice calcul;
	
	public StockManager(String fileName, ICalculPrice methodeDeCalcul){
		this.stock=new StockDAO(fileName);
		this.calcul=methodeDeCalcul;
	}
	
	public void changeCalcul(ICalculPrice calcul){
		this.calcul=calcul;
	}
	
	public void addProduct(Product p){
		stock.addProduct(p);
	}
	
	public void removeProduct(Product p){
		stock.removeProduct(p);
	}
	
	public void updateNameProduct(String id, String newName){
		stock.updateNameProduct(id, newName);
	}
	
	public void updatePriceProduct(String id){
		stock.updatePriceProduct(id, calcul.calcul(stock.findProduct(id)).toString() );
	}

	public void updateQuantityProduct(String id, String newQuantity){
		stock.updateQuantityProduct(id, newQuantity  );
	}
	
	public void updateProduct(Product oldProduct, Product newProuct){
		stock.updateProduct(oldProduct, newProuct);
	}
	
	public Product findProduct(String id){
		return stock.findProduct("5");
	}
	
	public ArrayList<Product> getAllProducts(){
		return stock.getAllProducts();
	}
}
