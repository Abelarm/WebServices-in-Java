package com.skatestown.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class AXIOMClient {
	
	public static void main(String[] args) throws Exception 
	{
		BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));
		String sku = null;
		int quantity = 0;
		try
		{
			while(true)
			{
				System.out.println("Insert the sku(EOF to end)");
				sku = cmd.readLine();
				if(sku == null)
					break;
				System.out.println("Insert the quantity(EOF to end)");
				String q = cmd.readLine();
				quantity=Integer.parseInt(q);
				if(q == null)
					break;
				System.out.println("The product you selected " + (doCheck(sku, quantity)?"is":"isn't") + " available, according to the quantity specified for it!");
			}
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Bye bye!");
		System.exit(0);
	}

	
	private static boolean doCheck(String sku,int quantity)throws Exception{
		boolean ris=false;
		ServiceClient serviceClient= new ServiceClient();
		
		Options opt = serviceClient.getOptions();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/InventoryCheckService");
		opt.setTo(endpointRef);
		
		OMFactory fac= OMAbstractFactory.getSOAP12Factory();
		OMNamespace omNs =fac.createOMNamespace("http://services.skatestown.com", "tns");
		
		//costruisce il body
		OMElement method = fac.createOMElement("doCheck", omNs);
		OMElement arg0 = fac.createOMElement("sku", omNs);
		arg0.addChild(fac.createOMText(arg0, sku));
		method.addChild(arg0);
		
		OMElement arg1 = fac.createOMElement("quantity", omNs);
		arg1.addChild(fac.createOMText(arg1,String.valueOf(quantity)));
		method.addChild(arg1);
		
		// invocazione del servizio utilizzando la MEP in-out
		 OMElement result = serviceClient.sendReceive(method);
		 // recupera il risultato
		 String response = result.getFirstElement().getText();
		 ris = Boolean.valueOf(response).booleanValue();
		 
		 return ris;
	}
}
