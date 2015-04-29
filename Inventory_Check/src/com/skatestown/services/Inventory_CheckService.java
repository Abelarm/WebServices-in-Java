package com.skatestown.services;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.skatestown.products.Product;
import com.skatestown.products.ProductDB;

public class Inventory_CheckService {
	private static String path = "skatestown/products.xml";
	
	public boolean doCheck(String sku, int quantity) {
		
		MessageContext msgContext = MessageContext.getCurrentMessageContext(); 
		ServletContext servletContext = (ServletContext)msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT); 
		String realPath = servletContext.getRealPath(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		factory.setNamespaceAware(true);
		Document doc = null;
		try {
		DocumentBuilder builder = factory.newDocumentBuilder(); doc = builder.parse(realPath);
		} catch (ParserConfigurationException e) {
		       e.printStackTrace();
		} catch (SAXException e) {
		       e.printStackTrace();
		} catch (IOException e) {
		       e.printStackTrace();
		}
		ProductDB pdb = new ProductDB(doc);
		Product product = pdb.getBySku(sku);
		return (product != null) && (product.getQuantity() >=quantity);
	}
}