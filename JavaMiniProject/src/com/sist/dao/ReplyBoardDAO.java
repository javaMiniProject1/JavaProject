package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class ReplyBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static ReplyBoardDAO dao;
	private final String URL="jdbc:oracle:thin:@211.238.142.124:1521:XE";
	
	public ReplyBoardDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 리플랙션 => 클래스 이름으로 메모리 할당 / 메소드 제어
			// new 역할 => 메소드명을 모르는 경우에도 호출 가능
		}catch(Exception ex) {}
	}
	// 싱글턴 만들기
	public static ReplyBoardDAO newInstance()
	{
		if(dao==null)
			dao=new ReplyBoardDAO();
		return dao;
	}
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr_2","happy");
		}catch(Exception ex) {}
	}
	public void disConnection()
	{
		try
		{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}catch(Exception ex) {}
	}
	//-----------------------------------------------------자바에서 오라클연동 기본(공통) => JDBC
	// JDBC: 원시소스 => DBCP => ORM(MyBatis,Hibernate => JDA)
	// 기능
	// 1. 목록 출력 => 인라인뷰 => 1page 10개 => 최신순
	// index 사용 X => 현재 추가, 수정, 삭제가 많음 => index 속도 느려짐 => ORDER BY 사용
	public List<ReplyBoardVO> boardListData(int page)
	{
		List<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
		try
		{
			getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+ "FROM replyBoard ORDER BY group_id DESC,group_step ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// 실행 요청
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ReplyBoardVO vo=new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
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
	// 1-1. 총페이지 출력
	public int boardTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM replyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
	// 2. 글쓰기 => INSERT
	public void boardInsert(ReplyBoardVO vo)
	{
		try
		{
			getConnection();
			// 새로운 그룹 생성 => 그룹번호: group_id
			String sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id) "
					+ "VALUES(rb_no_seq.nextval,?,?,?,?,(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate(); //commit
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
    // 3. 상세보기 => WHERE
	public ReplyBoardVO boardDetailData(int type,int no)
	{
		ReplyBoardVO vo=new ReplyBoardVO();
		try
		{
			getConnection();
			//sql 1. 조회수 증가
			if(type==1)
			{
				String sql="UPDATE replyBoard SET "
						+ "hit=hit+1 "
						+ "WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			//sql 2. 상세보기 내용 읽기
			String sql="SELECT no,name,subject,content,"
					+ "TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit "
					+ "FROM replyBoard "
					+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			// sql 한 기능을 수행할 때 => sql 문장은 여러 개일 수 있다
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
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
	// 4. 수정하기 => UPDATE
	public ReplyBoardVO boardUpdateData(int no)
	{
		ReplyBoardVO vo=new ReplyBoardVO();
		try
		{
			getConnection();
			String sql="SELECT no,name,subject,content "
					 +"FROM replyBoard "
					 +"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			rs.close();
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
	// 4.1. 실제 수정
	public boolean boardUpdate(ReplyBoardVO vo)
	{
		boolean bCheck=false;// 비밀번호 처리
		/*
		 *  메소드: 사용자 요청 처리
		 *  1) 사용자 요청값 받기
		 *   => 매개변수
		 *  2) 요청 처리 후 결과값
		 *   => 경우의 수
		 *   1. 목록: List<~VO>
		 *   2. 상세보기: ~VO
		 *   3. 비밀번호 맞다 / 아니다
		 *            ------------boolean
		 *   4. 경우의 수가 3개 이상
		 *      String / int
		 *      ------알아볼 수 있게 처리
		 *      |로그인 처리 => NOID / NOPWD / OK
		 *      -------------------------------자바 => 메소드 제작 => 데이터 확인(VO)
		 *      
		 *  1. 형식 => String
		 *    - INSERT
		 *     => DEFAULT가 많은 경우
		 *       INSERT INTO table_name(컬럼,컬럼,...)
		 *       VALUES(값,...)
		 *     => DEFAULT가 없는 경우
		 *       INSERT INTO table_name VALUES(값,...)
		 *      => 날짜, 문자 => ''
		 *    - UPDATE
		 *      UPDATE table_name SET
		 *      컬럼=값,컬럼=값[where 조건]
		 *    - DELETE
		 *      DELETE FROM fable_name
		 *      table_name
		 *      -------------------------------데이터 변경 
		 */
		try
		{
			getConnection();
			// 비밀번호
			String sql="SELECT pwd FROM replyBoard "
					 +"WHERE no="+vo.getNo();
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			// 오라클          사용자
			if(db_pwd.equals(vo.getPwd()))
			{
				bCheck=true;
				sql="UPDATE replyboard SET "
				   +"name=?,subject=?,content=? "
				   +"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				// 수정 명령
				ps.executeUpdate();
				// INSERT / UPDATE / DELETE가 여러 개인 경우(SELECT 제외) => 트랜잭션
			}
			else
			{
				bCheck=false;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return bCheck;
	}
	// 5. 답변하기 => 트랜잭션
	// 6. 삭제하기 => 트랜잭션
}
