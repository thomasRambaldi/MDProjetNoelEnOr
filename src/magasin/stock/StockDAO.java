package magasin.stock;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

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
	private Document doc;

	public StockDAO(String fileName){
		this.fileName=fileName;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
		try {
			doc=factory.newDocumentBuilder().parse("src/magasin/stock/"+this.fileName);
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
						Transformer transformer=null;
						try {
							transformer = TransformerFactory.newInstance().newTransformer();
						} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
							e.printStackTrace();
						}
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");

						StreamResult result = new StreamResult(new File("src/magasin/stock/stock.xml"));
						DOMSource source = new DOMSource(doc);
						try {
							transformer.transform(source, result);
						} catch (TransformerException e) {
							e.printStackTrace();
						}

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


}
