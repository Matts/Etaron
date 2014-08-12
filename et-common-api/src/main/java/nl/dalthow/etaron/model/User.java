/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class User.java
 * 
 **/

package nl.dalthow.etaron.model;

import java.util.ArrayList;
import java.util.List;

public class User 
{
	// Declaration
	
    private int id;
    private int level;
    private int score;
    
    private String userName;
    private String email;
    
    private transient String password;
    
    private List<Score> scores = new ArrayList<>();
    private Settings settings = new Settings();

    
    // Getters
    
    public int getId() 
    {
        return id;
    }
    
    public int getLevel()
    {
    	return level;
    }

    public String getUserName()
    {
        return userName;
    }

    public List<Score> getScores() 
    {
        return scores;
    }

    public Settings getSettings() 
    {
        return settings;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
    
    public void setLevel(int level)
    {
    	this.level = level;
    }

    public String getPassword()
    {
        return password;
    }
    
    public String getEmail() 
    {
        return email;
    }

    public int getScore() 
    {
		return score;
	}
    
    
    // Setters
    
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public void setScores(List<Score> scores) 
    {
        this.scores = scores;
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

	public void setScore(int score)
	{
		this.score = score;
	}
}
