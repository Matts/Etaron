/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Main.java
 *
 **/

package nl.dalthow.etaron.base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingUtilities;

import nl.dalthow.etaron.framework.Camera;
import nl.dalthow.etaron.framework.KeyMap;
import nl.dalthow.etaron.framework.State;
import nl.dalthow.etaron.framework.WorldObject;
import nl.dalthow.etaron.framework.XmlConverter;
import nl.dalthow.etaron.handler.ObjectHandler;
import nl.dalthow.etaron.handler.SoundHandler;
import nl.dalthow.etaron.loader.FontResource;
import nl.dalthow.etaron.loader.ImageResource;
import nl.dalthow.etaron.loader.ResourceLoader;
import nl.dalthow.etaron.loader.MusicResource;
import nl.dalthow.etaron.loader.SoundResource;
import nl.dalthow.etaron.model.User;
import nl.dalthow.etaron.window.Base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main extends Canvas implements Runnable 
{
    // Declaration
	
    private boolean isRunning = false;

    private Thread gameThread;

    @Autowired
    private ObjectHandler objectHandler;
    
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    private int temporaryFrames, absoluteFrames;
    private int temporaryTicks, absoluteTicks;
    private int tickToMenu;
  
    private static WorldObject cameraFocus;
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static LinkedList<BufferedImage> easyLevelPage = new LinkedList<>();
    public static LinkedList<BufferedImage> mediumLevelPage = new LinkedList<>();
    public static LinkedList<BufferedImage> hardLevelPage = new LinkedList<>();

    public static String[] allScores = new String[37];

    public String title;
    public String version;
    
    public static Camera cameraObject;

    public static User user;
    
    @Autowired
    public SoundHandler soundHandler;
    
    @Autowired
    private KeyListener keyHandler;
    
    @Autowired
    private MouseListener mouseHandler;

    public static Font defaultFont;
    public static State currentState;

    public static boolean displayInfo;

    public static int windowWidth;
    public static int windowHeight;

    public static long levelScore;
    
    public static int musicVolume = 0;
    public static int soundVolume = 8;
    
    public static int currentLevel;
    public static int currentPage;
    public static int loadTimeRemaining;

    public static float playerMovementSpeed;
    public static float playerJumpingHeight;
  
    public static KeyMap map;
    
    public static final String keyBindings = "keys.xml";
    
    
    // Constructor

    private Main()
    {
       
    }

    
	// Gets triggered before the initialisation and is only used for a few things
    
    private void preInitialisation() 
    {
    	XmlConverter converter = (XmlConverter) applicationContext.getBean("XMLConverter");
	     
		try 
		{
			map = (KeyMap)converter.convertFromXMLToObject(Main.keyBindings);
		} 
		
		catch(IOException error) 
		{
			logger.error(error.getMessage());
		}
    }
    
    
    // Gets triggered when the game starts
    
    public void initialisation() throws IOException, FontFormatException
    {
        currentState = State.SPLASH;

        cameraObject = new Camera(0, 0);

        tickToMenu = 60;
        loadTimeRemaining = 0;

        currentPage = 0;

        displayInfo = false;

        playerMovementSpeed = 5.0F;
        playerJumpingHeight = 15.630F;
    	
        windowWidth = getWidth();
        windowHeight = getHeight();

        loadFonts();
        loadLevelImages();
        loadMusic();
        
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
    }

    
    // Loads all the fonts
    
    private void loadFonts() throws FontFormatException, IOException
    {
        defaultFont = resourceLoader.get(FontResource.DEFAULT).deriveFont(Font.PLAIN, 13);
    }
    
    
    // Loads images for all the levels
    
    private void loadLevelImages() throws IOException 
    {
        easyLevelPage.add(resourceLoader.get(ImageResource.LEVEL_TUTORIAL));
        easyLevelPage.add(resourceLoader.get(ImageResource.LEVEL_THE_CLIMB));
        easyLevelPage.add(resourceLoader.get(ImageResource.LEVEL_CAVEMAN));
        easyLevelPage.add(resourceLoader.get(ImageResource.LEVEL_THE_FALL));
        easyLevelPage.add(resourceLoader.get(ImageResource.LEVEL_INVADERS));
        
        mediumLevelPage.add(resourceLoader.get(ImageResource.LEVEL_UNDER_FIRE));
       
        hardLevelPage.add(resourceLoader.get(ImageResource.LEVEL_GET_WREKT));

        logger.info("Loaded and sorted all levels.");
    }


    // Loads all the music files

    private void loadMusic() 
    {
        soundHandler.loadSong(MusicResource.ANTS);
        soundHandler.loadSound(SoundResource.PICKUP);

        soundHandler.changeVolume(musicVolume, soundHandler.musicClip);
        soundHandler.changeVolume(soundVolume, soundHandler.soundClip);

        logger.info("Loaded audio files.");
    }


    // Creates the thread if there is none

    public synchronized void start() 
    {
        if(isRunning == true) 
        {
            logger.error("Could not start a new thread, perhaps there is one running already?");

            return;
        } 
        
        else
        {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();

            logger.debug("Successfully started a new thread.");
        }
    }


    // Runs when the thread starts, also starts the game loop and creates a separate tick and frames per second line

    public void run()
    {
        try 
        {
            preInitialisation();
            initialisation();

            logger.info("Initialisation complete.");
        } 
        
        catch(FontFormatException | IOException error) 
        {
            logger.error(error.getMessage(), error);
        }

        requestFocus();

        long lastTime = System.nanoTime();

        double targetTicks = 60.0;
        double targetFrames = 120.0;
        
        double nanoTicks = 1000000000 / targetTicks;
        double nanoFrames = 1000000000 / targetFrames;
        
        double deltaTicksValue = 0;
        double deltaFramesValue = 0;

        long currentTime;
        long loopTimer = System.currentTimeMillis();

        while(isRunning) 
        {
            currentTime = System.nanoTime();
           
            deltaTicksValue += (currentTime - lastTime) / nanoTicks;
            deltaFramesValue += (currentTime - lastTime) / nanoFrames;
           
            lastTime = currentTime;

            while(deltaTicksValue >= 1) 
            {
                tick();

                deltaTicksValue--;
                temporaryTicks++;
            }

            while(deltaFramesValue >= 1) 
            {
                try 
                {
                	 render();

                     deltaFramesValue--;
                     temporaryFrames++;
                } 
                
                catch(IOException error) 
                {
                    logger.error(error.getMessage(), error);
                }
            }
            
            if(System.currentTimeMillis() - loopTimer > 1000) 
            {
                loopTimer += 1000;

                absoluteFrames = temporaryFrames;
                absoluteTicks = temporaryTicks;

                temporaryTicks = 0;
                temporaryFrames = 0;
            }
        }
    }


    // Gets triggered sixty times a second

    private void tick() 
    {
        if(currentState == State.SPLASH) 
        {
            if(tickToMenu <= 0) 
            {
                currentState = State.MENU;
            } 
            
            else 
            {
                tickToMenu--;
            }
        } 
        
        else if(currentState == State.GAME) 
        {	
        	if(loadTimeRemaining > 0)
        	{
        		loadTimeRemaining--;
        		
        		if(absoluteTicks == 60)
        		{
        			loadTimeRemaining = 0;
        		}
        	}
        	
            if(!soundHandler.musicClip.isRunning()) 
            {
                Random musicSelector = new Random();

                soundHandler.loadSong(MusicResource.values()[musicSelector.nextInt(MusicResource.values().length)]);

                soundHandler.musicClip.start();
            }

            objectHandler.tick();

            cameraObject.tick(Main.cameraFocus);
        }
    }


    // Gets triggered as much as possible

    private void render() throws IOException 
    {
        BufferStrategy bufferObject = getBufferStrategy();

        if(bufferObject == null)
        {
            createBufferStrategy(3);

            return;
        }

        Graphics graphics = bufferObject.getDrawGraphics();
        Graphics2D graphicsTwoDimensional = (Graphics2D) graphics;

        if(currentState == State.SPLASH) 
        {
            graphics.drawImage(resourceLoader.get(ImageResource.LOGO), 0, 0, getWidth(), getHeight(), this);
        }

        else if(currentState == State.MENU) 
        {
            graphics.setColor(new Color(20, 20, 20));
            graphics.fillRect(0, 0, windowWidth, windowHeight);

            graphics.setColor(new Color(255, 126, 0));
            graphics.fillRect(0, 0, 25, windowHeight);

            graphics.setColor(new Color(185, 0, 255));
            graphics.fillRect(25, 0, 10, windowHeight);

            setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 96);
            graphics.drawString(title, windowWidth / 2 - 220, 120);

            setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 14);
            graphics.drawString("VERSION" + " " + version, 40, 726);

            if(user != null)
            {
            	String welcomeMessage = "Welcome" + " " + user.getUserName() + "!";
                graphics.drawString(welcomeMessage, windowWidth - (graphics.getFontMetrics().stringWidth(welcomeMessage) + 4), 726);
            }
            
            else 
            {
            	String loginMessage = "Click here to log in.";
                graphics.drawString(loginMessage, windowWidth - (graphics.getFontMetrics().stringWidth(loginMessage) + 4), 726);
            }

            switch(currentPage) 
            {
                case 0: setFontAttributes(graphics, defaultFont, new Color(0, 255, 0), 32);

						graphics.drawString("Easy", windowWidth / 2 - 52, 165);

						for(int i = 0; i < easyLevelPage.size(); i++) 
						{
							int yModifier = 200;
							int xModifier = 200 + (150 * i);

							if(i > 3) 
							{
								yModifier = 365;
								xModifier = 200 + (150 * (i - 4));
							}

							if(i > 7) 
							{
								yModifier = 525;
								xModifier = 200 + (150 * (i - 8));
							}

							displayScore(graphics, i, xModifier, yModifier);

							graphics.drawImage(easyLevelPage.get(i), xModifier, yModifier, 128, 128, this);
						}
				break;
                    
                case 1: setFontAttributes(graphics, defaultFont, new Color(255, 255, 0), 32);

						graphics.drawString("Medium", windowWidth / 2 - 80, 165);

						for(int i = 0; i < mediumLevelPage.size(); i++) 
						{
							int yModifier = 200;
							int xModifier = 200 + (150 * i);

							if(i > 3) 
							{
								yModifier = 365;
								xModifier = 200 + (150 * (i - 4));
							}

							if(i > 7)
							{
								yModifier = 525;
								xModifier = 200 + (150 * (i - 8));
							}

							displayScore(graphics, i + 12, xModifier, yModifier);

							graphics.drawImage(mediumLevelPage.get(i), xModifier, yModifier, 128, 128, this);
						}
                break;
                   
                case 2: setFontAttributes(graphics, defaultFont, new Color(255, 0, 0), 32);

						graphics.drawString("Hard", windowWidth / 2 - 52, 165);

						for(int i = 0; i < hardLevelPage.size(); i++) 
						{
							int yModifier = 200;
							int xModifier = 200 + (150 * i);

							if(i > 3) 
							{
								yModifier = 365;
								xModifier = 200 + (150 * (i - 4));
							}

							if(i > 7) 
							{
								yModifier = 525;
								xModifier = 200 + (150 * (i - 8));
							}

							displayScore(graphics, i + 24, xModifier, yModifier);

							graphics.drawImage(hardLevelPage.get(i), xModifier, yModifier, 128, 128, this);
						}
				break;
            }
                       
            graphics.drawImage(resourceLoader.get(ImageResource.BUTTON_NEXT), windowWidth - 115, windowHeight / 2 - 40, 40, 80, this);
            graphics.drawImage(resourceLoader.get(ImageResource.BUTTON_BACK), 75, windowHeight / 2 - 40, 40, 80, this);

            Point point = new Point(MouseInfo.getPointerInfo().getLocation());
            SwingUtilities.convertPointFromScreen(point, Base.frame.getComponent(0));

            if(point.getX() > 75 && point.getX() < 115 && point.getY() > 325 && point.getY() < 405) 
			{
                graphics.fillRect(75, 413, 40, 6);
            } 
			
			else if(point.getX() > 855 && point.getX() < 895 && point.getY() > 325 && point.getY() < 405) 
			{
                graphics.fillRect(windowWidth - 115, 413, 40, 6);
            }
        }

        else if(currentState == State.GAME) 
		{
        	graphics.setColor(new Color(0, 0, 0));
            graphics.fillRect(0, 0, windowWidth, windowHeight);

            graphicsTwoDimensional.translate(cameraObject.getPosX(), cameraObject.getPosY());

            objectHandler.render(graphics);

            graphicsTwoDimensional.translate(-cameraObject.getPosX(), -cameraObject.getPosY());

            if(displayInfo) 
			{
            	graphics.setColor(new Color(0, 0, 0));
                graphics.fillRect(0, 0, 156, 115);
            	
                setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 13);

                graphics.drawString("VERSION:" + " " + version, 15, 25);
                graphics.drawString("FPS:" + " " + absoluteFrames, 15, 40);
                graphics.drawString("TICKS:" + " " + absoluteTicks, 15, 55);

                graphics.drawString("X:" + " " + (int)cameraObject.getPosX(), 15, 85);
                graphics.drawString("Y:" + " " + (int)cameraObject.getPosY(), 15, 100);
            }
            
           
            graphics.setColor(new Color(0, 0, 0));
            graphics.fillRect(0, windowHeight - 50, windowWidth, 50);
            
            setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 36);
            graphics.drawString("SCORE: " + levelScore, 12, windowHeight - 10);
            
        	if(loadTimeRemaining > 0)
        	{
        		 graphics.setColor(new Color(20, 20, 20));
                 graphics.fillRect(0, 0, windowWidth, windowHeight);
                 
                 setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 32);
                 graphics.drawString("Loading...", windowWidth / 2 - 94, windowHeight / 2);
                 
                 graphics.setColor(Color.white);
                 graphics.fillRect(windowHeight / 2, windowHeight / 2 + 10, 260, 20);
                 
                 graphics.setColor(new Color(20, 20, 20));
                 graphics.fillRect(windowHeight / 2 + 5, windowHeight / 2 + 15, 250, 10);
                 
                 graphics.setColor(new Color(255, 255, 255));
                 graphics.fillRect(windowHeight / 2 + 5, windowHeight / 2 + 15, 250 - loadTimeRemaining, 10);
        	}
        }
        
        graphics.dispose();
        bufferObject.show();
    }

    
    // Draws a string with new lines on the canvas
    
    private void drawString(Graphics graphics, String text, int xPos, int yPos) 
    {
        for(String line : text.split("\n"))
        {
        	graphics.drawString(line, xPos, yPos += graphics.getFontMetrics().getHeight());
        }
    }

    
    // Displays the score for each level

	private void displayScore(Graphics graphics, int i, int xModifier, int yModifier) 
	{
        setFontAttributes(graphics, defaultFont, new Color(255, 255, 255), 10);

        if(allScores[i + 1] != null) 
		{
            graphics.drawString("High score: " + " " + Main.allScores[i + 1], xModifier + 11, yModifier + 141);
        } 
		
		else if(user != null) 
		{
            graphics.drawString("No score found.", xModifier + 13, yModifier + 141);
        } 
		
		else
		{
            graphics.drawString("No connection.", xModifier + 16, yModifier + 141);
        }
    }


    // Sets the font style the game uses

    private void setFontAttributes(Graphics graphics, Font font, Color color, int size) 
	{
        graphics.setColor(color);
        graphics.setFont(font.deriveFont(Font.PLAIN, size));
    }
    
    
    // Getters

	public static WorldObject getCameraFocus() 
	{
		return cameraFocus;
	}


	// Setters
	
	public static void setCameraFocus(WorldObject cameraFocus) 
	{
		Main.cameraFocus = cameraFocus;
	}
}