/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class ConnectionException.java
 * 
 **/

package nl.dalthow.etaron.service;

import javax.xml.ws.WebServiceException;

public class ConnectionException extends Throwable 
{
	// Constructor
	
	public ConnectionException(WebServiceException error) 
	{
        super("An exception occurred while contacting the server, please try again later.", error);
    }
}
