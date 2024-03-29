/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Item.java
 * 
 **/

package nl.dalthow.etaron.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.WorldObject;

public class Item extends WorldObject
{
	// Declaration
	
	private int type;
	private int tickToAnimationChange;
	
	private boolean hasTurned;

	
	// Constructor
	
	public Item(float xPos, float yPos, int type, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		this.type = type;
		this.hasTurned = false;
	}

	
	// Gets triggered in the handler and then in the main method
	
	@Override
	public void tick(LinkedList<WorldObject> objectList) 
	{
		if(id == Identifier.COIN)
		{
			if(tickToAnimationChange == 0)
			{
				if(type < 4 && hasTurned == false)
				{
					type++;
				}
				
				else if(type > 0  && hasTurned == true)
				{
					type--;
					
					if(type == 0)
					{
						hasTurned = false;
					}
				}
				
				else
				{
					hasTurned = true;
				}
				
				tickToAnimationChange = 3;
			}
			
			else
			{
				tickToAnimationChange--;
			}
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		if(id == Identifier.COIN)
		{
			graphics.setColor(new Color(255, 255, 0));
			
			if(type == 0)
			{
				graphics.fillOval((int)xPos, (int)yPos, 32, 32);
			}
			
			else if(type == 1)
			{
				graphics.fillOval((int)xPos + 4, (int)yPos, 24, 32);
			}
			
			else if(type == 2)
			{
				graphics.fillOval((int)xPos + 8, (int)yPos, 16, 32);
			}
			
			else if(type == 3)
			{
				graphics.fillOval((int)xPos + 12, (int)yPos, 8, 32);
			}
			
			else if(type == 4)
			{
				graphics.fillOval((int)xPos + 16, (int)yPos, 0, 32);
			}
		}
		
		if(id == Identifier.KEY)
		{				
			graphics.setColor(new Color(175, 175, 175));
			graphics.fillOval((int)xPos + 10, (int)yPos + 1, 12, 12);
			
			graphics.setColor(new Color(175, 175, 175));
			
			graphics.fillRect((int)xPos + 15, (int)yPos + 12, 4, 19);
			graphics.fillRect((int)xPos + 10, (int)yPos + 16, 7, 3);
			graphics.fillRect((int)xPos + 8, (int)yPos + 21, 7, 3);
			graphics.fillRect((int)xPos + 11, (int)yPos + 26, 4, 3);
		}
	}
	
	
	// Used for collision detection
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}