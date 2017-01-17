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
		penAtos.setName("Stylo Atos Rouge");
		penAtos.setPrice(6.00);
		penAtos.setQuantity(200);
		
//		stock.addProduct(penSopra);
//		stock.removeProduct(penSopra);
//		stock.addProduct(penSopra);
//		stock.addProduct(penAtos);
			
//		stock.removeProduct(penSopra);

//		stock.updateProduct(penSopra, penAtos);
		
		Product sopra = stock.findProduct("5");
//		Product atos = stock.findProduct(penAtos);
//		
//		System.out.println("******** SOPRA ********");
		System.out.println(sopra.toString());
	    stock.updatePriceProduct("2", "40");
//
//		System.out.println("******** ATOS ********");
//		System.out.println(atos.toString());

//		ArrayList<Product> products = stock.getAllProducts();
//		System.out.println("List des produits : " + products);
		
		GiftPack gpPen= new GiftPack();
		gpPen.setName("Stylo d'entreprise");
		ArrayList<Product> penProducts = new ArrayList<>();
		penProducts.add(penSopra);
		penProducts.add(penAtos);
		gpPen.setProducts(penProducts);
		gpPen.setPrice(20.00);
		
//		stock.addPack(gpPen);
		
//		stock.removeGiftPack(gpPen);
		
		GiftPack newGpPen= new GiftPack();
		newGpPen.setName("Stylo d'entreprises et stagiares");
		ArrayList<Product> newPenProducts = new ArrayList<>();
		newPenProducts.add(penSopra);
		newPenProducts.add(penAtos);
		newGpPen.setProducts(penProducts);
		newGpPen.setPrice(50.00);
		
		stock.updateGiftPack(gpPen, newGpPen);
	}
}
