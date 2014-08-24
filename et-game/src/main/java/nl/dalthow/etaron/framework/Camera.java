/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Camera.java
 * 
 **/

package nl.dalthow.etaron.framework;

import nl.dalthow.etaron.base.Main;

public class Camera 
{
	// Declaration
	
	private float xPos, yPos;
	
	
	// Constructor
	
	public Camera(float xPos, float yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
	// Ticks when the world object is a player
	
	public void tick(WorldObject object) 
	{
		xPos =- object.getPosX() + Main.windowWidth / 2 - 16;
		yPos =- object.getPosY() + Main.windowHeight / 2 - 16 - 25;
		
		if(object.id == Identifier.PLAYER)
		{
			yPos = - object.getPosY() + Main.windowHeight / 2 - 32 - 25;
		}
	}

	
	// Getters
	
	public float getPosX() 
	{
		return xPos;
	}

	public float getPosY()
	{
		return yPos;
	}

	
	// Setters
	
	public void setPosX(float xPos) 
	{
		this.xPos = xPos;
	}

	public void setPosY(float yPos)
	{
		this.yPos = yPos;
	}
}
