package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.BoardDAO;

public class InsertBoardAction implements SistAction {

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//새로운 게시물을 등록할 때에 
		//검색된 상태유지를 제거합니다.
		HttpSession session = request.getSession();
		session.removeAttribute("keyword");
//		session.removeAttribute("searchColumn");
		
		String headerLine = "새글작성";
		String title = "";
		
		int no = 0;
		//일단, 새글이라고 보고 no에 0을 저장
		
		if(request.getParameter("no") != null) {
			no = Integer.parseInt(request.getParameter("no"));
			headerLine = "답글작성";
			BoardDAO dao = BoardDAO.getInstance();
			title = "답글)"+dao.findByNo(no, false).getTitle();
			
		}
		//만약 답글달기 이면
		//부모글의 글번호를 no에 저장합니다.
		
		request.setAttribute("no", no);
		request.setAttribute("headerLine", headerLine);
		request.setAttribute("title",title);		
		return "insertBoard.jsp";
	}
}






