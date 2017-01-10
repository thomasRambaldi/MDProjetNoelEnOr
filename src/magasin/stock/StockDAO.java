package magasin.stock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StockDAO {
	private String fileName="stock.xml";
	private static final String PATH = "src/magasin/stock/";
	private Document doc;

	public StockDAO(String fileName){
		this.fileName=fileName;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
		try {
			doc=factory.newDocumentBuilder().parse(PATH + this.fileName);
		} catch (SAXException e) { e.printStackTrace();
		} catch (IOException e)  { e.printStackTrace();
		} catch (ParserConfigurationException e) { e.printStackTrace();
		}
	}

	public void addProduct(Product p){
		Element racine = doc.getDocumentElement();
		NodeList list = racine.getChildNodes();
		for(int i=0; i < list.getLength(); i++){
			Node stock = list.item(i);
			if(stock.getNodeType()==Node.ELEMENT_NODE){ /*balise stock*/
				NodeList childsStock = stock.getChildNodes();
				for(int k=0; k < childsStock.getLength(); k++){
					Node products = list.item(i);
					if(products.getNodeType()==Node.ELEMENT_NODE){ /* balise products */
						Node nodeProduct = createNodeFromProduct(p);
						products.appendChild(nodeProduct);
						modifieDocumentXML();
						break;
					}
				}
				break;
			}	
		}
	}

	private Node createNodeFromProduct(Product p) {
		Element product = doc.createElement("product");
		Element id = doc.createElement("id");
		id.appendChild(doc.createTextNode(p.getId()));
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(p.getName()));
		Element price = doc.createElement("price");
		price.appendChild(doc.createTextNode(p.getPrice().toString()));
		Element quantity = doc.createElement("quantity");
		quantity.appendChild(doc.createTextNode(p.getQuantity().toString()));

		product.appendChild(id);
		product.appendChild(name);
		product.appendChild(price);
		product.appendChild(quantity);
		System.out.println();

		return product;
	}

	private void treatElements(NodeList list) {

	}

	// TODO : Probleme quand on supprime un produit celui ci se supprime
	// Mais il laisse une ligne vide !! 
	@Deprecated
	public void removeProduct2(Product p){
		Element racine = doc.getDocumentElement();
		NodeList list = racine.getChildNodes();
		for(int i=0; i < list.getLength(); i++){
			Node stock = list.item(i);
			if(stock.getNodeType()==Node.ELEMENT_NODE){ /*balise stock*/
				NodeList childsStock = stock.getChildNodes();
				for(int k=0; k < childsStock.getLength(); k++){
					Node products = list.item(k);
					if(products.getNodeType()==Node.ELEMENT_NODE){ /* balise products */
						NodeList listProduct = products.getChildNodes();
						for(int m=0 ; m < listProduct.getLength() ; m++){
							Node product = listProduct.item(m);
							if(product.getNodeType()==Node.ELEMENT_NODE){ /* balise product */
								NodeList childProduct = product.getChildNodes();
								String productName = childProduct.item(3).getTextContent();
								if( productName.equals(p.getName()) ){
									product.getParentNode().removeChild(product);
									modifieDocumentXML();
								}
							}
						}
					}
				}
			}
		}
	}

	// TODO : Probleme quand on supprime un produit celui ci se supprime
	// Mais il laisse une ligne vide !! 
	public void removeProduct(Product p){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			NodeList detailsProduct = allProduct.item(i).getChildNodes();
			if(detailsProduct.item(3).getTextContent().equals(p.getName())){
				allProduct.item(i).getParentNode().removeChild(allProduct.item(i));
				modifieDocumentXML();
			}
		}
	}

	public void updateProduct(Product oldProduct, Product NewProuct){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			NodeList detailsProduct = allProduct.item(i).getChildNodes();
			if(detailsProduct.item(3).getTextContent().equals(oldProduct.getName())){
				detailsProduct.item(1).setTextContent(NewProuct.getId());
				detailsProduct.item(3).setTextContent(NewProuct.getName());
				detailsProduct.item(5).setTextContent(NewProuct.getPrice().toString());
				detailsProduct.item(7).setTextContent(NewProuct.getQuantity().toString());
				modifieDocumentXML();
			}
		}
	}

	public Product findProduct(Product p){
		Product product = null;
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			NodeList detailsProduct = allProduct.item(i).getChildNodes();
			if(detailsProduct.item(3).getTextContent().equals(p.getName())){

				product = new Product();
				String id = detailsProduct.item(1).getTextContent();
				product.setId(id); 

				String name = detailsProduct.item(3).getTextContent();
				product.setName(name); 

				String price = detailsProduct.item(5).getTextContent();
				product.setPrice(Double.parseDouble(price)); 

				String quantity = detailsProduct.item(7).getTextContent();
				product.setQuantity(Integer.parseInt(quantity)); 
			}
		}
		return product;
	}

	public ArrayList<Product> getAllProducts(){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		ArrayList<Product> products = new ArrayList<>();
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			NodeList detailsProduct = allProduct.item(i).getChildNodes();
			if( ! isNodeStock(detailsProduct.item(i))  ) break;
			if(detailsProduct.item(3).getNodeType() == Node.ELEMENT_NODE ){

				Product product = new Product();

				String id = detailsProduct.item(1).getTextContent();
				product.setId(id); 

				String name = detailsProduct.item(3).getTextContent();
				product.setName(name); 

				String price = detailsProduct.item(5).getTextContent();
				product.setPrice(Double.parseDouble(price)); 

				String quantity = detailsProduct.item(7).getTextContent();
				product.setQuantity(Integer.parseInt(quantity));
				products.add(product);

			}
		}
		return products;
	}

	private boolean isNodeStock(Node node){
		return node.getParentNode().getParentNode().getParentNode().getNodeName().equals("stock") ? true : false;
	}

	// TODO : Probleme : la quantité de chaque produit qui se trouveront dans le packs 
	// auront pour quantité la quantité du stock
	public void addPack(GiftPack gp){
		NodeList allPacks = doc.getElementsByTagName("packs"); //Recupere toutes les balises product ainsi que les noeuds fils
		Node packs = allPacks.item(0);
		
		Node nodePack= createNodeFromPack(gp);
		packs.appendChild(nodePack);
		modifieDocumentXML();
	}
	
	private Node createNodeFromPack(GiftPack gf){
		Element pack = doc.createElement("pack");
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(gf.getName()));
		pack.appendChild(name);
		
		Element products = doc.createElement("products");
		pack.appendChild(products);
		
		for(int i = 0 ; i < gf.getProducts().size() ; i++){
			Element product = doc.createElement("product");
			products.appendChild(product);

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(gf.getProducts().get(i).getId()));
			product.appendChild(id);
			
			Element quantity = doc.createElement("quantity");
			quantity.appendChild(doc.createTextNode(gf.getProducts().get(i).getQuantity().toString()));
			product.appendChild(quantity);
		}
		
		Element price = doc.createElement("price");
		price.appendChild(doc.createTextNode(gf.getPrice().toString()));
		pack.appendChild(price);
		
		return pack;
	}

	public void removeGiftPack(GiftPack gf){

	}

	public void updateGiftPack(GiftPack gf){

	}


	public void modifieDocumentXML() {
		Transformer transformer=null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException |
				TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new File(PATH + fileName));
		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
