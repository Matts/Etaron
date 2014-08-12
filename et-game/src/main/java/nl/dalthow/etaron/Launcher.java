/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Launcher.java
 * 
 **/

package nl.dalthow.etaron;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher 
{
	// The first method that gets loaded
	
	public static void main(String[] args)
    {
        new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
    }
}
