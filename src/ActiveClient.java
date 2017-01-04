
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.apache.commons.codec.binary.Base64;

class ValueComparator implements Comparator<String>{
	 
	HashMap<String, Integer> map = new HashMap<String, Integer>();
 
	public ValueComparator(HashMap<String, Integer> map){
		this.map.putAll(map);
	}
 
	@Override
	public int compare(String s1, String s2) {
		if(map.get(s1) >= map.get(s2)){
			return -1;
		}else{
			return 1;
		}	
	}
}

public class ActiveClient {

	
	public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
	public static int func()
	{
		int range = 6;
		return (int)(Math.random() * range) + 3;

	}
	public static void main(String[] args) throws Exception {

		StringBuilder geocodePair=new StringBuilder();
		
		String financially_active="";
		int max_count=0;
		
	  try {
	
		  HashMap<String,Integer> countMap=new HashMap<String,Integer>();
		  
		  String customer_id="56c66be6a73e492741507b92";
		  
		String hey="http://api.reimaginebanking.com/enterprise/transfers?key=0cd2e1892e023a06c402b4521d0ed69e";
		URL url = new URL(hey);
		System.out.println(url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		StringBuilder total_input=new StringBuilder("");
		//System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) 
		{
			total_input.append(output);
			//System.out.println();
			//System.out.println(output);
		}
		conn.disconnect();
		br.close();
		
		
		//System.out.println(total_input.toString().substring(0, total_input.toString().indexOf("}]") +2) + "}");
		
		JSONObject obj = new JSONObject(total_input.toString());			
		
		JSONArray msg = (JSONArray)obj.get("results");
		
		//System.out.println("length.." + msg.length());
		
		for (int j = 0; j < msg.length(); j++) 
		{
			
			JSONObject jsonObject = (JSONObject)msg.get(j);
			//System.out.println(jsonObject.get("payee_id"));
			//System.out.println(((JSONObject)jsonObject.get("geocode")).get("lat"));
			String payee=(String)jsonObject.get("payee_id");
			System.out.println(payee);
			
			//geocodePair.append("@@");
			String payer=(String)jsonObject.get("payer_id");
			
			String entry;
			
			if(payee.trim().compareTo(customer_id)==0)
			{
				entry=payer;
			}
			else if(payer.trim().compareTo(customer_id)==0)
			{
				entry=payee;
			}
			else
			{
				entry=null;
			}
			if(entry!=null)
			{
				if(!countMap.containsKey(entry))
				{
					countMap.put(entry,1);
					if(max_count<1)
					{
						
						max_count=1;
						financially_active=entry;
					}
				}
				else
				{
					countMap.put(entry,countMap.get(entry) + 1);
					if(max_count<countMap.get(entry))
					{
						
						max_count=countMap.get(entry);
						financially_active=entry;
					}
				}
			}
			
			
			
		}
		
		
		
		
		
		System.out.println("Most Active Person with you is "
				+ "\n	ID --> " + financially_active 
				+ "\n	with Number of transactions = " + max_count);
	  
		
		
		// Sort the map so that we can check who are the users that are constantly engaged in transactions.
		//We can take advertisement decisions based on that.
		
		TreeMap<String, Integer> sortedMap = sortMapByValue(countMap);  
		
		for(Map.Entry<String, Integer> map : sortedMap.entrySet())
		{
			
			System.out.println(map.getKey() + " --> " + map.getValue());
			
		}
		
		
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	  
	  
	  
	}

}