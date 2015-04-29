package it.unisa.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.namespace.Constants;

public class AXIOMClient {

	static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		System.out.println("GameHouse:\n1) elencoGiochi \n2) getPrezzo \n3) Acquista");
		int op = 0;
		try {
			op = Integer.parseInt(console.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (op)
		{
			case 1: elencoGiochi(); break;
			case 2: getPrezzo(); break;
			case 3: Acquista(); break;
			default: System.out.println("non valido"); break;
		}
		
	}

	private static void Acquista() throws AxisFault {
		String cod = null;
		String clicod=null;
		System.out.println("inserisci il codice del gioco");
		try {
			cod=console.readLine();
			System.out.println("inserisci codice cliente");
			clicod=console.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceClient service = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/GameHouse");
		Options opt = new Options();
		opt.setTo(endpointRef);
		opt.setSoapVersionURI(Constants.URI_SOAP12_ENV);
		service.setOptions(opt);
		OMFactory factory = OMAbstractFactory.getSOAP12Factory();
		OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");
		
		OMElement method = factory.createOMElement("getPrezzoScontato", ns);
		OMElement arg0 = factory.createOMElement("codice", ns);
		arg0.addChild(factory.createOMText(arg0, cod));
		method.addChild(arg0);
		
		//Creazion e aggunta header
		OMNamespace clienteHeaderNs = factory.createOMNamespace("http://ws.service.unisa.it.com", "em");
     	OMNamespace soapenvNs = factory.createOMNamespace("http://www.w3.org/2003/05/soap-envelope", "soapenv");
     	OMElement clienteHeader = factory.createOMElement("scontoHeader", clienteHeaderNs);
     	clienteHeader.addChild(factory.createOMText(clienteHeader, clicod));
     	clienteHeader.addAttribute("role", "http://www.w3.org/2003/05/role/next", soapenvNs);
     	service.addHeader(clienteHeader);
		
		// invocazione del servizio utilizzando la MEP in-out
		OMElement result = service.sendReceive(method);
		// recupera il risultato
		String response = result.getFirstElement().getText();
		System.out.println(response);
		
	}

	private static void getPrezzo() throws AxisFault {
		String cod = null;
		System.out.println("inserisci il codice del gioco");
		try {
			cod=console.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceClient service = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/GameHouse");
		Options opt = new Options();
		opt.setTo(endpointRef);
		service.setOptions(opt);
		OMFactory factory = OMAbstractFactory.getSOAP12Factory();
		OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");
		
		OMElement method = factory.createOMElement("getPrezzo", ns);
		OMElement arg0 = factory.createOMElement("codice", ns);
		arg0.addChild(factory.createOMText(arg0, cod));
		method.addChild(arg0);
		
		// invocazione del servizio utilizzando la MEP in-out
		OMElement result = service.sendReceive(method);
		// recupera il risultato
		String response = result.getFirstElement().getText();
		System.out.println(response);
		
	}

	private static void elencoGiochi() throws AxisFault {
		ServiceClient service = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8081/axis2/services/GameHouse");
		Options opt = service.getOptions();
		opt.setTo(endpointRef);
		service.setOptions(opt);
		OMFactory factory = OMAbstractFactory.getSOAP12Factory();
		OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");
		
		OMElement method = factory.createOMElement("getGiochi", ns);
		
		// invocazione del servizio utilizzando la MEP in-out
		OMElement result = service.sendReceive(method);
		// recupera il risultato
		String response = result.getFirstElement().getText();
		System.out.println(response);
		
	}

}
