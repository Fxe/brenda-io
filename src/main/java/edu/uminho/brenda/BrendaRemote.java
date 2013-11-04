package edu.uminho.brenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;

public class BrendaRemote {
	
	public static void main(String[] args) throws Exception
    {
		   Service service = new Service();
		   Call call = (Call) service.createCall();
		   String endpoint = "http://www.brenda-enzymes.org/soap2/brenda_server.php";
		   call.setTargetEndpointAddress( new java.net.URL(endpoint) );
		   //getOrganism
		   //getEcNumbersFromOrganism
		   call.setOperationName(new QName("http://soapinterop.org/", "getOrganism"));
		   String resultString = (String) call.invoke( new Object[] {"organism*Saccharomyces cerevisiae"});
		   
		   //call.setOperationName(new QName("http://soapinterop.org/", "getKmValue"));
		   //String resultString = (String) call.invoke( new Object[] {"ecNumber*1.1.1.1#organism*Mus musculus"} );
		   
		   String[] results = resultString.split("!");
		   Map<String, List<String>> ecNumbers = new HashMap<>();
		   
		   for (String str : results) {
			   String[] data = str.split("#");
			   Map<String, String> information = new HashMap<> ();
			   for (String rec : data) {
				   String[] d = rec.split("\\*");
				   information.put(d[0], d.length < 2 ? d[0] : d[1]);
			   }
			   
			   String ecnId = information.get("ecNumber");
			   if (ecNumbers.containsKey(ecnId)) {
				   ecNumbers.get(ecnId).add(str);
			   } else {
				   List<String> aux = new ArrayList<>();
				   aux.add(str);
				   ecNumbers.put(ecnId, aux);
			   }
		   }
		   System.out.println(ecNumbers.keySet().size());
    }
}
