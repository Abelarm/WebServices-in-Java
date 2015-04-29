package it.unisa.data;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ClientiManager
{
	
	private static Document doc = null;
	public static String xml_clienti = "TravelOnline/clienti.xml";
	
	static
	{
		MessageContext msg_context = MessageContext.getCurrentMessageContext();
		ServletContext servlet_context = (ServletContext) msg_context.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		xml_clienti = servlet_context.getRealPath(xml_clienti);
		
	}

	public static void Init() throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		System.out.println(xml_clienti);
		doc = builder.parse(new File(xml_clienti));
		doc.getDocumentElement().normalize();
	}
	
	public static boolean checkCliente(String login)
	{
		
		try
		{
			if (doc == null)
				Init();
			NodeList list = doc.getElementsByTagName("login");
			for (int i = 0, l = list.getLength(); i != l; ++i)
			{
				Node n = list.item(i);
				if (n.getFirstChild().getTextContent().equals(login))
				{
					return true;
				}
			}
		}	
		catch (Exception e)
		{
			return false;
		}
		return false;
		
	}
	
	public static void writeClient(Cliente c)
	{
		try
		{
			if (doc == null)
				Init();
			Node clienti = doc.getElementsByTagName("clienti").item(0);
			Node new_client = doc.createElement("cliente");
			new_client.appendChild(doc.createElement("login")).setTextContent(c.getLogin());
			new_client.appendChild(doc.createElement("nome")).setTextContent(c.getNome());
			new_client.appendChild(doc.createElement("cognome")).setTextContent(c.getCognome());
			new_client.appendChild(doc.createElement("email")).setTextContent(c.getEmail());
			clienti.appendChild(new_client);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xml_clienti));
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
