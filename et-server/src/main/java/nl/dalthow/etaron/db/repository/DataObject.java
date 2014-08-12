/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class DataObject.java
 * 
 **/

package nl.dalthow.etaron.db.repository;

import nl.dalthow.etaron.db.Query;
import nl.dalthow.etaron.model.User;
import nl.dalthow.etaron.db.mapper.ScoreRowMapper;
import nl.dalthow.etaron.db.mapper.CredentialsRowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataObject 
{
	// Declaration
	
    private static final Logger log = LoggerFactory.getLogger(DataObject.class);
    
    private static final CredentialsRowMapper ROW_MAPPER = new CredentialsRowMapper();
    private static final ScoreRowMapper SCORE_ROW_MAPPER = new ScoreRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    
    // Gets a user out of the database if the username and password match a record
    
    public User findUser(String username, String password) 
    {
        try 
        {
            log.debug("Searching for user [{}]", username);
            Map<String, String> userSearchParams = new HashMap<>(2);
            userSearchParams.put("username", username);
            userSearchParams.put("password", password);
            User user = jdbcTemplate.queryForObject(Query.USER_SEARCH, userSearchParams, ROW_MAPPER);
            
            log.debug("Searching for scores of user [{}]", username);
            Map<String, Integer> scoreSearchParams = new HashMap<>(1);
            scoreSearchParams.put("userId", user.getId());
            user.setScores(jdbcTemplate.query(Query.SCORE_SEARCH, scoreSearchParams, SCORE_ROW_MAPPER));
            
            return user;
        } 
        
        catch(DataAccessException error) 
        {
            log.error(error.getMessage(), error);
            
            return null;
        }
    }

    
    // Updates the settings for a specific user
    
    public void updateSettings(User user) 
    {
        log.debug("Updating settings for user [{}]", user.getUserName());
        Map<String, Integer> updateSettingsParams = new HashMap<>(3);
        updateSettingsParams.put("userId", user.getId());
        updateSettingsParams.put("sound", user.getSettings().getSoundVolume());
        updateSettingsParams.put("music", user.getSettings().getMusicVolume());
        jdbcTemplate.update(Query.UPDATE_SETTINGS, updateSettingsParams);
    }
    
    
    // Updates the scores for a specific user
    
    public void updateScores(User user)
    {
        Date currentDate = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = dataFormat.format(currentDate);

    	Map<String, String> updateScoreParams = new HashMap<>(3);
    	updateScoreParams.put("userId", Integer.toString(user.getId()));
    	updateScoreParams.put("level", Integer.toString(user.getLevel()));
    	updateScoreParams.put("score", Integer.toString(user.getScore()));
    	updateScoreParams.put("date", currentTime);
        jdbcTemplate.update(Query.UPDATE_SCORES, updateScoreParams);
    }
 }
