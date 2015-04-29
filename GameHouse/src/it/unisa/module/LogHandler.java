package it.unisa.module;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.transport.http.HTTPConstants;

public class LogHandler extends AbstractHandler implements Handler{
	//private static final Log log = LogFactory.getLog(LogHandler.class);
	private String name;
	private String path = "Giochi/log.txt";

		public String getName() {
			return name;
		}

		public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
			System.out.println("dove mai si fermerˆ??");

			SOAPBody body = msgContext.getEnvelope().getBody();

			OperationContext opContext = msgContext.getOperationContext();
			SOAPHeader head = (msgContext.getEnvelope()).getHeader();
			
			@SuppressWarnings("unchecked")
			Iterator<SOAPHeaderBlock> it = head.examineHeaderBlocks("http://www.w3.org/2003/05/role/next");
			System.out.println(it.toString());
			while(it!=null && it.hasNext()) {
				
				SOAPHeaderBlock headEl =  (SOAPHeaderBlock)it.next();
				System.out.println("Header"+headEl.getLocalName());
				// cerca l'header block "checkHeader" e lo processa
				if( headEl.getLocalName().equalsIgnoreCase("scontoHeader") ) {
				// Recupera il context della servlet
				ServletContext ctx = (ServletContext) msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETCONTEXT);
				PrintWriter out=null;
				String realFile = ctx.getRealPath(path);
				System.out.println(realFile);

				try {
					out = new PrintWriter(new FileOutputStream(realFile, true));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 		

				// recupera il valore di ritorno del servizio dal body
			
				String risposta = body.getFirstElement().getFirstElement().getText();
				

				// recupera i dati della richiesta dall'OperationContext
				String sconto = (String)opContext.getProperty("sconto");
				String codice = (String)opContext.getProperty("codice");
				
				System.out.println(sconto+codice);

				// salva la notifica nel file
				out.print("Hai acquistato il prodotto avente codice: "+codice);
				//if(avail)
				if(sconto.equalsIgnoreCase("-1"))	
					out.println(risposta);
				else
					out.println(risposta);
				out.close();

				return InvocationResponse.CONTINUE;
			}
		}
			return InvocationResponse.CONTINUE;

			        
		}

		public void revoke(MessageContext msgContext) {
		}

		public void setName(String name) {
			this.name = name;
		}

}
