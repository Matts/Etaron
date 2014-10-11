/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Projectile.java
 * 
 **/

package nl.dalthow.etaron.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import nl.dalthow.etaron.framework.Identifier;
import nl.dalthow.etaron.framework.WorldObject;

public class Projectile extends WorldObject
{
	// Declaration
	
	private float speed;
	
	private int timeInWorld;
	
	
	// Constructor
	
	public Projectile(float xPos, float yPos, float speed, Identifier id, boolean isSolid) 
	{
		super(xPos, yPos, id, isSolid);
		
		timeInWorld = 0;
		
		this.speed = speed;

	}

	
	// Gets triggered in the handler and then in the main method
	
	@Override
	public void tick(LinkedList<WorldObject> objectList) 
	{
		xPos += speed;
		
		if(timeInWorld >= 300)
		{
			objectList.remove(this);
		}
		
		else
		{
			timeInWorld++;
		}
	}

	@Override
	public void render(Graphics graphics) 
	{
		graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect((int)xPos, (int)yPos, 8, 8);
	}

	
	// Used for collision detection
	
	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int)xPos, (int)yPos, 8, 8);
	}
}