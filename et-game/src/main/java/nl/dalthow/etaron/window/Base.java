/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Base.java
 *
 **/

package nl.dalthow.etaron.window;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import nl.dalthow.etaron.base.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Base 
{
    // Declaration

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static JFrame frame;


    // Creates the window where everything gets projected on

    @Autowired
    public Base(@Value("${base.width}") int width, @Value("${base.height}") int height, @Value("${base.title}") String title, @Value("${base.version}") String version, Main main) 
    {
        main.setPreferredSize(new Dimension(width, height));

        frame = new JFrame(title);

        frame.add(main);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                logger.warn("Shutting down.");
            }
        });
        
        Image image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("global/icon.png"));
        
        frame.setIconImage(image);

        main.title = title;
        main.version = version;
        
        main.start();
    }
}
