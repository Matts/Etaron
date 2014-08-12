/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class FontResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

public enum FontResource 
{
	// A list of all the fonts available in the game
	
    DEFAULT("font/default.ttf");
    
    
    // Declaration
    
    private String path;
    
    
    // Sets up the path where the font is located
    
    FontResource(String path) 
    {
        this.path = path;
    }

    
    // Getter
    
    public String getPath() 
    {
        return path;
    }
}
