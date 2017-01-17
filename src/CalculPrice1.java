import magasin.stock.ICalculPrice;
import magasin.stock.Product;

public class CalculPrice1 implements ICalculPrice{

	@Override
	public double calcul(Product product) {
		if(product.getQuantity() >= 200)
			product.setPrice( product.getPrice() * 1.2 );
		return product.getPrice();
	}

}
