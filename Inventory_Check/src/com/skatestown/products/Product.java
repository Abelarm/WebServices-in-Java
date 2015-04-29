package com.skatestown.products;

public class Product {
	
	private String sku;
	private String name;
	private String type; 
	private String description; 
	private double price; 
	private int quantity;
	
	
	public String getName() { 
		return name;
	}

	public void setName(String n) {
		name = n;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public String getDescription() {
        return description;
	}
	
	public void setDescription(String d) {
        description = d;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double p) {
        price = p;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int q) {
        quantity = q;
	}
	
	public String getSku(){
		return sku;
	}
	
	public void setSku(String s){
		sku=s;
	}
}
