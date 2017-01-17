package magasin.stock;

public class StockManager {
	private StockDAO stock;
	private ICalculPrice calcul;
	
	public StockManager(String fileName, ICalculPrice methodeDeCalcul){
		this.stock=new StockDAO(fileName);
		this.calcul=methodeDeCalcul;
	}
	
	public void updatePriceProduct(String id){
		stock.updatePriceProduct(id, calcul.calcul(stock.findProduct(id)).toString() );
	}
	
	public void changeCalcul(ICalculPrice calcul){
		this.calcul=calcul;
	}
	
}
