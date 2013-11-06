package edu.uminho.brenda;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

public class BrendaRemote {
	
	private static String ENDPOINT = "http://www.brenda-enzymes.org/soap2/brenda_server.php";
	private static String NAMESPACEURI = "http://soapinterop.org/";
	
	private static String soapCall(String function, String args) throws ServiceException, MalformedURLException, RemoteException {
		Service service = new Service();
		Call call = (Call) service.createCall();
		String endpoint = ENDPOINT;
		call.setTargetEndpointAddress( new java.net.URL(endpoint) );
		call.setOperationName(new QName(NAMESPACEURI, function));
		
		return (String) call.invoke( new Object[] {args});
	}
	
	public static void main(String[] args) throws Exception
    {
		//"getOrganism", "organism*Saccharomyces cerevisiae"
		//getEcNumbersFromReaction
		//getReaction
		String resultString = soapCall("getReaction", "organism*Saccharomyces cerevisiae");
		for (String str : resultString.split("!")) {
			System.out.println(str);
		}
		System.out.println(resultString.split("!").length);
		//System.out.println(resultString);
		   //call.setOperationName(new QName("http://soapinterop.org/", "getKmValue"));
		   //String resultString = (String) call.invoke( new Object[] {"ecNumber*1.1.1.1#organism*Mus musculus"} );
		   
//		   String[] results = resultString.split("!");
//		   Map<String, List<String>> ecNumbers = new HashMap<>();
//		   
//		   for (String str : results) {
//			   String[] data = str.split("#");
//			   Map<String, String> information = new HashMap<> ();
//			   for (String rec : data) {
//				   String[] d = rec.split("\\*");
//				   information.put(d[0], d.length < 2 ? d[0] : d[1]);
//			   }
//			   
//			   String ecnId = information.get("ecNumber");
//			   if (ecNumbers.containsKey(ecnId)) {
//				   ecNumbers.get(ecnId).add(str);
//			   } else {
//				   List<String> aux = new ArrayList<>();
//				   aux.add(str);
//				   ecNumbers.put(ecnId, aux);
//			   }
//		   }
//		   System.out.println(ecNumbers.keySet().size());
//		   
//		   System.out.println(ecNumbers.get(ecNumbers.keySet().iterator().next()));
    }
}
