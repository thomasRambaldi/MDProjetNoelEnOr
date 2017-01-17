package testUnitaires;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import calcul.CalculPrice1;
import magasin.stock.ICalculPrice;
import magasin.stock.Product;
import magasin.stock.StockDAO;
import magasin.stock.StockManager;

public class StockTest {

	StockDAO stockDao = new StockDAO("stock.xml");
	
	Product p = new Product();
	Product penSopra = new Product();
	
	ICalculPrice calcul = new CalculPrice1();
	StockManager sm = new StockManager("stock.xml", calcul);
	
	@Before
	public void before(){
		p.setId("10");
		p.setName("Ordinateur");
		p.setPrice(1600.00);
		p.setQuantity(5);
		
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void addProductTest() throws InterruptedException{
		
		stockDao.addProduct(p);	
		
//		Product product = stockDao.findProduct(p);
//		Assert.assertEquals(10, product.getId());
	}
	
	@Test
	public void removeProductTest() throws InterruptedException{
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
		
		stockDao.removeProduct(penSopra);	
		
//		Product product = stockDao.findProduct(p);
//		Assert.assertNotEquals(10, product.getId());
	}
	
	@Test
	public void updateProductTest(){
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
		
		Product penAtos = new Product();
		penAtos.setId("5");
		penAtos.setName("Stylo Atos Rouge");
		penAtos.setPrice(6.00);
		penAtos.setQuantity(200);
		
		stockDao.updateProduct(penSopra, penAtos);
	}
	
	@Test
	public void updatePrice1(){
		sm.updatePriceProduct("1");
	}
	
	@Test
	public void updatePrice2(){
		sm.updatePriceProduct("5");
	}
}
