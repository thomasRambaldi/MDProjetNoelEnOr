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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/*
 * Faudrai pourvoir ajouter / editer / supprimer des produits qui se trouve dans un gifpack
 */

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

	public void addProduct(Product p) {
		Node baliseProducts = doc.getElementsByTagName("products").item(0);
		NodeList products = doc.getElementsByTagName("product");
		
		/** Section qui va detecter si un autre produit avec le meme id que p existe **/
		for(int i = 0; i < products.getLength() ; i++){
			Node nodeProduct = products.item(i);
			Product product = createProductFromNode(nodeProduct);
			if(product.getId().equals(p.getId())){
				if( product.getName().equals(p.getName())){
					nodeProduct.getChildNodes().item(7).setTextContent(Integer.toString(product.getQuantity()+p.getQuantity()));
					saveModifications();
					return;
				}
				else{
					System.err.println("Erreur de mise en stock : le produit \""+p.getName()+"\"\nà un id : "+p.getId()+" déjà utilisé par le produit \""+product.getName()+"\"");
					return;
				}
			}
		}

		Node product = createNodeFromProduct(p);
		baliseProducts.appendChild(product);
		saveModifications();
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

		return product;
	}

	private Product createProductFromNode(Node n) {
		Product product = new Product();
		NodeList itemsProducts = n.getChildNodes();
		for(int i=0; i<itemsProducts.getLength(); i++){
			Node itemProduct = itemsProducts.item(i);
			if(itemProduct.getNodeType()==Node.ELEMENT_NODE){
				if(itemProduct.getNodeName().equals("id"))       product.setId(itemProduct.getTextContent());
				if(itemProduct.getNodeName().equals("name"))     product.setName(itemProduct.getTextContent());
				if(itemProduct.getNodeName().equals("price"))    product.setPrice(Double.valueOf(itemProduct.getTextContent()));
				if(itemProduct.getNodeName().equals("quantity")) product.setQuantity(Integer.decode(itemProduct.getTextContent()));
			}
		}
		return product;
	}

	public void removeProduct(Product p){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			Node product = allProduct.item(i);
			if(product.getChildNodes().item(1).getTextContent().equals(p.getId())){
				product.getParentNode().removeChild(product);
				removeSpacesInXML();
				saveModifications();
			}	
		}
	}

	public void updateNameProduct(String id, String newName){
		Node product = findNodeProduct(id);
		product.getChildNodes().item(3).setTextContent(newName);
		saveModifications();
	}
	
	public void updatePriceProduct(String id, String newPrice){
		Node product = findNodeProduct(id);
		product.getChildNodes().item(5).setTextContent(newPrice);
		saveModifications();
	}
	
	public void updateQuantityProduct(String id, String newQuantity){
		Node product = findNodeProduct(id);
		product.getChildNodes().item(7).setTextContent(newQuantity);
		saveModifications();
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
				saveModifications();
			}
		}
	}

	public Product findProduct(String id){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			Node product = allProduct.item(i);
			if(product.getChildNodes().item(1).getTextContent().equals(id))
				return createProductFromNode(product);
		}
		return null;
	}

	public Node findNodeProduct(String id){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		for(int i = 0 ; i < allProduct.getLength() ; i++){ // parcours de tous les product
			Node product = allProduct.item(i);
			if(product.getChildNodes().item(1).getTextContent().equals(id))
				return product;
		}
		return null;
	}

	public ArrayList<Product> getAllProducts(){
		NodeList allProduct = doc.getElementsByTagName("product"); //Recupere toutes les balises product ainsi que les noeuds fils
		ArrayList<Product> products = new ArrayList<>();
		for(int i = 0 ; i < allProduct.getLength() ; i++)// parcours de tous les product
			products.add(createProductFromNode(allProduct.item(i)));
		return products;
	}

	private boolean isNodeStock(Node node){
		return node.getParentNode().getParentNode().getParentNode().getNodeName().equals("stock") ? true : false;
	}

	// TODO : Probleme : la quantit� de chaque produit qui se trouveront dans le packs 
	// auront pour quantit� la quantit� du stock
	public void addPack(GiftPack gp){
		NodeList allPacks = doc.getElementsByTagName("packs"); //Recupere toutes les balises product ainsi que les noeuds fils
		Node packs = allPacks.item(0);

		Node nodePack= createNodeFromPack(gp);
		packs.appendChild(nodePack);
		saveModifications();
	}

	private Node createNodeFromPack(GiftPack gf){
		Element pack = doc.createElement("pack");
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(gf.getName()));
		pack.appendChild(name);

		Element products = doc.createElement("idProducts");
		pack.appendChild(products);

		for(int i = 0 ; i < gf.getProducts().size() ; i++){
			Element product = doc.createElement("idProduct");
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

	// TODO : Probleme quand on supprime un giftPack, celui ci se supprime
	// Mais il laisse une ligne vide !! 
	public void removeGiftPack(GiftPack gp){
		NodeList allPack = doc.getElementsByTagName("pack"); // //Recupere toutes les balises pack ainsi que les noeuds fils

		for(int i = 0 ; i < allPack.getLength() ; i++){ // parcours de tous les pack
			NodeList detailsPack = allPack.item(i).getChildNodes();
			for(int j = 0 ; j < allPack.getLength() ; j++){ // parcours des balises name and products
				Node baliseName = detailsPack.item(j);
				NodeList name = detailsPack.item(j).getChildNodes();
				if(baliseName.getNodeType() == Node.ELEMENT_NODE){
					for(int k = 0 ; k < name.getLength() ; k++){
						//						System.out.print("<"+baliseName.getNodeName()+">");
						//						System.out.print(name.item(k).getTextContent());
						//						System.out.println("</"+baliseName.getNodeName()+">");
						if(name.item(k).getTextContent().equals(gp.getName())){
							allPack.item(i).getParentNode().removeChild(allPack.item(i));
							removeSpacesInXML();
							saveModifications();
						}
					}
				}
			}
		}
	}


	//	TODO : pas fini. Faut mettre les produits a l'interieur du packs a jour
	// COmment recupere le frere dans les balises pack ?
	public void updateGiftPack(GiftPack oldGp, GiftPack newGp){		NodeList allPack = doc.getElementsByTagName("pack"); // //Recupere toutes les balises pack ainsi que les noeuds fils
	for(int i = 0 ; i < allPack.getLength() ; i++){ // parcours de tous les pack
		NodeList detailsPack = allPack.item(i).getChildNodes();
		for(int j = 0 ; j < allPack.getLength() ; j++){ // parcours des balises name and products
			Node baliseName = detailsPack.item(j);
			Node name = detailsPack.item(j).getFirstChild();
			if(baliseName.getNodeType() == Node.ELEMENT_NODE){
				if(name.getTextContent().equals(oldGp.getName())){
					name.setTextContent(newGp.getName());

					Node price = baliseName.getNextSibling().getNextSibling().getNextSibling().getNextSibling();
					price.setTextContent(newGp.getPrice().toString());
					System.out.println("price : " + price.getTextContent());
					saveModifications();
				}

			}
		}
	}
	}


	public void removeSpacesInXML(){
		XPath xp = XPathFactory.newInstance().newXPath();
		NodeList nl = null;
		try {
			nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}

		for (int i=0; i < nl.getLength(); ++i) {
			Node node = nl.item(i);
			node.getParentNode().removeChild(node);
		}
	}

	public void saveModifications() {
		Transformer transformer=null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException |
				TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		StreamResult result = new StreamResult(new File(PATH + fileName));

		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
