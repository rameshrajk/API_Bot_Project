import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//Dad joke API service
public class DadAPICall {
	public static String dadJokeCall() {
		try {		
			//create API endpoint
			URL url = new URL("https://icanhazdadjoke.com/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//custom User-Agent header and specified API response format for 
			con.addRequestProperty("User-Agent", "My Library (rrk170000@utdallas.edu)");
			con.addRequestProperty("Accept", "text/plain");//API response format specified as plain/text; can also use application/json
			//create buffered object
	        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        StringBuilder result = new StringBuilder();	 
			//read in and add lines to result
	        String line;
	        while ((line = reader.readLine()) != null) {
	            result.append(line);
	        }
	        reader.close();
	        //parse json and get joke
	        //String joke = parseJSON(result.toString());
	        return result.toString();  
	        //return stemp;
		} catch (Exception e) {
			//System.out.println("Error: Exception " + e);
			return "Error: Exception e";
		}	
	}
	
	//if you want get joke from parsing json
	/*private static String parseJSON(String json) {
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        //get object under "joke" key section
        JsonObject joke = object.getAsJsonObject("joke");
        //joke from "joke"
        String joke = main.get("joke");
        return joke;
	}*/
	
	//testing
    /*public static void main(String[] args) {
        System.out.println(dadJokeCall());
    }*/
}