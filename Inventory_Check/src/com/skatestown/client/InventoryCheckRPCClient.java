package com.skatestown.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class InventoryCheckRPCClient {

	public InventoryCheckRPCClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) 
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
	
	private static boolean doCheck(String sku, int quantity) throws AxisFault
	{
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options opt = serviceClient.getOptions();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/InventoryCheckService");
		opt.setTo(endpointRef);
		opt.setSoapVersionURI(Constants.URI_SOAP12_ENV);
		QName opName = new QName("http://services.skatestown.com","doCheck");
		Object[] opArgs = new Object[]{sku, quantity};
		Class[] returnTypes = new Class[]{ Boolean.class };
		Object[] returnValues = serviceClient.invokeBlocking(opName, opArgs, returnTypes);
		return (Boolean)returnValues[0];
	}

}

