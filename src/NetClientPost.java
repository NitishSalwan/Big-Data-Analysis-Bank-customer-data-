import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetClientPost {

	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {

	  try {

		URL url = new URL("http://api.reimaginebanking.com/customers?key=0cd2e1892e023a06c402b4521d0ed69e");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

	
	String input = "{\r\n  \"first_name\": \"Vishu\",\r\n  \"last_name\": \"Salwan\",\r\n  \"address\": {\r\n      \"street_number\":\"7650\",\r\n     \"street_name\":\"mccallum\",\r\n     \"city\": \"Dallas\",\r\n     \"state\": \"TX\",\r\n     \"zip\": \"75252\"\r\n    }\r\n}";
	
	OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }

	}

}