/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class SoundResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

public enum SoundResource
{
	// A list of all the sounds available in the game
	
    COIN("sound/coin.wav"),
    BULLET("sound/bullet.wav"),
    FAILURE("sound/failure.wav"),
    VICTORY("sound/victory.wav"), 
    BOING("sound/boing.wav");

    
    // Declaration
    
    private String path;

    
    // Sets up the path where the font is located
    
    SoundResource(String path) 
    {
        this.path = path;
    }

    
    // Getter 
    
    public String getPath() 
    {
        return path;
    }
}
