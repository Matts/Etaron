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

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.State;
import nl.dalthow.etaron.framework.WorldObject;
import nl.dalthow.etaron.object.Item;
import nl.dalthow.etaron.object.Player;

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

		if(Main.currentState == State.GAME && Main.loadTimeRemaining == 0)
		{
			for(int i = 0; i < objectHandler.objects.size(); i++)
			{
				WorldObject temporaryObject = objectHandler.objects.get(i);

				if(temporaryObject.equals(cameraShouldFocus))
				{
					if(currentKey == Main.map.getJump() && !temporaryObject.isJumping())
					{
						temporaryObject.setVelY(-Main.playerJumpingHeight);
						temporaryObject.setJumping(true);
					}

					if(currentKey == Main.map.getMoveRight())
					{
						temporaryObject.setVelX(Main.playerMovementSpeed);
						temporaryObject.setFacing(1);
					}

					if(currentKey == Main.map.getMoveLeft())
					{
						temporaryObject.setVelX(-Main.playerMovementSpeed);
						temporaryObject.setFacing(0);
					}

					if(currentKey == Main.map.getPerformanceInfo())
					{
						Main.displayInfo = true;
					}
				}
			}
		}

		if(currentKey == Main.map.getBackKey())
		{
			Main.currentState = State.MENU;

			soundHandler.musicClip.stop();
		}
	}

	// Gets triggered when a key on the keyboard is released

	int i = 0;

	public void keyReleased(KeyEvent event)
	{
		int currentKey = event.getKeyCode();

		if(Main.currentState == State.MENU)
		{
			if(currentKey == Main.map.getMoveLeft())
			{
				if(Main.currentPage > 0)
				{
					Main.currentPage--;
				}
			}

			if(currentKey == Main.map.getMoveRight())
			{
				if(Main.currentPage < 2)
				{
					Main.currentPage++;
				}
			}
		}

		if(Main.currentState == State.GAME)
		{
			for(int i = 0; i < objectHandler.objects.size(); i++)
			{
				WorldObject temporaryObject = objectHandler.objects.get(i);

				if(temporaryObject.equals(cameraShouldFocus))
				{
					if(currentKey == Main.map.getMoveRight())
					{
						temporaryObject.setVelX(0);
					}

					if(currentKey == Main.map.getMoveLeft())
					{
						temporaryObject.setVelX(0);
					}

					if(currentKey == Main.map.getPerformanceInfo())
					{
						Main.displayInfo = false;
					}
				}
			}

			if(currentKey == Main.map.getTradeItem())
			{
				Player oldKeyHolder = (Player) Main.getCameraFocus();
				Player newKeyHolder;
				
				for(int j = 0; j < objectHandler.objects.size(); j++)
				{
					WorldObject temporaryObject = objectHandler.objects.get(j);
					
					if(temporaryObject.getId() == Identifier.PLAYER)
					{
						if(oldKeyHolder.getUpdateBounds().intersects(temporaryObject.getBounds()))
						{
							newKeyHolder = (Player)temporaryObject;
							
							if(!oldKeyHolder.equals(newKeyHolder))
							{
								if(oldKeyHolder.hasKey)
								{
									newKeyHolder.hasKey = true;
									oldKeyHolder.hasKey = false;
								}
							}
						}
					}
				}
			}
			
			if(currentKey == KeyEvent.VK_R)
			{
				Player player = (Player) Main.getCameraFocus();
				
				if(player.hasKey)
				{
					player.hasKey = false;
					player.pickUpDelay = 120;
					
					objectHandler.addObject(new Item((int)player.getPosX(), (int)player.getPosY() + 32, 0, Identifier.KEY, false));
				}
			}

			if(currentKey == Main.map.getSwitchPlayer())
			{
				if(objectHandler.players.size() > 1)
				{
					cameraShouldFocus.setVelX(0);

					if(i < objectHandler.players.size() - 1)
					{
						i++;
					}

					else
					{
						i = 0;
					}

					cameraShouldFocus = objectHandler.players.get(i);
					
					Main.setCameraFocus(cameraShouldFocus);
				}
			}
		}
	}
}