package com.sist.dao;
import com.sist.main.*;
import com.sist.vo.MovieVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
					+ "?,?,?,?,?,?,?,?,?,?,NVL(?,0),?,?)";
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
			ps.setDouble(11, vo.getRaiting() != null ? vo.getRaiting() : 0.0);
			ps.setString(12, vo.getStory());//story
			ps.setString(13, vo.getGrade());//grade
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	public List<MovieVO> MovieList() {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			getConnection();
			String sql = "SELECT m_no,m_title,m_post,m_eng_title,nation,genre,runtime,reg_date,total_audi,dir,act,rating,story,grade FROM movie";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setM_no(rs.getInt(1));
				vo.setM_title(rs.getString(2));
				vo.setM_post(rs.getString(3));
				vo.setM_eng_title(rs.getString(4));
				vo.setNation(rs.getString(5));
				vo.setGenre(rs.getString(6));
				vo.setRuntime(rs.getString(7));
				vo.setReg_date(rs.getDate(8));
				vo.setTotal_audi(rs.getInt(9));
				vo.setDir(rs.getString(10));
				vo.setAct(rs.getString(11));
				vo.setRaiting(rs.getDouble(12));
				vo.setStory(rs.getString(13));
				vo.setGrade(rs.getString(14));
				list.add(vo);
				
			}
			rs.close();

		} catch (Exception e) {
		} finally {
			disConnection();
		}
		return list;
	}
	public List<MovieVO> MovieListData(int page)
	   {
		   List<MovieVO> list=
				  new ArrayList<MovieVO>();
		   try
		   {
			   // 1. 연결 
			   getConnection();
			   // 2. SQL문장 제작 
			   String sql = "SELECT m_no,m_title,m_post,m_eng_title,nation,genre,runtime,reg_date,total_audi,dir,act,rating,story,grade FROM movie WHERE m_no BETWEEN ? AND ?";
			   // 3. SQL문장 오라클로 전송 
			   ps=conn.prepareStatement(sql);
			   // 4. ?에 데이터값을 채운다 
			   /*
			    *   1page => 1~12
			    *   2page => 13~24
			    */
			   int rowSize=12;
			   int start=(rowSize*page)-(rowSize-1);
			   int end=rowSize*page;
			   
			   ps.setInt(1, start);
			   ps.setInt(2, end);
			   
			   // 실행=> 결과값 
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   MovieVO vo = new MovieVO();
					vo.setM_no(rs.getInt(1));
					vo.setM_title(rs.getString(2));
					vo.setM_post(rs.getString(3));
					vo.setM_eng_title(rs.getString(4));
					vo.setNation(rs.getString(5));
					vo.setGenre(rs.getString(6));
					vo.setRuntime(rs.getString(7));
					vo.setReg_date(rs.getDate(8));
					vo.setTotal_audi(rs.getInt(9));
					vo.setDir(rs.getString(10));
					vo.setAct(rs.getString(11));
					vo.setRaiting(rs.getDouble(12));
					vo.setStory(rs.getString(13));
					vo.setGrade(rs.getString(14));
					list.add(vo);
			   }
			   rs.close();
			   
		   }catch(Exception ex)
		   {
			   // 오류 확인 
			   ex.printStackTrace();
		   }
		   finally
		   {
			   // 닫기 
			   disConnection();
		   }
		   return list;
	   }
	
	public int MovieTotalPage() {
		   int total=0;
		   try {
			   getConnection();
			   String sql="SELECT CEIL(COUNT(*)/12.0) FROM movie";
			   ps=conn.prepareStatement(sql);
			   ResultSet rs = ps.executeQuery();
			   rs.next();
			   total= rs.getInt(1);
			   rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			disConnection();
		}
		   return total;
	   }
	
	 // 1-2 인기순위 10개 
	   public List<MovieVO> movieTop10()
	   {
		   List<MovieVO> list=
				   new ArrayList<MovieVO>();
		   try
		   {
			   getConnection();
			   String sql="select num,m_title "
			   		+ "from (select rownum as num,m_title "
			   		+ "from (select /*+INDEX ASC(movie m_mno_pk)*/m_title "
			   		+ "from movie)) "
			   		+ "where num<=15";
			   ps=conn.prepareStatement(sql);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   MovieVO vo=new MovieVO();
				   vo.setM_no(rs.getInt(1));
				   vo.setM_title(rs.getString(2));
				   list.add(vo);
			   }
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
		   return list;
	   }
	   public List<MovieVO> MovieFindData(String title)
	   {	
		   List<MovieVO> list=
				   new ArrayList<MovieVO>();
		   try
		   {
			   getConnection();
			   String sql="SELECT m_no,m_title,m_post,m_eng_title,nation,genre,runtime,reg_date,total_audi,dir,act,rating,story,grade FROM movie"
			   		+ " WHERE m_title LIKE '%'||?||'%' ORDER BY m_no";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, title);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   MovieVO vo = new MovieVO();
					vo.setM_no(rs.getInt(1));
					vo.setM_title(rs.getString(2));
					vo.setM_post(rs.getString(3));
					vo.setM_eng_title(rs.getString(4));
					vo.setNation(rs.getString(5));
					vo.setGenre(rs.getString(6));
					vo.setRuntime(rs.getString(7));
					vo.setReg_date(rs.getDate(8));
					vo.setTotal_audi(rs.getInt(9));
					vo.setDir(rs.getString(10));
					vo.setAct(rs.getString(11));
					vo.setRaiting(rs.getDouble(12));
					vo.setStory(rs.getString(13));
					vo.setGrade(rs.getString(14));
					list.add(vo);
			   }
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
		   return list;
	   }
	   public MovieVO MovieDetailData(int m_no)
	   {
		   MovieVO vo=new MovieVO();
		   try
		   {
			   getConnection();
			   String sql="SELECT m_no,m_title,m_post,m_eng_title,nation,genre,runtime,reg_date,total_audi,dir,act,rating,story,grade FROM movie WHERE m_no ="+m_no;
			   ps=conn.prepareStatement(sql);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
				vo.setM_no(rs.getInt(1));
				vo.setM_title(rs.getString(2));
				vo.setM_post(rs.getString(3));
				vo.setM_eng_title(rs.getString(4));
				vo.setNation(rs.getString(5));
				vo.setGenre(rs.getString(6));
				vo.setRuntime(rs.getString(7));
				vo.setReg_date(rs.getDate(8));
				vo.setTotal_audi(rs.getInt(9));
				vo.setDir(rs.getString(10));
				vo.setAct(rs.getString(11));
				vo.setRaiting(rs.getDouble(12));
				vo.setStory(rs.getString(13));
				vo.setGrade(rs.getString(14));
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
		   return vo;
	   }
}
