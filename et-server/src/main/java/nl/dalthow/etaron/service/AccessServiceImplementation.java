/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class AccessServiceImplementation.java
 * 
 **/

package nl.dalthow.etaron.service;

import nl.dalthow.etaron.model.User;
import nl.dalthow.etaron.db.repository.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service("accessServiceImpl")
@WebService
(
    serviceName = "access",
    name = "accessBinding",
    portName = "accessPort",
    endpointInterface = "nl.dalthow.etaron.service.AccessService"
)
public class AccessServiceImplementation implements AccessService 
{
	// Declaration
	
    @Value("${app.version}")
    private String version;

    @Autowired
    private DataObject dataObject;

    
    // Getter
    
    @Override
    public String getVersion() 
    {
        return version;
    }

    
    // Login implementation 
    
    @Override
    public User login(String username, String password) 
    {
        return dataObject.findUser(username, password);
    }

    
    // Save settings implementation
    
    @Override
    public void saveSettings(User user) 
    {
        dataObject.updateSettings(user);
    }

    
    // Save scores implementation
    
    @Override
    public void saveScores(User user)
    {
       dataObject.updateScores(user);
    }
}
