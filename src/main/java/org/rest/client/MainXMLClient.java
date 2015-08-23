package org.rest.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.rest.model.Holiday;
import org.rest.model.User;
import org.rest.parser.XMLParser;

import generated.NewDataSet.Holidays;

public class MainXMLClient {

	public static final String REST_URL="http://www.holidaywebservice.com/Holidays/HolidayService.asmx/GetHolidaysForYear?countryCode=US&year=2015";
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
	public static void main(String[] args) throws Exception {
		SessionFactory sessionFactory=createSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		 List<Holidays> holidayList = null;
		 ArrayList<User> userList = null;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd");
		// Call the service and get the response
     Response response =ClientBuilder.newClient().target(REST_URL)
    		 .request(MediaType.APPLICATION_JSON).get();
     
     //process the response object
     
     StatusType status=response.getStatusInfo();
     int statusCode =status.getStatusCode();
     if(statusCode==OK_STATUS)
     {
    	 //System.out.println(response.readEntity(String.class));
    	 XMLParser ps=new XMLParser();
    	 holidayList=ps.parseXML(response.readEntity(String.class));
    	 for (Holidays h : holidayList) {
			Holiday hldy=new Holiday();
			String s=h.getDate().getMonth()+"/"+h.getDate().getDay()+"/"+h.getDate().getYear(); 
			System.out.println("S-==="+s);
			hldy.setDate(s);
			hldy.setName(h.getName());
			hldy.setKey(h.getKey());
			session.save(hldy);
			}
     }
     else
     {
    	 System.out.println("Service Returned Status : "+statusCode+"--"+status.getReasonPhrase());
     }
    session.getTransaction().commit();
    session.close();
     
     
	}

}
