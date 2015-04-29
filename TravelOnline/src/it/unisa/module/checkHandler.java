package it.unisa.module;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class checkHandler extends AbstractHandler implements Handler{
	
	private String name;
	private String path = "TravelOnline/clienti.xml";
	
	public String getName() {
		return name;
	}


	@SuppressWarnings("unchecked")
	@Override
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		System.out.println("Entra nell'invoke?");
		ServletContext servletContext = (ServletContext)msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		path=servletContext.getRealPath(path);
		
		SOAPEnvelope env = msgContext.getEnvelope();
		SOAPHeader head = env.getHeader();
		if(head==null)
			return InvocationResponse.CONTINUE;

		
		// recupera gli header block diretti al role "http://www.w3.org/2003/05/role/next"
		Iterator<SOAPHeaderBlock> it = head.examineHeaderBlocks("http://www.w3.org/2003/05/role/next");
		System.out.println(it.toString());
		while(it!=null && it.hasNext()) {
			
			System.out.println("ciclo fro");
			SOAPHeaderBlock headEl =  (SOAPHeaderBlock)it.next();

			try {
				// cerca l'header block "checkHeader" e lo processa
				if( headEl.getLocalName().equalsIgnoreCase("checkHeader") ) {
					
					headEl.setProcessed();
					String cognome = headEl.getFirstElement().getText();
					
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new File(path));
					doc.getDocumentElement().normalize();
					NodeList list= doc.getElementsByTagName("cognome");
					for (int i = 0; i < list.getLength(); i++) {
						if(list.item(i).getFirstChild().getTextContent().equalsIgnoreCase(cognome)){
							System.out.println("qui dovrebbe trovarlo");
							return InvocationResponse.CONTINUE;
						}
					}
				} 

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return InvocationResponse.ABORT;        
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
