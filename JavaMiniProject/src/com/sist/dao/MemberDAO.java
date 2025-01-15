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
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
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
	public MemberVO getMemberInfo() { // 채팅방 회원 상세보기
		MemberVO vo = new MemberVO();
		try {
			getConnection(); 
			String sql = "SELECT id, name, nickname, sex "
					   + "FROM member";
			
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			vo.setId(rs.getString(1));
			vo.setName(rs.getNString(2));
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
