/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class KeyMap.java
 * 
 **/

package nl.dalthow.etaron.framework;

public class KeyMap 
{
	private int jump;
	private int moveRight;
	private int moveLeft;
	private int tradeItem;
	private int switchPlayer;
	private int backKey;
	private int performanceInfo;
	
	public int getJump()
	{
		return jump;
	}
	
	public void setJump(int jump) 
	{
		this.jump = jump;
	}
	
	public int getMoveRight() 
	{
		return moveRight;
	}
	
	public void setMoveRight(int moveRight) 
	{
		this.moveRight = moveRight;
	}
	
	public int getMoveLeft() 
	{
		return moveLeft;
	}
	
	public void setMoveLeft(int moveLeft)
	{
		this.moveLeft = moveLeft;
	}
	
	public int getTradeItem() 
	{
		return tradeItem;
	}
	
	public void setTradeItem(int tradeItem) 
	{
		this.tradeItem = tradeItem;
	}
	
	public int getSwitchPlayer() 
	{
		return switchPlayer;
	}
	
	public void setSwitchPlayer(int switchPlayer) 
	{
		this.switchPlayer = switchPlayer;
	}
	
	public int getBackKey() 
	{
		return backKey;
	}
	
	public void setBackKey(int backKey) 
	{
		this.backKey = backKey;
	}
	
	public int getPerformanceInfo()
	{
		return performanceInfo;
	}
	
	public void setPerformanceInfo(int performanceInfo) 
	{
		this.performanceInfo = performanceInfo;
	}
}