package test.resources;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import calcul.CalculPrice1;
import magasin.stock.ICalculPrice;
import magasin.stock.Product;
import magasin.stock.StockManager;

public class StockTest {

	Product p = new Product();
	Product penSopra = new Product();
	Product penAtos = new Product();

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

		penAtos.setId("5");
		penAtos.setName("Stylo Atos Rouge");
		penAtos.setPrice(6.00);
		penAtos.setQuantity(200);
		
	}
	
	@After
	public void tearDown(){
		sm.removeProduct(p);
//		sm.addProduct(penSopra);
//		sm.updateQuantityProduct("5", "400");
	}
	
	@Test
	public void addProductTest() throws InterruptedException{
		sm.addProduct(p);
	}
	
	@Test
	public void removeProductTest() throws InterruptedException{
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
		
		sm.removeProduct(penSopra);	
	}
	
	@Test
	public void updateProductTest(){
		penSopra.setId("2");
		penSopra.setName("Stylo Sopra Steria blanc et rouge");
		penSopra.setPrice(5.00);
		penSopra.setQuantity(100);
		
		sm.updateProduct(penSopra, penAtos);
	}
	
	@Test
	public void updateQuantityTest() throws InterruptedException{
		sm.updateQuantityProduct("5", "600");
		penAtos = sm.findProduct("5");
		assertEquals("600", penAtos.getQuantity().toString());
	}
	
	@Test
	public void updateNameProduct(){
		sm.updateNameProduct("5", "Stylo Atos Rouge");
	}
	
	@Test
	public void findProduct(){
		Product p = sm.findProduct("5");
		assertEquals("5", p.getId());
	}
	
	@Test
	public void getAllProducts(){
		ArrayList<Product> listProducts = new ArrayList<>();
		Product bic = new Product();
		bic.setId("1");
		bic.setName("Stylo BIC classic");
		bic.setPrice(2.0);
		bic.setQuantity(50);
		
		listProducts.add(bic);
		listProducts.add(penAtos);
		listProducts.add(p);
		Product p = sm.findProduct("5");
		assertEquals("5", p.getId());
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
