/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class SongResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

public enum MusicResource 
{
	// A list of all the music available in the game
	
    ANTS("music/ants.wav"),
    PYTHON("music/python.wav"),
    STRAW_FIELDS("music/straw fields.wav"),
    BACTERIAL_LOVE("music/bacterial love.wav"),
    SAVAGE_STEEL_FUN_CLUB("music/savage steel fun club.wav"),
    A_NINJA_AMONG_CULTURACHIPPERS("music/a ninja among culturachippers.wav"),
    ANOTHER_BEEK_BEEP_BEER_PLEASE("music/another beek beep beer please.wav");
    

    
    // Declaration
    
    private String path;

     
    // Sets up the path where the font is located
    
    MusicResource(String path)
    {
        this.path = path;
    }

    
    // Getter
    
    public String getPath() 
    {
        return path;
    }
}
