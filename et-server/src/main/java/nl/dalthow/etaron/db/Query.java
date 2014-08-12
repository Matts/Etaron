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
	// Query Declaration
	
    public static final String USER_SEARCH = "SELECT\n" + "    cr.Username, cr.UserID id, cr.Email, st.Music, st.Sound\n" + "FROM\n" + "    Credentials cr left outer join Settings st\n" + "ON\n" + "    cr.UserID = st.UserID\n" + "WHERE\n" + "    cr.Username = :username and\n" + "    cr.Password = :password";
    public static final String SCORE_SEARCH = "select \n" + "    max(sc.Score) value, sc.Level\n" + "from\n" + "    Scores sc\n" + "WHERE\n" + "    sc.UserID = :userId\n" + "group by sc.Level";
    public static final String UPDATE_SETTINGS = "insert into Settings (UserID, Music, Sound) values (:userId, :music, :sound) on duplicate key update Music=values(Music), Sound=values(Sound)";
	public static final String UPDATE_SCORES = "insert into Scores (UserID, Level, Score, Date) values (:userId, :level, :score, :date)";
}
