/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class WorldObject.java
 * 
 **/

package nl.dalthow.etaron.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class WorldObject 
{
	// Declaration
	
	protected float xPos, yPos;
	protected float xVel, yVel;
	
	protected int facing;
	
	protected boolean isJumping;
	protected boolean isFalling;
	
	protected Identifier id;
	
	protected boolean isSolid;
	
	
	// Constructor
	
	public WorldObject(float xPos, float yPos, Identifier id, boolean isSolid)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.isJumping = false;
		this.isFalling = false;
		
		this.facing = 0;
		
		this.id = id;
		
		this.isSolid = isSolid;
	}
	
	
	// Used to make the object tick and render
	
	public abstract void tick(LinkedList<WorldObject> objectList);
	public abstract void render(Graphics graphicsObject);
	
	
	// Used for collision
	
	public abstract Rectangle getBounds();
	
	
	// Getters
	
	public float getPosX()
	{
		return xPos;
	}
	
	public float getPosY()
	{
		return yPos;
	}
	
	public float getVelX()
	{
		return xVel;
	}
	
	public float getVelY()
	{
		return yVel;
	}
	
	public boolean isJumping() 
	{
		return isJumping;
	}
	
	public boolean isFalling() 
	{
		return isFalling;
	}
	
	public Identifier getId()
	{
		return id;
	}
	
	public int getFacing()
	{
		return facing;
	}
	
	public boolean getSolid()
	{
		return isSolid;
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
	
	public void setVelX(float xVel)
	{
		this.xVel = xVel;
	}
	
	public void setVelY(float yVel)
	{
		this.yVel = yVel;
	}
	
	public void setJumping(boolean isJumping) 
	{
		this.isJumping = isJumping;
	}
	
	public void setFalling(boolean isFalling) 
	{
		this.isFalling = isFalling;
	}
	
	public void setFacing(int facing)
	{
		this.facing = facing;
	}
	
	public void setSolid(boolean isSolid)
	{
		this.isSolid = isSolid;
	}
}