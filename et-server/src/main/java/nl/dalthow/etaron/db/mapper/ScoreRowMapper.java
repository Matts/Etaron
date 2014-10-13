/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class ScoreRowMapper.java
 * 
 **/

package nl.dalthow.etaron.db.mapper;

import nl.dalthow.etaron.model.Score;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreRowMapper implements RowMapper<Score> 
{
	// The row mapper used for the scores table
	
    @Override
    public Score mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
        Score score = new Score();
        score.setValue(rs.getLong("score"));
        score.setLevel(rs.getInt("level"));
        
        return score;
    }
}