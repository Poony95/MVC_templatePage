package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.catalina.users.SparseUserDatabase;

import com.sist.db.ConnectionProvider;
import com.sist.vo.BoardVO;

public class BoardDAO {
	public static int totalRecord; 	//전체 레코드수 
	public static int pageSIZE = 10;//한화면에 보여줄 레코드수
	public static int totalPage;	//전체 페이지수
	
	
	private static BoardDAO dao;
	private BoardDAO() {		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	//새로운 글번호 발생을 위한 메소드
	public int getNextNo() {
		int no = 0;
		String sql = "select nvl(max(no),0) + 1 from board";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				no = rs.getInt(1);
			}
			ConnectionProvider.close(conn, stmt);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return no;
	}
	
	
	public int insertBoard(BoardVO b) {
		int re = -1;
		try {
			String sql = "insert into board(no,title,writer,pwd,content,regdate,hit,fname,b_ref,b_level,b_step) values(?,?,?,?,?,sysdate,0,?,?,?,?)";	
			
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b.getNo());
			pstmt.setString(2, b.getTitle());
			pstmt.setString(3, b.getWriter());
			pstmt.setString(4, b.getPwd());
			pstmt.setString(5, b.getContent());
			pstmt.setString(6, b.getFname());
			pstmt.setInt(7, b.getB_ref());
			pstmt.setInt(8, b.getB_level());
			pstmt.setInt(9, b.getB_step());
			
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
			
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return re;
	}
	
	
	//전체레코드 수를 반환하는 메소드
	public int getTotalRecord(String keyword, String searchColumn) {
		
		int n =  0;
		String sql = "select count(*) from board ";
		if(keyword != null) {
			sql += "where "+searchColumn+" like '%"+keyword+"%'";
		}
		
		System.out.println("sql:"+sql);
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				n = rs.getInt(1);
			}
			ConnectionProvider.close(conn, stmt, rs);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		System.out.println("레코드수 :"+n);
		return n;
	}
	
	public ArrayList<BoardVO> findAll(int pageNUM, String keyword, String searchColumn){
		
		totalRecord = getTotalRecord(keyword, searchColumn);

		System.out.println("totalRecord:"+totalRecord);
		
		totalPage = (int)Math.ceil((totalRecord / (double)pageSIZE));
		
		int start = (pageNUM-1)*pageSIZE+1; //현재 페이지에 보여줄 시작레코드
		int end=start+pageSIZE-1;			//현재 페이지에 보여줄 마지막레코드
		
		if(end > totalRecord) {
			end = totalRecord;
		}
		
		System.out.println("start:"+start);
		System.out.println("end:"+end);
		
		
		System.out.println("totalRecord:"+totalRecord);
		System.out.println("totalPage:"+totalPage);
		
		ArrayList<BoardVO> list = 
				new ArrayList<BoardVO>();
		
		String sql = "select no,title,writer,pwd,content,regdate,hit,fname,b_ref,b_level,b_step "
				+ "from (select rownum n, no,title,writer,pwd,content,regdate,hit,fname,b_ref,b_level,b_step "
				+ "from (select * from board ";
				
				if(keyword != null) {
					sql += "where "+searchColumn+" like '%"+keyword+"%'";
				}
								
				sql += " order by b_ref desc, b_step)) a "
				+ "where a.n between ? and ?";
		
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVO b = new BoardVO();
				b.setNo(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setWriter(rs.getString(3));
				b.setPwd(rs.getString(4));
				b.setContent(rs.getString(5));
				b.setRegdate(rs.getDate(6));
				b.setHit(rs.getInt(7));
				b.setFname(rs.getString(8));
				b.setB_ref(rs.getInt(9));
				b.setB_level(rs.getInt(10));
				b.setB_step(rs.getInt(11));
				list.add(b);
			}
			ConnectionProvider.close(conn, pstmt, rs);
					
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return list;
	}
	
	public void updateHit(int no) {
		String sql = 
		"update board set hit=hit+1 where no=?";
		
		try {
			Connection conn = 
			ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		
	}
	
	
	
	
	public BoardVO findByNo(int no, boolean hit) {
		BoardVO b = new BoardVO();
		try {
			if(hit==true) {
				updateHit(no);
			}
			
			String sql = "select * from board where no=?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				b.setNo(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setWriter(rs.getString(3));
				b.setPwd(rs.getString(4));
				b.setContent(rs.getString(5));
				b.setRegdate(rs.getDate(6));
				b.setHit(rs.getInt(7));
				b.setFname(rs.getString(8));
				b.setB_ref(rs.getInt(9));
				b.setB_level(rs.getInt(10));
				b.setB_step(rs.getInt(11));
			}
			ConnectionProvider.close(conn, pstmt, rs);		
			
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return b;
	}
	
	//서로관련 있는 글일때에(b_ref가 동일할때에)
	//글이 출력되는 순서를 위하여 
	//이미 달려있는 답글들의 b_step를 증가시키는 메소드를 정의
	public void updateStep(int b_ref, int b_step) {
		String sql = "update board set b_step = b_step + 1 where b_ref=? and b_step > ?";
		try {
			
			Connection conn 
			= ConnectionProvider.getConnection();
			PreparedStatement pstmt =
					conn.prepareStatement(sql);
			pstmt.setInt(1, b_ref);
			pstmt.setInt(2, b_step);
			pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
}






































