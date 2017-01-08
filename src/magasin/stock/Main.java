package magasin.stock;

public class Main {
	public static void main(String [] args){
		StockDAO stock = new StockDAO("stock.xml");
		Product penSopra = new Product();
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
//		stock.addProduct(penSopra);
		stock.removeProduct(penSopra);
	}
}
