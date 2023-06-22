package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.db.ConnectionProvider;

public class MemberDAO {
	private static MemberDAO dao;
	private MemberDAO() {		
	}
	
	public static MemberDAO getInstance() {
		if(dao == null) {
			dao = new MemberDAO();
		}
		return dao;
	}
	
	
	//아이디와 암호를 매개변수로 전달받아 올바른 회원인지 
	//판별하는 메소드
	
	//-1	: id가 없을때
	//0		: 암호가 틀렸을때
	//1		: 올바른 회원
	public int isMember(String id, String pwd) {
		int re = -1;
		String sql = "select pwd from member where id=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String dbPwd = rs.getString(1);
				if(dbPwd.equals(pwd)) {
					re = 1;
				}else {
					re = 0;
				}
			}
			ConnectionProvider.close(conn, pstmt,rs);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}	
		
		return re;
	}
}









