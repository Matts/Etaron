/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Block.java
 * 
 **/

package nl.dalthow.etaron.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.WorldObject;

@Component
@Scope("prototype")
public class Block extends WorldObject
{
	// Declaration
	
	private int type;
	private int direction;
	private int amount;
	
	private int timePassed;
	
	
	// Constructor
	
	public Block(float xPos, float yPos, int type, int direction, int amount, Identifier id) 
	{
		super(xPos, yPos, id);
		
		this.type = type;
		this.direction = direction;
		this.amount = amount;
		
		this.timePassed = 0;
	}

	
	// Gets triggered in the handler and then in the main method
	
	@Override
	public void tick(LinkedList<WorldObject> objectList) 
	{
		if(type == 3 || type == 4)
		{
			xPos += xVel;
			yPos += yVel;
			
			switch(direction)
			{
				case 0: if(timePassed < (amount * 32))
						{
							yVel = 1;
						}
						
						if(timePassed > (amount * 32))
						{
							yVel = -1;
						}
					
						timePassed++;
						
						if(timePassed > (amount * 32) * 2)
						{
							timePassed = 0;
						}
				break;
			
				case 1: if(timePassed < (amount * 32))
						{
							yVel = -1;
						}
				
						if(timePassed > (amount * 32))
						{
							yVel = 1;
						}
					
						timePassed++;
						
						if(timePassed > (amount * 32) * 2)
						{
							timePassed = 0;
						}
				break;
			
				case 2: if(timePassed < (amount * 32))
						{
							xVel = 1;
						}
						
						if(timePassed > (amount * 32))
						{
							xVel = -1;
						}
					
						timePassed++;
						
						if(timePassed > (amount * 32) * 2)
						{
							timePassed = 0;
						}
				break;
	
				case 3: if(timePassed < (amount * 32))
						{
							xVel = -1;
						}
						
						if(timePassed > (amount * 32))
						{
							xVel = 1;
						}
					
						timePassed++;
						
						if(timePassed > (amount * 32) * 2)
						{
							timePassed = 0;
						}
				break;
			}
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		if(type == 0)
		{
			graphics.setColor(new Color(255, 255, 255));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 1)
		{
			graphics.setColor(new Color(255, 0, 0));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 2)
		{
			graphics.setColor(new Color(0, 255, 0));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 3)
		{
			graphics.setColor(new Color(0, 255, 255));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 4)
		{
			graphics.setColor(new Color(255, 0, 255));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 5)
		{
			graphics.setColor(new Color(100, 100, 100));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
		
		else if(type == 6)
		{
			graphics.setColor(new Color(255, 175, 175));
			graphics.fillRect((int)xPos, (int)yPos, 32, 32);
		}
	}
	
	
	// Used for collision detection
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}
