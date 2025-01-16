package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class FreeBoardDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private String URL="jdbc:oracle:thin:@211.238.142.124:1521:XE";
	private static FreeBoardDAO dao;
	
	public FreeBoardDAO()
	{
		//드라이버생성
		try {

			Class.forName("orcale.jdbc.driver.OracleDriver");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void getConnection()
	{
		try {
			
			conn=DriverManager.getConnection(URL,"hr_2","happy");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void disConnection()
	{
		try {
			if(conn!=null) conn.close();
			if(ps!=null) ps.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static FreeBoardDAO newInstance()
	{
		if(dao==null)
			dao=new FreeBoardDAO();
		return dao;
		
	}
	
	//기능
	
	//목록 (페이징기법) => 인라인뷰 사용
	public List<FreeBoardVO> boardListData(int page)
	{
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
		try {
			
			getConnection();
			String sql = "SELECT no,subject,name,regdate,hit,num "
					   + "FROM (SELECT no,subject,name,regdate,hit,rownum as num "
					          + "FROM (SELECT no,subject,name,regdate,hit "
					          		+ "FROM freeboard ORDER BY no DESC)) WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FreeBoardVO vo = new FreeBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
				
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
		
	}
	//총페이지구하기
	public int boardTotalPage()
	{
		int total=0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM freeboard";
			ps= conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		
		return total;
		
	}
	
	//상세보기 WHERE -> Primary key 값을 넘겨준다
	//게시물 번호 => 자동증가
	public FreeBoardVO boardDetailData(int no)
	{
		FreeBoardVO vo =new FreeBoardVO();
		try {
			getConnection();
			String sql = "UPDATE freeboard SET hit=hit+1 WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			/*
			 
			 executeQuery() => 데이터검색(SELECT)
			 extcuteUpdate() => 데이터 변경
			 					INSERT / UPDATE / DELETE				
			 
			 */
			sql="SELECT no,name,subject,content,regdate,hit FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
		
	}
	
	//글쓰기 Insert
	public void boardInsert(FreeBoardVO vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd) VALUES(fb_no_seq.nextval,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName())
			;
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	//수정하기 1. 수정 데이터 읽기 2. 실제수정
	public FreeBoardVO boaredUpdateData(int no)
	{
		//한 개의 게시물 읽기 => primary key이용
		//한 개에 대한 데이터 읽기 vo , 여러개에 대한 데이터 읽기 list<vo>
		
		FreeBoardVO vo = new FreeBoardVO();
		try {
			getConnection();
			String sql="SELECT no,name,subject,content FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	public boolean boardUpdate(FreeBoardVO vo)
	{
		boolean bCheck=false;
		try {
			getConnection();
			String sql="SELECT pwd FROM freeboard WHERE no="+vo.getNo();
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd()))
			{
				bCheck=true;
				sql="UPDATE freeboard SET name=?, subject=?, content=? WHERE no=?";
				//"SELECT pwd FROM freeboard WHERE no="+vo.getNo(); -> 숫자일 땐 이 방식이 편하다
				//숫자는 ' ' 싱글따옴표를 안써도 되기 때문에
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3,vo.getContent());
				ps.setInt(4, vo.getNo());
				//실행
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		return bCheck;
	}
	
	//삭제
	public boolean boardDelete(int no,String pwd)
	{
		boolean bCheck = false;
		try {
			getConnection();
			String sql="SELECT pwd FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				sql="DELETE FROM freeboard WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	
	
	//찾기
	public List<FreeBoardVO> boardFindData(String col, String fd)
	{
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
		try {
			getConnection();
			String sql="SELECT no,subject,name,regdate,hit, FROM freeboard WHERE "+col+" LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FreeBoardVO vo = new FreeBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			disConnection();
		}
		return list;
	}
	
	public int boardFindCount(String col,String fd)
	{
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) "
				     +"FROM freeboard "
				     +"WHERE "+col+" LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ResultSet rs=ps.executeQuery();
			rs.next();
			 count=rs.getInt(1);
			 rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return count;
	}
	
	
	
}



































