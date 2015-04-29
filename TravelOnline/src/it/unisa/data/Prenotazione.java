package it.unisa.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Prenotazione {
	
	public static String pacchetti = "TravelOnline/pacchetti.xml";
	
	static
	{
		MessageContext msg_context = MessageContext.getCurrentMessageContext();
		ServletContext servlet_context = (ServletContext) msg_context.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		pacchetti = servlet_context.getRealPath(pacchetti);
		
	}
	
	public static String prenota(String ID, int num_posti){
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(pacchetti));
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("pacchetto");
			for (int i = 0; i < list.getLength(); i++) {
				Element el=(Element) list.item(i);
				String idPacchetto=el.getAttribute("id");
				if(ID.equals(idPacchetto)){
					Element prezzoElement=(Element) (el.getElementsByTagName("prezzo")).item(0);
					double prezzo=Double.parseDouble(prezzoElement.getFirstChild().getTextContent());
					prezzo=prezzo*num_posti;
					return String.valueOf(prezzo);
				}
			}
			
		}catch(Exception e){
			System.out.println("Errore!!");
			e.printStackTrace();
			return "C' un errore nella ricerca del file";
		}
		return "ID non trovato";
	}
	
	
	public static void main(String args[]){
		try{
			BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Inserisci l'id");
			String ID=in.readLine();
			System.out.println("Inserisci la quantitˆ");
			int num= Integer.parseInt(in.readLine());
			System.out.println(Prenotazione.prenota(ID, num));
			}catch(Exception e){
				
			}
	}
}

