/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Launcher.java
 * 
 **/

package nl.dalthow.etaron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Launcher implements ApplicationListener<ContextRefreshedEvent> 
{
	// Declaration
	
    private final String wsPort;
    private final String wsSoapVersion;

    private static final Logger log = LoggerFactory.getLogger(Launcher.class);
    
    
    // The first method that gets loaded
    
    public static void main(String[] args) 
    {
        new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
    }

    
    // Constructor 
    
    @Autowired
    public Launcher(@Value("${ws.port}") String wsPort, @Value("${ws.soap.version}") String wsSoapVersion) 
    {
        this.wsPort = wsPort;
        this.wsSoapVersion = wsSoapVersion;
    }

    
    // Gets triggered when the server started
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) 
    {
        log.info("Server started on port [{}] using simple object access protocol version [{}]", wsPort, wsSoapVersion);
    }
}
