/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class SongResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

public enum SongResource 
{
	// A list of all the music available in the game
	
    ANTS("music/ants.wav"),
    PYTHON("music/python.wav"),
    STRAW_FIELDS("music/straw fields.wav"),
    BACTERIAL_LOVE("music/bacterial love.wav");

    
    // Declaration
    
    private String path;

     
    // Sets up the path where the font is located
    
    SongResource(String path)
    {
        this.path = path;
    }

    
    // Getter
    
    public String getPath() 
    {
        return path;
    }
}
