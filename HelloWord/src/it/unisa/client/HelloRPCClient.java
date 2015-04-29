package it.unisa.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class HelloRPCClient {
	 
	
	public static void main(String[] args) throws AxisFault{ 
		RPCServiceClient serviceClient = new RPCServiceClient();  
		Options opt = serviceClient.getOptions();
		EndpointReference er = new EndpointReference("http://localhost:8080/axis2/services/HelloWorld");
		opt.setTo(er);
		QName opEcho= new QName ("http://services.unisa.it", "sayHello");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));  
		try{
			while(true) {
				System.out.println("Enter the name (ctrl + d to end)");
				String message = console.readLine();
				Object[] opEchoArgs = null;
				Class[] returnTypes = new Class[]{String.class};
				Object[] response = null;
				opEchoArgs= new Object[]{message};
				response= serviceClient.invokeBlocking(opEcho, opEchoArgs, returnTypes);
				String returnValue=(String)response[0];
				System.out.println("Spedito  il messaggio: "+message);
				System.out.println("Ricevuto il messaggio: "+ returnValue);  
			}
		}catch(Exception e){
			e.printStackTrace();
		}     
	}
} 