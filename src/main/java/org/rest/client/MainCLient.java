package org.rest.client;

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

public class MainCLient {

	public static final String REST_URL="http://jsonplaceholder.typicode.com/posts";
	public static final int OK_STATUS=Response.Status.OK.getStatusCode();
	
	public static SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry();
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SessionFactory sessionFactory=createSessionFactory();
		Session session=sessionFactory.openSession();
		
		 ArrayList<User> userList = null;
		// Call the service and get the response
     Response response =ClientBuilder.newClient().target(REST_URL)
    		 .request(MediaType.APPLICATION_JSON).get();
     
     //process the response object
     
     StatusType status=response.getStatusInfo();
     int statusCode =status.getStatusCode();
     if(statusCode==OK_STATUS)
     {
    	 //System.out.println(response.readEntity(String.class));
    	 JsonParserClass jpc=new JsonParserClass();
    	 userList=(ArrayList<User>) jpc.parseJson(response.readEntity(String.class));
     }
     else
     {
    	 System.out.println("Service Returned Status : "+statusCode+"--"+status.getReasonPhrase());
     }
    
     session.beginTransaction();
     int i=0;
     for (i=0; i<userList.size(); i++ ) {
    	  
    	    session.save(userList.get(i));
    	    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
    	        //flush a batch of inserts and release memory:
    	        session.flush();
    	        session.clear();
    	    }
    	    
    	    
    	}
     session.getTransaction().commit();
		session.close();
     
     
     
	}

}
