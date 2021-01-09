import org.jibble.pircbot.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

import java.util.*;
import java.io.*;
import java.net.*;


public class MyBot extends PircBot{
	
	public MyBot() {
		this.setName("OsmansRobo");
		
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	    {
		//just say "facts" to get a random fact
		if (message.contains("facts")) {
				String fact[] = {""};
				facts(fact);
				sendMessage(channel, "Fact: " + fact[0]);
			}
		//need to send message "weather (zip_code)"
		//to get a response with the weather
			if (message.contains("weather")) {
				try {
					double[] temp = {0};
					weather(temp, message);
					sendMessage(channel, "Temperature: " + (int)temp[0]);
				
				}
				catch(Exception e){
					sendMessage(channel, "Something went wrong!");
				}
				
			} 
			//"Hello" to say hi
			if (message.contains("Hello")) {
				sendMessage(channel, "Hey " + sender + "! ");

			} 
		}
	//uses gson to convert json into a map
	public static Map<String, Object> jsonToMap(String str){
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken
				<HashMap<String, Object>>(){}.getType());
		return map;
	}
	
	void weather(double[] fahren, String message) {
		//my apikey from open weather
		String apikey = "e9f8734782784575dc6f140da12348ff";
		String location = message.replace("weather ", "");
		
		//creates the url
		String urls = "http://api.openweathermap.org/data/2.5/weather?q=" + 
		location + "&appid=" + apikey;
		
		//create a stringbuilder class
		StringBuilder result = new StringBuilder();
		try {
		//url object
		URL urlo = new URL(urls);
		
		//connect to the url
		HttpURLConnection con = (HttpURLConnection) urlo.openConnection();
		
		//create bufferedreader object to read data in
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		//input data from bufferedreader to string info
		String info;
		
		//while there is info in the connection it'll input into info 
		//then append it to result
		while((info = rd.readLine()) != null) {
			result.append(info);
		}
		//close bufferedreader once done
		rd.close();
		
		//parse info in result to a map
		Map<String, Object> respMap = jsonToMap(result.toString());
		//get info from main and parse it
		Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
		fahren[0] = (((double)mainMap.get("temp") - 273) * 1.8) + 32;
		
		}
		catch(Exception e) {
			System.out.println("Error occured");
            System.out.println(e.toString());
		}
	}
	
	static void facts(String[] fact) {
		String urls = "https://uselessfacts.jsph.pl/random.json?language=en";
		StringBuilder result = new StringBuilder();
		
		try {
		URL urlo = new URL(urls);
		
		//connect to the url
		HttpURLConnection con = (HttpURLConnection) urlo.openConnection();
		con.setRequestMethod("GET");
		//create bufferedreader object to read data in
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		//input data from bufferedreader to string info
		String info;
		//while there is info in the connection it'll input into info 
		//then append it to result
		while((info = rd.readLine()) != null) {
			result.append(info);
		}
		//close bufferedreader once done
		rd.close();
		//parse info in result to a map
		Map<String, Object> respMap = jsonToMap(result.toString());
		//get info from main and parse it
		
		
		fact[0] = respMap.get("text").toString();
		
		}
		catch(Exception e) {
			System.out.println("Error occured");
            System.out.println(e.toString());
		}
	}

}
