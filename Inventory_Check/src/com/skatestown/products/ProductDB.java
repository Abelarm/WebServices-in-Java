package com.skatestown.products;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProductDB {
	
	private NodeList products;

	public ProductDB(Document doc){
		products = doc.getElementsByTagName("product");
	}
	
	public Product getBySku(String sku){
	
		for(int i = 0; i < products.getLength(); i++) {
			Element productEl = (Element)products.item(i);
			Element skuEl = (Element)productEl.getElementsByTagName("sku").item(0); 
			if((skuEl.getFirstChild().getNodeValue()).equals(sku)){
				Product product = new Product();
				product.setSku(sku);
				product.setName(getValue("name", productEl)); 
				product.setType(getValue("type", productEl)); 
				product.setDescription(getValue("desc", productEl)); 
				product.setPrice(Double.parseDouble(getValue("price", productEl))); 
				product.setQuantity(Integer.parseInt(getValue("inStock", productEl)));
	            return product;
	         }
	       }
	return null;
	}
	
	private String getValue(String childName, Element el) {
		Element childEl = (Element)el.getElementsByTagName(childName).item(0); return childEl.getFirstChild().getNodeValue();
		}
	}


