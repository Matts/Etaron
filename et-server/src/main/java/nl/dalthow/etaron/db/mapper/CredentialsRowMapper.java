/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class CredentialsRowMapper.java
 * 
 **/

package nl.dalthow.etaron.db.mapper;

import nl.dalthow.etaron.model.Settings;
import nl.dalthow.etaron.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialsRowMapper implements RowMapper<User> 
{
	// The row mapper used for the credentials table
	
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        String music = rs.getString("music");
        
        if(music != null) 
        {
            Settings settings = new Settings();
            settings.setMusicVolume(Integer.valueOf(music));
            settings.setSoundVolume(rs.getInt("sound"));
            user.setSettings(settings);
        }
        
        return user;
    }
}