//import java.util.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat; 

//API weather service, connects to OpenWeather API, parses information with parseJson function, and returns temperature with weatherCall function,
public class APICall {
	
	public static String weatherCall(String city) throws IOException {
		try {
			//prompt for city
			//Scanner input = new Scanner(System.in);
			//System.out.print("Which city do you want to find the weather for?");
			//city = input.next();
			
			//create API endpoint with city
			String key = "dcbb1ea265643b4ba675346a697c6d1d";//API key
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			//create buffered object
	        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        StringBuilder result = new StringBuilder();	        
			//read in and add lines to result
	        String line;
	        while ((line = reader.readLine()) != null) {
	            result.append(line);
	        }
	        reader.close();
	        
	        //parse json and put in temp
	        double temp = parseJSON(result.toString());
	        
			//convert temp to Kelvin
	        temp = (temp - 273.15) * 1.8 + 32;
	        DecimalFormat df = new DecimalFormat("0.##");//to cut off decimals
	        df.setRoundingMode(RoundingMode.DOWN);
	        temp = Double.valueOf(df.format(temp));
	        String stemp = df.format(temp);
	        
	        //print temp
	        //System.out.println("The temperature in " + city + " is currently " + stemp + "\u00B0");  
	        return stemp;//return temp
		} catch (Exception e) {
			//System.out.println("Error: Exception " + e);
			return "Error: Exception e";
		}	
	}
	
	//function to parse json and return temp
	private static double parseJSON(String json) {
		//parse json and return as object
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        //get object under "main" key section
        JsonObject main = object.getAsJsonObject("main");
        //temp from main
        double temp = main.get("temp").getAsDouble();
        return temp;
	}
	
}