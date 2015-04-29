package it.unisa.module;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.transport.http.HTTPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class scontoHandler extends AbstractHandler implements Handler{
	private String name;
	private String path = "Giochi/clienti.xml";
	
	public String getName() {
		return name;
	}


	@SuppressWarnings("unchecked")
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		System.out.println("invoke dell inflow");
		ServletContext servletContext = (ServletContext)msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
		path=servletContext.getRealPath(path);
		
		SOAPEnvelope env = msgContext.getEnvelope();
		SOAPHeader head = env.getHeader();
		if(head==null)
			return InvocationResponse.CONTINUE;
		
		OperationContext opContext = msgContext.getOperationContext();
		
		// recupera gli header block diretti al role "http://www.w3.org/2003/05/role/next"
		Iterator<SOAPHeaderBlock> it = head.examineHeaderBlocks("http://www.w3.org/2003/05/role/next");
		System.out.println(it.toString());
		while(it!=null && it.hasNext()) {
			
			SOAPHeaderBlock headEl =  (SOAPHeaderBlock)it.next();
			System.out.println(headEl.getLocalName());
			
			try {
				// cerca l'header block "checkHeader" e lo processa
				if( headEl.getLocalName().equalsIgnoreCase("scontoHeader") ) {
					
					headEl.setProcessed();
			
					String ID = headEl.getText();
					
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new File(path));
					doc.getDocumentElement().normalize();
					NodeList list= doc.getElementsByTagName("cliente");
					NodeList listsconto=doc.getElementsByTagName("sconto");
					for (int i = 0; i < list.getLength(); i++) {
						System.out.println(list.item(i).getAttributes().item(0).getTextContent()+ID);
						if(list.item(i).getAttributes().item(0).getTextContent().equals(ID)){
							System.out.println("ID");
							Element e = (Element) listsconto.item(i);
							String sconto= e.getFirstChild().getTextContent();
							
							
														
							// recupera i dati dal body e li salva come property nell'OperationContext
							OMElement method = env.getBody().getFirstElement();
							Iterator<OMElement> args = (Iterator<OMElement>) method.getChildElements();
							String codice = args.next().getText();
							OMFactory factory1 = OMAbstractFactory.getSOAP12Factory();
							OMNamespace ns = factory1.createOMNamespace("http://service.unisa.it", "tns");
							OMElement arg0 = factory1.createOMElement("sconto", ns);
							arg0.addChild(factory1.createOMText(arg0, sconto));
							method.addChild(arg0);
							opContext.setProperty("codice", codice);
							opContext.setProperty("sconto",sconto);
							System.out.println(opContext.getProperty("codice"));
							System.out.println(opContext.getProperty("sconto"));
							return InvocationResponse.CONTINUE;
						}
					}
				} 

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("dovrebbe stampare questo visto che non ci sono header");
		return InvocationResponse.CONTINUE;        
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
