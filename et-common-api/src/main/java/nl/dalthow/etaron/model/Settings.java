/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Settings.java
 * 
 **/

package nl.dalthow.etaron.model;

public class Settings
{
	// Declaration
	
    private int musicVolume = 7;
    private int soundVolume = 7;

    
    // Getters
    
    public int getMusicVolume() 
    {
        return musicVolume;
    }

    public int getSoundVolume() 
    {
        return soundVolume;
    }

    
    // Setters
    
    public void setSoundVolume(int soundVolume)
    {
        this.soundVolume = soundVolume;
    }
    
    public void setMusicVolume(int musicVolume) 
    {
        this.musicVolume = musicVolume;
    }
}
