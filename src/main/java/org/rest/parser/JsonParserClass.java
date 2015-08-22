package org.rest.parser;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rest.model.User;


public class JsonParserClass {

	
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