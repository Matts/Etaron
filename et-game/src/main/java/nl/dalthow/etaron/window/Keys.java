/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Keys.java
 *
 **/

package nl.dalthow.etaron.window;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.KeyMap;
import nl.dalthow.etaron.framework.XmlConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Keys 
{
	// Declaration
	
	public static JFrame frame;
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	@Autowired
    private ApplicationContext applicationContext;

	@Autowired
	private Keys(@Value("${keys.width}") int width, @Value("${keys.height}") int height, @Value("${keys.title}") String title) 
	{
		frame = new JFrame(title);
		frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        
        frame.addWindowListener(new WindowAdapter() 
        {
	        @Override
	        public void windowClosing(WindowEvent event) 
	        {
	    		XmlConverter converter = (XmlConverter) applicationContext.getBean("XMLConverter");
	     
	    		KeyMap map = new KeyMap();
	    		
	    		map.setJump(87);
	    		map.setBackKey(27);
	    		map.setMoveLeft(65);
	    		map.setMoveRight(68);
	    		map.setSwitchPlayer(81);
	    		map.setPerformanceInfo(114);
	    		map.setTradeItem(69);
	     
	    		try 
	    		{
					converter.convertFromObjectToXML(map, Main.keyBindings);
				} 
	    		
	    		catch(IOException error)
	    		{
					logger.error(error.getMessage());
				}
	        }
        });
        
        KeyAdapter keyPicker = new KeyAdapter() 
        {
            @Override public void keyPressed(KeyEvent event) 
            {
            	int currentKey = event.getKeyCode();
            	
            	System.out.println(currentKey);
            }
        };

        frame.addKeyListener(keyPicker);
        
	    Image image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("global/icon.png"));       
	   
	    frame.setIconImage(image);
    }
}