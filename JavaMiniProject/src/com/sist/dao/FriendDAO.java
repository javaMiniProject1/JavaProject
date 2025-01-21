package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class FriendDAO {
	Connection conn;
	PreparedStatement ps ;
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:XE";
	private static FriendDAO dao;
	
	public FriendDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static FriendDAO newInstance() {
		if (dao == null) {
			dao = new FriendDAO();
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
	
	public void addFriend(String requesterId, String receiverId) {
	    try {
	        getConnection();
	        // 자동 커밋 비활성화
	        conn.setAutoCommit(false);

	        // 첫 번째 쿼리: 친구 요청 삽입
	        String sql = "INSERT INTO friend(requester_id, receiver_id, status) VALUES(?, ?, 'A')";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, requesterId);
	        ps.setString(2, receiverId);
	        ps.executeUpdate();
	        

	        
	        ps.clearParameters();
	        // 두 번째 쿼리: 역방향 데이터 삽입
	        String reverseSql = "INSERT INTO friend(requester_id, receiver_id, status) VALUES(?, ?, 'A')";
	        ps = conn.prepareStatement(reverseSql);
	        ps.setString(1, receiverId);
	        ps.setString(2, requesterId);
	        ps.executeUpdate();

	        conn.commit();
	    } catch (Exception ex) {
	        try {
	                conn.rollback();
	        } catch (Exception rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        ex.printStackTrace();
	    } finally {
	        try {
	                conn.setAutoCommit(true);
	        } catch (Exception autoCommitEx) {
	            autoCommitEx.printStackTrace();
	        }
	        disconnetion();
	    }
	}
	public boolean isAlreadyFriend(String currentUserId, String friendId) {
	    boolean isFriend = false;

	    try {
	        getConnection();
	        
	        // SQL 쿼리: 두 ID로 친구 관계 여부 확인
	        String sql = "SELECT COUNT(*) FROM friend WHERE " +
	                     "(requester_id = ? AND receiver_id = ?) OR " +
	                     "(requester_id = ? AND receiver_id = ?)";
	        ps = conn.prepareStatement(sql);
	        
	        // 쿼리에 매개변수 설정
	        ps.setString(1, currentUserId);
	        ps.setString(2, friendId);
	        ps.setString(3, friendId);
	        ps.setString(4, currentUserId);

	        // 쿼리 실행 및 결과 처리
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1); // COUNT 결과 가져오기
	            isFriend = count > 0; // 친구 관계 여부 확인
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        disconnetion();
	    }

	    return isFriend;
	}
	
	public List<String> getFriendList(String currentId) {
		List<String> list = new ArrayList<String>();
		try {
			getConnection();
			String sql = "SELECT DISTINCT receiver_id FROM friend WHERE requester_id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, currentId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disconnetion();
		}
		return list;
	}
	
	
}
