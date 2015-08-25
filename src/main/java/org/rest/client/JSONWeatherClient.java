package org.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.rest.model.User;
import org.rest.parser.JsonParserClass;

public class JSONWeatherClient {

	public static final String REST_URL = "Http://api.openweathermap.org/data/2.5/weather?zip=60661";
	public static final int OK_STATUS = Response.Status.OK.getStatusCode();
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

	/*
	 * public static SessionFactory createSessionFactory() { Configuration
	 * configuration = new Configuration(); configuration.configure();
	 * ServiceRegistry serviceRegistry = new
	 * ServiceRegistryBuilder().applySettings( configuration.getProperties()).
	 * buildServiceRegistry(); SessionFactory sessionFactory =
	 * configuration.buildSessionFactory(serviceRegistry); return
	 * sessionFactory; }
	 */
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		HttpURLConnection con = null;
		InputStream is = null;
		String location = "chicago";
		String jsonString=null;

		try {

			// Another way to call the service --IMPORTANT
			/*
			 * con = (HttpURLConnection) ( new URL(BASE_URL +
			 * location)).openConnection(); 
			 * con.setRequestMethod("GET");
			 * con.setDoInput(true); 
			 * con.setDoOutput(true); 
			 * con.connect(); 
			 * URL ul=new URL(BASE_URL + location); 
			 * String s1=ul.toString();
			 * System.out.println(s1);
			 */

			// Let's read the response
			/*
			 * StringBuffer buffer = new StringBuffer(); 
			 * is =con.getInputStream(); String s=getStringFromInputStream(is);
			 * System.out.println(s);
			 */

			// Call the service and get the response

			Response response = ClientBuilder.newClient().target(REST_URL).request(MediaType.APPLICATION_JSON).get();
			// process the response object

			StatusType status = response.getStatusInfo();
			int statusCode = status.getStatusCode();
			if (statusCode == OK_STATUS) {
			
				jsonString=response.readEntity(String.class);
				System.out.println(jsonString);
				JsonParserClass jpc=new JsonParserClass();
				jpc.parseWeatherJson(jsonString);

			} else {
				System.out.println("Service Returned Status : " + statusCode + "--" + status.getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
