/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class ImageResource.java
 * 
 **/

package nl.dalthow.etaron.loader;

public enum ImageResource 
{
	// A list of all the textures available in the game
	
    LOGO("/menu/logo.png"),
    
    BUTTON_NEXT("/menu/next.png"),
    BUTTON_BACK("/menu/back.png"),
    
    LEVEL_TUTORIAL("/level/easy/tutorial.png"),
    LEVEL_THE_CLIMB("/level/easy/the climb.png"),
    LEVEL_CAVEMAN("/level/easy/caveman.png"),
    LEVEL_THE_FALL("/level/easy/the fall.png"),
   
    LEVEL_UNDER_FIRE("/level/medium/under fire.png"),
    
    LEVEL_GET_WREKT("/level/hard/get wrekt.png"); 
    
    
    // Declaration
    
    private String path;

    
    // Sets up the path where the font is located
    
    ImageResource(String path) 
    {
        this.path = path;
    }

    
    // Getter
    
    public String getPath() 
    {
        return path;
    }
}
