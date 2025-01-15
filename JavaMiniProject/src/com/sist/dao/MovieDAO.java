package com.sist.dao;
import com.sist.main.*;
import java.sql.*;

public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.238.142.124:1521:XE";
	private static MovieDAO dao;
	
	public MovieDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
		}
	}
	
	public static MovieDAO newInstance() {
		if(dao==null) dao=new MovieDAO();
		return dao;
	}
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr_2","happy");
		} catch (Exception e) {
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
		}
	}
	
//	기능
	public void MovieInsert(MovieVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO movie VALUES(movie_mno_seq.nextval,"
					+ "?,?,?,?,?,?,?,?,?,?,0,?,?)";
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getM_title());//title
			ps.setString(2, vo.getM_eng_title());//eng title
			ps.setString(3, vo.getM_post());//post
			ps.setString(4, vo.getNation());//nation
			ps.setString(5, vo.getGenre());//genre
			ps.setString(6, vo.getRuntime());//runtime
			ps.setDate(7, new java.sql.Date(vo.getReg_date().getTime()));//regdate
			ps.setInt(8, vo.getTotal_audi());//total audi
			ps.setString(9, vo.getDir());//dir
			ps.setString(10, vo.getAct());//act
			ps.setDouble(11, vo.getRaiting());;//rate
			ps.setString(12, vo.getStory());//story
			ps.setString(13, vo.getGrade());//grade
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
}
