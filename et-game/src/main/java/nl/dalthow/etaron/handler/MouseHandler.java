/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class MouseHandler.java
 *
 **/

package nl.dalthow.etaron.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.State;
import nl.dalthow.etaron.window.KeyBindings;
import nl.dalthow.etaron.window.Login;
import nl.dalthow.etaron.window.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MouseHandler implements MouseListener 
{
    // Declaration

    @Autowired
    private ObjectHandler handler;

    @Autowired
    private SoundHandler soundHandler;

    @Autowired
    private Settings settings;

    @Autowired
    private Login login;
    
    @Autowired
    private ApplicationContext applicationContext;


    // Constructor

    private MouseHandler() 
    {
        
    }


    // Gets triggered whenever you click anywhere with your mouse

    @Override
    public void mouseClicked(MouseEvent event) 
    {
        if(Main.currentState == State.MENU) 
        {
            switch(Main.currentPage) 
            {
                case 0: for(int i = 0; i < Main.easyLevelPage.size(); i++) 
                		{
		                    int yModifier = 200;
		                    int xModifier = 200 + (150 * i);
		
		                    if(i > 3) 
		                    {
		                        yModifier = 365;
		                        xModifier = 200 + (150 * (i - 4));
		
		                    }
		
		                    if(i > 7) 
		                    {
		                        yModifier = 525;
		                        xModifier = 200 + (150 * (i - 8));
		                    }
		
		                    if(event.getX() > xModifier && event.getX() < xModifier + 128 && event.getY() > yModifier && event.getY() < yModifier + 128) 
		                    {
		                        Main.currentLevel = (i + 1);
		                        soundHandler.musicClip.stop();
		
		                        handler.loadLevel(Main.easyLevelPage.get(i));
		                    }
		                }

                break;
                
                case 1: for(int i = 0; i < Main.mediumLevelPage.size(); i++) 
                		{
		                    int yModifier = 200;
		                    int xModifier = 200 + (150 * i);
		
		                    if(i > 3) 
		                    {
		                        yModifier = 365;
		                        xModifier = 200 + (150 * (i - 4));
		
		                    }
		
		                    if(i > 7) 
		                    {
		                        yModifier = 525;
		                        xModifier = 200 + (150 * (i - 8));
		                    }
		
		                    if(event.getX() > xModifier && event.getX() < xModifier + 128 && event.getY() > yModifier && event.getY() < yModifier + 128) 
		                    {
		                        Main.currentLevel = (i + 13);
		                        soundHandler.musicClip.stop();
		
		                        handler.loadLevel(Main.mediumLevelPage.get(i));
		                    }
                		}
                    
                break;

                case 2: for(int i = 0; i < Main.hardLevelPage.size(); i++) 
                		{
                    		int yModifier = 200;
                    		int xModifier = 200 + (150 * i);

		                    if(i > 3) 
		                    {
		                        yModifier = 365;
		                        xModifier = 200 + (150 * (i - 4));
		
		                    }
		
		                    if(i > 7) 
		                    {
		                        yModifier = 525;
		                        xModifier = 200 + (150 * (i - 8));
		                    }
		
		                    if(event.getX() > xModifier && event.getX() < xModifier + 128 && event.getY() > yModifier && event.getY() < yModifier + 128) 
		                    {
		                        Main.currentLevel = (i + 25);
		                        soundHandler.musicClip.stop();
		
		                        handler.loadLevel(Main.hardLevelPage.get(i));
		                    }
		                }
                break;
            }
            
			if(event.getX() > 75 && event.getX() < 115 && event.getY() > 325 && event.getY() < 405) 
			{
				if(Main.currentPage > 0) 
				{
					Main.currentPage--;
				}
			}

			if(event.getX() > 855 && event.getX() < 895 && event.getY() > 325 && event.getY() < 405) 
			{
				if(Main.currentPage < 2) 
				{
					Main.currentPage++;
				}
			}

			if(event.getX() > 766 && event.getX() < 965 && event.getY() > 715 && event.getY() < 726 && Main.user == null) 
			{
				login.show();
			}
        }

		if(event.getX() > 40 && event.getX() < 177 && event.getY() > 715 && event.getY() < 726) 
		{
			settings.show();
		}
    }
    
    @Override
    public void mouseEntered(MouseEvent event) 
    {

    }

    @Override
    public void mouseExited(MouseEvent event) 
    {

    }

    @Override
    public void mousePressed(MouseEvent event) 
    {

    }

    @Override
    public void mouseReleased(MouseEvent event) 
    {

    }
}
