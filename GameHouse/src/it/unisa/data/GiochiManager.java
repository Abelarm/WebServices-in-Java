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
public class GiochiManager {
	
public static String giochi = "Giochi/store.xml";
	
	static
	{
		MessageContext msg_context = MessageContext.getCurrentMessageContext();
		ServletContext servlet_context = (ServletContext) msg_context.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		giochi = servlet_context.getRealPath(giochi);
	}
	
	NodeList Listagiochi;
	
	public static String getAll(){
		String res="";
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(giochi);
			Document doc = builder.parse(new File(giochi));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("gioco");
			for (int i = 0; i < list.getLength(); i++) {
				Element e = (Element) list.item(i);	
				res=res+"[Titolo:"+e.getElementsByTagName("titolo").item(0).getFirstChild().getTextContent()+"\n";
				res=res+"SoftwareHouse:"+e.getElementsByTagName("softwarehouse").item(0).getFirstChild().getTextContent()+"\n";
				res=res+"Genere:"+e.getElementsByTagName("genere").item(0).getFirstChild().getTextContent()+"\n";
				res=res+"Anno:"+e.getElementsByTagName("anno").item(0).getFirstChild().getTextContent()+"\n";
				res=res+"Prezzo:"+e.getElementsByTagName("prezzo").item(0).getFirstChild().getTextContent()+"]\n";
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	public static double getPrezzo(String ID){
		double prezzo=-1;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(giochi);
			Document doc = builder.parse(new File(giochi));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("gioco");
			for (int i = 0; i < list.getLength(); i++) {
				Element e = (Element) list.item(i);	
				if(e.getAttribute("cod").equals(ID))
					prezzo=Double.parseDouble(e.getElementsByTagName("prezzo").item(0).getFirstChild().getTextContent());
			}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
		return prezzo;
	}
}
