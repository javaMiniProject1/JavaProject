package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.sist.vo.MemberVO;

public class MemberDAO {
	Connection conn;
	PreparedStatement ps ;
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:XE";
	private static MemberDAO dao;
	
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static MemberDAO newInstance() {
		if (dao == null) {
			dao = new MemberDAO();
		}
		return dao;
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr_2", "happy");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void disconnetion() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/*
	member column_name
 	ID       NOT NULL VARCHAR2(50)  
	PWD      NOT NULL VARCHAR2(50)  
	NAME     NOT NULL VARCHAR2(51)  
	NICKNAME NOT NULL VARCHAR2(51)  
	BIRTHDAY          DATE          
	SEX               VARCHAR2(10)  
	POST              VARCHAR2(7)   
	ADDR1             VARCHAR2(100) 
	ADDR2             VARCHAR2(100) 
	PHONE             VARCHAR2(14)  
	EMAIL    NOT NULL VARCHAR2(100) 
	JOIN              CLOB 
	 */
	public MemberVO getMemberInfo(String id) { // 채팅방 회원 상세보기
		MemberVO vo = new MemberVO();
		try {
			getConnection(); 
			String sql = "SELECT id, name, nickname, sex "
					   + "FROM member "
					   + "WHERE id = ?";
			
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			vo.setId(rs.getString(1));
			vo.setName(rs.getString(2));
			vo.setNickname(rs.getString(3));
			vo.setSex(rs.getString(4));
			
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disconnetion();
		}
		
		return vo;
	}
	public MemberVO isLogin(String id,String pwd)
	   {
		   MemberVO vo=new MemberVO();
		   try
		   {
			   // 1. 연결 
			   getConnection();
			   // 2. SQL문장 전송 => id존재여부 확인 
			   String sql="SELECT COUNT(*) FROM member "
					     +"WHERE id=?";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, id);
			   
			   // 실행 요청 
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   int count=rs.getInt(1);
			   rs.close();
			   
			   if(count==0) // ID없는 상태 
			   {
				   vo.setMsg("NOID");
			   } 
			   else // ID있는 상태
			   {
				   // 비밀번호 확인 
				   sql="SELECT id,pwd,name,sex "
					  +"FROM member "
					  +"WHERE id=?";
				   ps=conn.prepareStatement(sql);
				   // 실행전에 ?에 값을 채운다 
				   ps.setString(1, id);
				   // 결과값 
				   rs=ps.executeQuery();
				   rs.next(); // 한줄씩 읽어 온다 
				   //            ----- RECORD
				   vo.setId(rs.getString(1));
				   vo.setName(rs.getString(3));
				   vo.setSex(rs.getString(4));
				   String db_pwd=rs.getString(2);
				   
				   // 비밀번호 검사 
				   if(db_pwd.equals(pwd)) // 로그인
				   {
					   vo.setMsg("OK");
				   }
				   else // 비밀번호가 틀리다 
				   {
					   vo.setMsg("NOPWD");
				   }
				   
			   }
			   
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disconnetion();
		   }
		   return vo;
	   }
	/*
	public ArrayList<String> getMemberId(MemberVO vo) { // UI 에서 연결할지 고민해야 함
		ArrayList<String> connUsers = new ArrayList<String>();
		try {
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return connUsers;
	}
	*/
	
}
