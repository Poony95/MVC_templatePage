package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.BoardDAO;

public class ListBoardAction implements SistAction{

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session= request.getSession();
	
		String all = request.getParameter("all");
		System.out.println("all-------->"+all);
		
		String searchColumn = null;		
		String keyword = null;
	
		
		if(session.getAttribute("keyword") != null) {
			keyword = (String)session.getAttribute("keyword");
			searchColumn = (String)session.getAttribute("searchColumn");
		}
		
		if(request.getParameter("keyword") != null) {
			keyword = request.getParameter("keyword");
			searchColumn = request.getParameter("searchColumn");
		}
		
		if(all != null && all.equals("yes")) {
			keyword = "";
		}
		
		
		System.out.println("keyword:"+keyword);
		System.out.println("searchColumn:"+searchColumn);
		
		BoardDAO dao = BoardDAO.getInstance();
		int pageNUM = 1;
		if(request.getParameter("pageNUM") != null){
			pageNUM = Integer.parseInt(request.getParameter("pageNUM"));
		}
		
		System.out.println("pageNUM:"+pageNUM);
		request.setAttribute("list", dao.findAll(pageNUM, keyword, searchColumn));
		request.setAttribute("totalPage", dao.totalPage);
		
		//검색어를 세션에 상태유지 합니다.		
		session.setAttribute("keyword", keyword);
		session.setAttribute("searchColumn", searchColumn);
		
		return "listBoard.jsp";
	}

}










