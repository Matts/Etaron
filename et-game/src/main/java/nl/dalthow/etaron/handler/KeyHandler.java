/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class KeyHandler.java
 *
 **/

package nl.dalthow.etaron.handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.State;
import nl.dalthow.etaron.framework.WorldObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyHandler extends KeyAdapter 
{
    // Declaration

    @Autowired
    private ObjectHandler objectHandler;

    @Autowired
    private SoundHandler soundHandler;

    public static WorldObject cameraShouldFocus;


    // Constructor
    
    private KeyHandler() 
    {
        
    }


    // Gets triggered when a key on the keyboard is pressed

    public void keyPressed(KeyEvent event) 
    {
        int currentKey = event.getKeyCode();

        for(int i = 0; i < objectHandler.objects.size(); i++) 
        {
            WorldObject temporaryObject = objectHandler.objects.get(i);

            if(temporaryObject.equals(cameraShouldFocus)) 
            {
                if(currentKey == KeyEvent.VK_W && !temporaryObject.isJumping()) 
                {
                    temporaryObject.setVelY(-Main.playerJumpingHeight);
                    temporaryObject.setJumping(true);
                }

                if(currentKey == KeyEvent.VK_D) 
                {
                    temporaryObject.setVelX(Main.playerMovementSpeed);
                    temporaryObject.setFacing(1);
                }

                if(currentKey == KeyEvent.VK_A) 
                {
                    temporaryObject.setVelX(-Main.playerMovementSpeed);
                    temporaryObject.setFacing(0);
                }
                
                if(currentKey == KeyEvent.VK_F3) 
                {
                    Main.displayInfo = true;
                }
            }
        }

        if(currentKey == KeyEvent.VK_ESCAPE) 
        {
            Main.currentState = State.MENU;

            soundHandler.musicClip.stop();
        }
    }


    // Gets triggered when a key on the keyboard is released

    public void keyReleased(KeyEvent event) 
    {
        int currentKey = event.getKeyCode();

        for(int i = 0; i < objectHandler.objects.size(); i++) 
        {
            WorldObject temporaryObject = objectHandler.objects.get(i);

            if(temporaryObject.equals(cameraShouldFocus))
            {
                if(currentKey == KeyEvent.VK_D)
                {
                    temporaryObject.setVelX(0);
                }

                if(currentKey == KeyEvent.VK_A)
                {
                    temporaryObject.setVelX(0);
                }

                if(currentKey == KeyEvent.VK_F3) 
                {
                    Main.displayInfo = false;
                }
            }
        }
        
        if(Main.currentState == State.GAME)
        {
        	if(currentKey == KeyEvent.VK_Q) 
            {
                LinkedList<Integer> allPlayers = new LinkedList<Integer>();
                
	            for(int i = 0; i < objectHandler.objects.size(); i++) 
	            {
	                WorldObject temporaryObject = objectHandler.objects.get(i);
	            	
	                if(temporaryObject.getId() == Identifier.PLAYER) 
	                {
	                	allPlayers.add(i);
	                }
	            }
	            
	            if(allPlayers.size() > 1)
	            {
		            if(Main.getCameraFocus().equals(objectHandler.objects.get(allPlayers.getFirst())))
	        		{
		            	cameraShouldFocus = objectHandler.objects.get(allPlayers.getLast());
	        		}
		            
		            else
		            {
		            	cameraShouldFocus = objectHandler.objects.get(allPlayers.getFirst());
		            }
		            
		            Main.setCameraFocus(cameraShouldFocus);
	            }
            }
        }
    }
}