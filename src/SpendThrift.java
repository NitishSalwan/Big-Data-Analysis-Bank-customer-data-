
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.apache.commons.codec.binary.Base64;



public class SpendThrift {

	
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
		ArrayList<Integer> list =new ArrayList<Integer>();
		String financially_active="";
		int max_count=0;
		
		int limit=0;
		
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
			
			String payer=(String)jsonObject.get("payer_id");
			
			
			if(payer.trim().compareTo(customer_id)==0)
			{
				list.add((Integer)jsonObject.get("amount"));
			}			
		}
		
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	  
	  
	  ///////////////////////////////////////////////////////////////////////////
	  
	  
	  try {
			
		  
		String customer_id="56c66be6a73e492741507b92";
		  
		String hey1="http://api.reimaginebanking.com/accounts/" + customer_id + "/withdrawals?key=0cd2e1892e023a06c402b4521d0ed69e";
		URL url1 = new URL(hey1);
		System.out.println(url1.toString());
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("GET");
		conn1.setRequestProperty("Accept", "application/json");

		if (conn1.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn1.getResponseCode());
		}
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
			(conn1.getInputStream())));

		String output;
		StringBuilder total_input1=new StringBuilder("");
		//System.out.println("Output from Server .... \n");
		while ((output = br1.readLine()) != null) 
		{
			total_input1.append(output);
			//System.out.println();
			//System.out.println(output);
		}
		conn1.disconnect();
		br1.close();
		
		
		//System.out.println(total_input.toString().substring(0, total_input.toString().indexOf("}]") +2) + "}");
		
		System.out.println(total_input1.toString());
		
		if(!total_input1.toString().equals("[]"))
		{
		
			JSONObject obj1 = new JSONObject(total_input1.toString());			
		
			JSONArray msg1 = (JSONArray)obj1.get("results");
		
			//System.out.println("length.." + msg.length());
		
			for (int j = 0; j < msg1.length(); j++) 
			{
			
				JSONObject jsonObject = (JSONObject)msg1.get(j);
				String payer=(String)jsonObject.get("amount");
				System.out.println(payer);
			
			
				if(payer.trim().compareTo(customer_id)==0)
				{
					list.add((Integer)jsonObject.get("amount"));
				}			
			}
		
		
		}
		
		
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	  
	  
	  //////////////////////////////////////////////////////////////////////////
	  
	  
	  Collections.sort(list);
	  
	  if(!list.isEmpty())
	  limit=list.get(list.size()-1);
	  int counting=0;
	  
	  for(Integer in : list)
	  {
		  if(in>limit)
			  counting++;
	  }
	  
	  if( counting >(8*list.size())/10  )
	  {
		  System.out.println("Can apply higher interest rates");
	  }
	  
	  
	  
	  
	}

}