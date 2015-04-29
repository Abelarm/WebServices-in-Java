package it.unisa.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VacanzeLoader
{

	public static String pacchetti = "TravelOnline/pacchetti.xml";
	
	static
	{
		MessageContext msg_context = MessageContext.getCurrentMessageContext();
		ServletContext servlet_context = (ServletContext) msg_context.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		pacchetti = servlet_context.getRealPath(pacchetti);
	}


	private static String r_string(Element e, String field)
	{
		Node node = e.getElementsByTagName(field).item(0);
		return node.getFirstChild().getTextContent();
	}
	
	private static int r_int(Element e, String field)
	{
		return Integer.parseInt(r_string(e, field));
	}
	
	
	public static Vacanza[] loadVacanze(String categoria) //string(filtra la categoria)/null(senza filtro)
	{
		Vacanza[] ret = null;
		List<Vacanza> vec = new ArrayList<Vacanza>();
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(pacchetti);
			Document doc = builder.parse(new File(pacchetti));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("pacchetto");
			for (int i = 0; i < list.getLength(); i++) 
			{
				Element e = (Element) list.item(i);	
				int id = Integer.parseInt(e.getAttribute("id"));
				Vacanza v = new Vacanza(
											id,
											r_string(e, "nome"),
											r_string(e, "categoria"),
											r_int(e, "prezzo")
				);
				if (categoria == null)
					vec.add(v);
				else if (categoria.toLowerCase().equals(v.getCategoria().toLowerCase()))
				{
					vec.add(v);
				}
				System.out.println("ok " + v);
			}
		}
		catch (Exception e)
		{
			System.out.println("not ok " + e);
			vec.clear();
			//return null;
		}
		ret = new Vacanza[vec.size()];
		for (int i = 0; i < vec.size(); i++)
			ret[i] = vec.get(i);
		return ret;
		
	}
/*
	public static void main(String[] args)
	{
		Vector<Vacanza> r = loadVacanze(null);
		for (Vacanza e : r)
			System.out.println(e);
	}
*/
	
}
