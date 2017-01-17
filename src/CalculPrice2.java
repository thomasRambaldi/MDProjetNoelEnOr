import magasin.stock.ICalculPrice;
import magasin.stock.Product;

public class CalculPrice2 implements ICalculPrice{

	@Override
	public double calcul(Product product) {
		if(product.getQuantity() <= 100 )
			product.setPrice( product.getPrice() * 0.2 );
		return product.getPrice();
	}
}
