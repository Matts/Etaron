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
	
	private JButton[] buttons  = new JButton[6];
	
	private KeyMap map;
	
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
    	String names[] = {"Jump", "Back", "Left", "Right", "Switch", "Performance", "Trade"};
        
    	for(int i = 0; i < buttons.length; i++)
        {
             buttons[i] = new JButton(names[i]);
             buttons[i].addActionListener(new ActionListener()
             {
     			@Override
     			public void actionPerformed(ActionEvent event)
     			{
     				Object source = event.getSource();
     				
     				if(source.equals(buttons[0]))
     				{
     					System.out.println("0");
     				}
     				
     				if(source.equals(buttons[1]))
     				{
     					System.out.println("1");
     				}
     				
     				if(source.equals(buttons[2]))
     				{
     					System.out.println("2");
     				}
     				
     				if(source.equals(buttons[3]))
     				{
     					System.out.println("3");
     				}
     				
     				if(source.equals(buttons[4]))
     				{
     					System.out.println("4");
     				}
     				
     				if(source.equals(buttons[5]))
     				{
     					System.out.println("5");
     				}
     			}
             });
             
             keysContent.add(buttons[i]);
        }
    }
}