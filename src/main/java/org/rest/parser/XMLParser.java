package org.rest.parser;
import generated.NewDataSet;
import generated.NewDataSet.Holidays;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class XMLParser {
	
	public static final String XML_FILE_NAME = "GetHolidaysForYear.xml";
	

	public List parseXML(String string) throws JAXBException
	{
		
			try {

				// Get an unmarshaller.
				JAXBContext jc;
				jc = JAXBContext.newInstance("generated");
				Unmarshaller u = jc.createUnmarshaller();

				// Build a DOM.
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource( new StringReader( string ) );
				Document doc = db.parse(is);

				// Traverse the DOM until 'NewDataSet' is reached.
				Element subtree = doc.getDocumentElement();
				Node node = subtree.getElementsByTagName("NewDataSet")
						.item(0);

				// Unmarshal 'NewDataSet'.
				JAXBElement<NewDataSet> dataSet = u.unmarshal(node,
						NewDataSet.class);
				
				// Print the holidays.
				List<Holidays> holidays = dataSet.getValue()
						.getHolidays();
				
				return holidays;
           
			} catch (Exception ex) {
				System.out.println("Caught Exception: " + ex.getMessage());
				return null;
			}

		}

	
		
	}
	


