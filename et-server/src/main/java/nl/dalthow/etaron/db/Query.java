/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class Query.java
 * 
 **/

package nl.dalthow.etaron.db;

public class Query 
{
	// Query declaration

	public static final String USER_SEARCH = "SELECT users.id, users.username, users.email, settings.music, settings.sound \n FROM users \n LEFT OUTER JOIN settings \n ON users.id = settings.usersId \n WHERE users.username = :username AND   users.password = :password";
    public static final String SCORE_SEARCH = "SELECT max(scores.score) AS 'score', scores.level \n FROM scores \n WHERE scores.usersId = :userId \n GROUP BY scores.level";
    public static final String UPDATE_SETTINGS = "INSERT INTO settings (usersId, music, sound) VALUES (:userId, :music, :sound) \n ON DUPLICATE KEY UPDATE music = values(music), sound = values(sound)";
	public static final String UPDATE_SCORES = "INSERT INTO scores (usersId, level, score, date) VALUES (:userId, :level, :score, :date) \n ON DUPLICATE KEY UPDATE level = values(level), score = values(score), date = values(date)";
}