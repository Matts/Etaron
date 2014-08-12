/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Score.java
 * 
 **/

package nl.dalthow.etaron.model;

public class Score 
{
	// Declaration
	
    private long value;
    private int level;

    
    // Getters
    
    public long getValue() 
    {
        return value;
    }

    public int getLevel() 
    {
        return level;
    }

    
    // Setters
    
    public void setValue(long value) 
    {
        this.value = value;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }
}
