package magasin.stock;

import java.util.ArrayList;

public class Main {
	public static void main(String [] args){
		StockDAO stock = new StockDAO("stock.xml");
		
		Product penSopra = new Product();
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
		
		Product penAtos = new Product();
		penAtos.setId("5");
		penAtos.setName("Stylo Atos Bleu");
		penAtos.setPrice(6.00);
		penAtos.setQuantity(100);
		
//		stock.addProduct(penSopra);
		
//		stock.removeProduct(penSopra);

//		stock.updateProduct(penSopra, penAtos);
		
//		Product sopra = stock.findProduct(penSopra);
//		Product atos = stock.findProduct(penAtos);
//		
//		System.out.println("******** SOPRA ********");
//		System.out.println(sopra.toString());
//
//		System.out.println("******** ATOS ********");
//		System.out.println(atos.toString());

//		ArrayList<Product> products = stock.getAllProducts();
//		System.out.println("List des produits : " + products);
		
	}
}
