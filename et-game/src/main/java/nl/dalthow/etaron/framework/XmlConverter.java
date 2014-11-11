/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class XmlConverter.java
 *
 **/

package nl.dalthow.etaron.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

public class XmlConverter 
{
	// Declaration
	
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
 
	
	// Getters
	
	public Marshaller getMarshaller() 
	{
		return marshaller;
	}

	public Unmarshaller getUnmarshaller() 
	{
		return unmarshaller;
	}
 
	
	// Setters
	
	public void setMarshaller(Marshaller marshaller) 
	{
		this.marshaller = marshaller;
	}
 
	public void setUnmarshaller(Unmarshaller unmarshaller)
	{
		this.unmarshaller = unmarshaller;
	}
 
	public void convertFromObjectToXML(KeyMap object, String path) throws IOException 
	{
		FileOutputStream outputStream = null;
		
		try 
		{
			outputStream = new FileOutputStream(path);
			getMarshaller().marshal(object, new StreamResult(outputStream));
		} 
		
		finally 
		{
			if(outputStream != null) 
			{
				outputStream.close();
			}
		}
	}
 
	public Object convertFromXMLToObject(String path) throws IOException 
	{
		FileInputStream inputStream = null;
	
		try 
		{
			inputStream = new FileInputStream(path);
			
			return getUnmarshaller().unmarshal(new StreamSource(inputStream));
		} 
		
		finally 
		{
			if(inputStream != null) 
			{
				inputStream.close();
			}
		}
	}
}