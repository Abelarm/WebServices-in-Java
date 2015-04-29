package it.unisa.client;

import it.unisa.data.Vacanza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.axis2.namespace.Constants;


public class TravelOnlineAxiomClient 
{

	static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		System.out.println("TravelOnline:\n1) elencoPacchetti\n2) registraCliente \n3) Prenota");
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
			case 3: prenota(); break;
			default: System.out.println("non valido"); break;
		}
		
	}
	
	public static void elencoPacchetti() throws AxisFault 
	{
		ServiceClient service = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8081/axis2/services/TravelOnline");
		Options opt = new Options();
		opt.setTo(endpointRef);
		service.setOptions(opt);
		OMFactory factory = OMAbstractFactory.getSOAP12Factory();
		OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");
		
		OMElement method = factory.createOMElement("elencoPacchetti", ns);
		OMElement arg0 = factory.createOMElement("filtro_categoria", ns);
		OMElement result = null;
		
		try
		{
			while (true)
			{
				System.out.println(">>> ");
				String message = console.readLine();
				arg0.addChild(factory.createOMText(arg0, message));
				method.addChild(arg0);
				result = service.sendReceive(method);
				@SuppressWarnings("rawtypes")
				Iterator it = result.getChildren();
				while (it.hasNext())
				{
					Vacanza v = (Vacanza) BeanUtil.deserialize(Vacanza.class, (OMElement) it.next(), new DefaultObjectSupplier(), "Vacanza"); 
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
		ServiceClient service = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://localhost:8081/axis2/services/TravelOnline");
		Options opt = new Options();
		opt.setTo(endpointRef);
		service.setOptions(opt);
		OMFactory factory = OMAbstractFactory.getSOAP12Factory();
		OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");
		
		OMElement method = factory.createOMElement("registraCliente", ns);
		OMElement arg0 = factory.createOMElement("c", ns);
		OMElement result = null;
	
		try
		{
			while (true)
			{
			
				for (String field : new String[] { "nome", "cognome", "email", "login" })
				{
					System.out.println("Inserire " + field);
					String str = console.readLine();
					OMElement e = factory.createOMElement(field, ns);
					e.addChild(factory.createOMText(e, str));
					arg0.addChild(e);
				}
				method.addChild(arg0);
				result = service.sendReceive(method);
				// non c'e' bisogno di usare BeanUtil.deserialise per un singolo bool...
				@SuppressWarnings("rawtypes")
				Iterator it = result.getChildrenWithName(new QName("http://service.unisa.it", "return"));
				OMElement e = (OMElement) it.next();
				boolean b = Boolean.parseBoolean(e.getText());
				System.out.println(b ? "Ok" : "not ok");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void prenota() throws AxisFault{
		String ID=null;
		int num=0;
		String cognome = null;
		BufferedReader console = new BufferedReader( new InputStreamReader(System.in) );
		while( true ) {
			System.out.println("Inserire l'id per continuare (EOF per terminare): ");
			try {
				ID = console.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (ID == null)
				System.exit(-1);

			System.out.println("Inserire il numero di pezzi: ");
			try {
				num = Integer.parseInt(console.readLine());
				System.out.println("Inserire il cognome");
				cognome=console.readLine();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		ServiceClient serviceClient = new ServiceClient();
		EndpointReference endpointRef = new EndpointReference("http://127.0.0.1:8080/axis2/services/TravelOnline");
		Options options = serviceClient.getOptions();
		options.setTo(endpointRef);
		options.setSoapVersionURI(Constants.URI_SOAP12_ENV);
		serviceClient.setOptions(options);

   	     OMFactory fac = OMAbstractFactory.getSOAP12Factory();
   	     OMNamespace omNs = fac.createOMNamespace("http://service.unisa.it", "tns");

   	     OMElement method = fac.createOMElement("prenotazione", omNs);
   	     OMElement arg0 = fac.createOMElement("ID", omNs);
   	     arg0.addChild(fac.createOMText(arg0, ID));
   	     method.addChild(arg0);
   	     OMElement arg1 = fac.createOMElement("num_posti", omNs);
   	     arg1.addChild(fac.createOMText(arg1, String.valueOf(num)));
   	     method.addChild(arg1);
    
        OMNamespace clienteHeaderNs = fac.createOMNamespace("http://ws.service.unisa.it.com", "em");
     	OMNamespace soapenvNs = fac.createOMNamespace("http://www.w3.org/2003/05/soap-envelope", "soapenv");
     	OMElement clienteHeader = fac.createOMElement("checkHeader", clienteHeaderNs);
     	clienteHeader.addChild(fac.createOMText(clienteHeader, cognome));
     	clienteHeader.addAttribute("role", "http://www.w3.org/2003/05/role/next", soapenvNs);
     	serviceClient.addHeader(clienteHeader);
           
           
        // invocazione del servizio utilizzando la MEP in-out
           OMElement result = serviceClient.sendReceive(method);
           
           // recupera il risultato
           String response = result.getFirstElement().getText();
           System.out.println(response);
		}

	}


	private static void eseguiPrenotazione() throws NumberFormatException, IOException 
	{
	ServiceClient service = new ServiceClient();
	EndpointReference endpointRef = new EndpointReference("http://localhost:8080/axis2/services/TravelOnline");
	Options opt = new Options();
	opt.setTo(endpointRef);
	opt.setSoapVersionURI(Constants.URI_SOAP12_ENV);
	service.setOptions(opt);
	OMFactory factory = OMAbstractFactory.getSOAP12Factory();
	OMNamespace ns = factory.createOMNamespace("http://service.unisa.it", "tns");

	int id, posti;
	String login;
	System.out.println("Inserire login:");
	login = console.readLine();
	System.out.println("Inserire id:");
	id = Integer.parseInt(console.readLine());
	System.out.println("Inserire posti:");
	posti = Integer.parseInt(console.readLine()); // effettuo il cast ad int x essere sicuro ke sia un numero

	OMElement method = factory.createOMElement("prenotazione", ns);
	OMElement arg0 = factory.createOMElement("ID", ns);
	arg0.addChild(factory.createOMText(arg0, "" + id));
	method.addChild(arg0);
	OMElement arg1 = factory.createOMElement("num_posti", ns);
	arg1.addChild(factory.createOMText(arg1, "" + posti));
	method.addChild(arg1);

	OMNamespace clienteHeaderNs = factory.createOMNamespace("http://ws.service.unisa.it.com", "em");
	OMNamespace soapenvNs = factory.createOMNamespace("http://www.w3.org/2003/05/soap-envelope", "soapenv");
	OMElement clienteHeader = factory.createOMElement("checkHeader", clienteHeaderNs);
	clienteHeader.addChild(factory.createOMText(clienteHeader, login));
	clienteHeader.addAttribute("role", "http://www.w3.org/2003/05/role/next", soapenvNs);
	service.addHeader(clienteHeader);

	OMElement result = service.sendReceive(method);

	// recupera il risultato
	String response = result.getFirstElement().getText();
	System.out.println(response);


	}

}


