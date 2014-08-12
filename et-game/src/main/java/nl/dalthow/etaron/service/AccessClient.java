/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class AccessClient.java
 *
 **/

package nl.dalthow.etaron.service;

import nl.dalthow.etaron.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceException;

@Component
public class AccessClient 
{
	// Declaration
	
    @Resource 
    private AccessService wsClient;

    @Value("${ws.server.version:UNKNOWN}")
    private String expectedServerVersion;

    private static final Logger logger = LoggerFactory.getLogger(AccessClient.class);
    
    
    // Version verification 
    
    @PostConstruct
    private void postInitVerification() 
    {

    }

    
    // Login method
    
    public User login(String username, String password) throws ConnectionException 
    {
        try 
        {
            return wsClient.login(username, password);
        } 
        
        catch(WebServiceException error) 
        {
            throw new ConnectionException(error);
        }
    }

    public void saveSettings(User user) 
    {
        wsClient.saveSettings(user);
        logger.debug("Updated user settings");
    }
    
    public void saveScores(User user) 
    {
        wsClient.saveScores(user);
        logger.debug("Updated user scores");
    }
}
