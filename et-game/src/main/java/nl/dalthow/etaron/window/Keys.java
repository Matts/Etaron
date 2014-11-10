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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

	private JPanel keysContent;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	private JButton[] buttons  = new JButton[7];
	private KeyMap map;
	private JButton selectedButton;
	
	@Autowired
	private Keys(@Value("${keys.width}") int width, @Value("${keys.height}") int height, @Value("${keys.title}") String title) 
	{
        keysContent = new JPanel();
        
		frame = new JFrame(title);
		map = new KeyMap();
		
       
        frame.addWindowListener(new WindowAdapter() 
        {
	        @Override
	        public void windowClosing(WindowEvent event) 
	        {
	    		XmlConverter converter = (XmlConverter) applicationContext.getBean("XMLConverter");
	     
	    		map.setJump(Integer.parseInt(buttons[0].getText()));
	    		map.setBackKey(Integer.parseInt(buttons[1].getText()));
	    		map.setMoveLeft(Integer.parseInt(buttons[2].getText()));
	    		map.setMoveRight(Integer.parseInt(buttons[3].getText()));
	    		map.setSwitchPlayer(Integer.parseInt(buttons[4].getText()));
	    		map.setPerformanceInfo(Integer.parseInt(buttons[5].getText()));
	    		map.setTradeItem(Integer.parseInt(buttons[6].getText()));
	     
	    		try 
	    		{
					converter.convertFromObjectToXML(map, Main.keyBindings);
					
					logger.info("Saving keybindings file.");
				} 
	    		
	    		catch(IOException error)
	    		{
					logger.error(error.getMessage());
				}
	    		
	    		logger.debug("Keys window closed.");
	        }
        });
        
        frame.pack();
        frame.add(keysContent);
        
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
	  
        Image image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("global/icon.png"));       
	   
	    frame.setIconImage(image);
    }
	
	
	public void show() 
    {
        frame.setVisible(true);
        frame.requestFocus();
    }
	
    @PostConstruct
    private void placeComponents() 
    { 
    	KeyAdapter keyPicker = new KeyAdapter() 
        {
            @Override public void keyPressed(KeyEvent event) 
            {
            	selectedButton.setText(Integer.toString(event.getKeyCode()));
            }
        };
        
        XmlConverter converter = (XmlConverter) applicationContext.getBean("XMLConverter");
	      
        try
        {
			map = (KeyMap)converter.convertFromXMLToObject(Main.keyBindings);
		} 
        
        catch(IOException error) 
        {
			logger.error(error.getMessage());
		}
        
        String[] names = {Integer.toString(map.getJump()), Integer.toString(map.getMoveLeft()), Integer.toString(map.getMoveRight()), Integer.toString(map.getBackKey()), Integer.toString(map.getSwitchPlayer()), Integer.toString(map.getTradeItem()), Integer.toString(map.getPerformanceInfo())};
        
        for(int i = 0; i < buttons.length; i++)
        {
        	buttons[i] = new JButton(names[i]);
        	buttons[i].addKeyListener(keyPicker);
        	buttons[i].addActionListener(new ActionListener() 
        	{
                @Override
                public void actionPerformed(ActionEvent event) 
                {
                    selectedButton = (JButton) event.getSource();
                }
            });
        	
        	keysContent.add(buttons[i]);
        }
    }
}