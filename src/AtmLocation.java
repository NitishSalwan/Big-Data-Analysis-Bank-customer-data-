
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.apache.commons.codec.binary.Base64;

public class AtmLocation {

	// http://localhost:8080/RESTfulExample/json/product/get
	public static int func()
	{
		int range = 6;
		return (int)(Math.random() * range) + 3;

	}
	public static void main(String[] args) throws Exception {

		StringBuilder geocodePair=new StringBuilder();
		
		
	  try {

	for(int i=1;i<=63;i++)
	{
		String hey="http://api.reimaginebanking.com/atms?key=0cd2e1892e023a06c402b4521d0ed69e&page=" + i;
		
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
		
		JSONObject obj = new JSONObject(total_input.toString().substring(0, total_input.toString().indexOf("}]") +2) + "}");			
		
		JSONArray msg = (JSONArray)obj.get("data");
		
		//System.out.println("length.." + msg.length());
		
		for (int j = 0; j < msg.length(); j++) 
		{
			//System.out.println(msg.get(i));
			
			JSONObject jsonObject = (JSONObject)msg.get(j);
			//System.out.println(jsonObject.get("name"));
			//System.out.println(((JSONObject)jsonObject.get("geocode")).get("lat"));
			geocodePair.append(((JSONObject)jsonObject.get("geocode")).get("lng"));
			geocodePair.append("@@");
			geocodePair.append(((JSONObject)jsonObject.get("geocode")).get("lat"));
			geocodePair.append("##");
		}
				
		
	  }
		
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	  
	  System.out.println(geocodePair.toString());
	  
	  
	  String[] geo = geocodePair.toString().split("##");
	  int output=45;
	  for(int i=0;i<geo.length-1;i++)
	  {
	  
		  String[] longLat= geo[i].split("@@");
		  
	  String stringUrl = "https://jgentes-Crime-Data-v1.p.mashape.com/crime?enddate=9/30/2016&lat=" + longLat[1] + "&long=" + longLat[0] + "&startdate=1/1/1990";
      URL url = new URL(stringUrl);
      URLConnection uc = url.openConnection();
      
      uc.setRequestProperty("X-Mashape-Key", "mun4hzJnrLmshxh0ybrKJLkS6xsrp1cGoFhjsnIZT6bbhHakrx");
      uc.setRequestProperty("X-Requested-With", "Curl");

      //String userpass = "X-Mashape-Key" + ":" + " mun4hzJnrLmshxh0ybrKJLkS6xsrp1cGoFhjsnIZT6bbhHakrx";
      //String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
      //uc.setRequestProperty("Authorization", basicAuth);

      BufferedReader br1 = new BufferedReader(new InputStreamReader(
  			(uc.getInputStream())));
      
      
      String output1;
      while ((output1 = br1.readLine()) != null) 
		{
    	  System.out.println(output);
    	  output=func();
		}
	  }
	}

}