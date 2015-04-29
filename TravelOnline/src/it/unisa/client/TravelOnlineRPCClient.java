package it.unisa.client;


import it.unisa.data.Cliente;
import it.unisa.data.Vacanza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TravelOnlineRPCClient
{

	static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws AxisFault 
	{
		System.out.println("TravelOnline:\n1) elencoPacchetti\n2) registraCliente");
		int op = 0;
		try {
			op = Integer.parseInt(console.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (op)
		{
			case 1: elencoPacchetti(); break;
			case 2: registraCliente(); break;
			default: System.out.println("non valido"); break;
		}
		
	}
	
	public static void elencoPacchetti() throws AxisFault 
	{
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options opt = serviceClient.getOptions();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/TravelOnline");
		opt.setTo(endpointRef);
		opt.setSoapVersionURI(Constants.URI_SOAP12_ENV);
		QName opName = new QName("http://service.unisa.it", "elencoPacchetti");
		
		try
		{
			while (true)
			{
				System.out.println(">>> ");
				String message = console.readLine();
				Object[] opArgs = new Object[] { message };
				@SuppressWarnings("rawtypes")
				Class[] returnTypes = new Class[] { Vacanza[].class };
				Object[] returnValues = serviceClient.invokeBlocking(opName, opArgs, returnTypes);
				System.out.println("#" + returnValues.length);
				Vacanza[] vacanze = (Vacanza[]) returnValues[0];
				for (Object o : vacanze)
				{
					Vacanza v = (Vacanza) o;
					System.out.println(v);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void registraCliente() throws AxisFault 
	{
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options opt = serviceClient.getOptions();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/TravelOnline");
		opt.setTo(endpointRef);
		opt.setSoapVersionURI(Constants.URI_SOAP12_ENV);
		QName opName = new QName("http://service.unisa.it", "registraCliente");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			while (true)
			{
				ArrayList<String> a = new ArrayList<String>();
				for (String s : new String[] { "nome", "cognome", "email", "login" })
				{
					System.out.println("Inserire " + s);
					String x = console.readLine();
					a.add(x);
				}
				Cliente c = new Cliente(a.get(0), a.get(1), a.get(2), a.get(3));

				Object[] returnValues = serviceClient.invokeBlocking(opName, new Object[] { c }, new Class[] { Boolean.class });
				System.out.println("#" + returnValues.length);
				boolean b = (Boolean) returnValues[0];
				System.out.println(b ? "Ok" : "not ok");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
