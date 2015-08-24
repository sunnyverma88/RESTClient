package org.rest.parser;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rest.model.User;
import org.rest.utils.Utility;


public class JsonParserClass {

	public List parseWeatherJson(String string)
	{
		/* Below XML is getting parsed
				 {
		  "coord": {
		    "lon": -87.65,
		    "lat": 41.85
		  },
		  "weather": [
		    {
		      "id": 801,
		      "main": "Clouds",
		      "description": "few clouds",
		      "icon": "02n"
		    }
		  ],
		  "base": "cmc stations",
		  "main": {
		    "temp": 291.07,
		    "pressure": 1014,
		    "humidity": 46,
		    "temp_min": 288.75,
		    "temp_max": 294.15
		  },
		  "wind": {
		    "speed": 3.6,
		    "deg": 280
		  },
		  "clouds": {
		    "all": 20
		  },
		  "dt": 1440386732,
		  "sys": {
		    "type": 1,
		    "id": 961,
		    "message": 0.0141,
		    "country": "US",
		    "sunrise": 1440414480,
		    "sunset": 1440463029
		  },
		  "id": 4887398,
		  "name": "Chicago",
		  "cod": 200
		}
		*/
		
		JsonObject c=null;
		try
		{   
			
			InputStream is =Utility.getInputStreamFromString(string);
			JsonReader rdr=Json.createReader(is);
			JsonObject jsonObj=rdr.readObject();
			rdr.close();
			JsonArray jsonArray=jsonObj.getJsonArray("weather");
			for(int i = 0; i < jsonArray.size(); i++){
		          c = jsonArray.getJsonObject(i);
		          System.out.println(c.getInt("id"));
			}
			JsonObject innerJsonObject=jsonObj.getJsonObject("main");
			System.out.println(innerJsonObject.getInt("temp"));
			
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//Same parsing using org.jason. Above was javax.json.
		JSONObject jsonObj1=new JSONObject(string);
		System.out.println(jsonObj1.getString("base"));
		JSONObject innetJsonObj1=jsonObj1.getJSONObject("main");
		System.out.println(innetJsonObj1.getInt("temp"));
		
		
		return null;
		
	}
	public List parseJson(String string)
	{
		String jsonString = string;

	   
	    JSONArray topArray; 
	    try {
	         // Getting your top array
	         topArray = new JSONArray(jsonString);
	         ArrayList<User> userList=new ArrayList<User>();
	          // looping through All elements
	          for(int i = 0; i < topArray.length(); i++){
	          JSONObject c = topArray.getJSONObject(i);


	         
	          // Storing each json item in variable
	          int userId = c.getInt("userId");
	          int id = c.getInt("id");
	          String tittle=c.getString("title");
	          System.out.println("Nodename" + userId +"-"+ id);
	          
	          //add rest of the json data to NodePOJO class
	          User user=new User();
	          user.setId(id);
	          user.setUserId(userId);
	          user.setTittle(tittle);
	          //the object to list
	         userList.add(user);
	          
	        }
	          return userList;
	    } catch (JSONException e) {
	            e.printStackTrace();
	            return null;
	     }
		
}
	
	
}