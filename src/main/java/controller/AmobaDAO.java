package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Game;

/**
 * Database services of the game.
 * 
 * @author Jockay
 *
 */
public class AmobaDAO {
	
	/**
	 * Saves game result to database.
	 * 
	 * @return True if method succeed at database writing, else returns false.
	 * @throws SQLException
	 */
	public boolean uploadGameState(Game game) throws SQLException { 
		final String INSERT = "INSERT INTO AMOBA VALUES (?, ?, ?)";
		final PreparedStatement pstmtInsert;
		final String today;

		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
				"H_HG8H7I", "kassai");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
		Calendar cal = Calendar.getInstance();
		today = dateFormat.format(cal.getTime());
		System.out.println(today);
		pstmtInsert = conn.prepareStatement(INSERT);
		pstmtInsert.setString(1, today);
		if (game.getP1().isWon()) {
			pstmtInsert.setString(2, game.getP1().getName());
			pstmtInsert.setString(3, game.getP2().getName());
		} else if (game.getP2().isWon()) {
			pstmtInsert.setString(2, game.getP2().getName());
			pstmtInsert.setString(3, game.getP1().getName());
		} else {
			conn.close();
			return false;
		}
		pstmtInsert.executeUpdate();

		conn.close();
		return true;
	}
	
}
