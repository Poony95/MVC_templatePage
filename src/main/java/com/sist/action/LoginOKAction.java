package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.MemberDAO;

public class LoginOKAction implements SistAction{

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String view  = "loginOK.jsp";
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		MemberDAO dao = MemberDAO.getInstance();
		int re = dao.isMember(id, pwd);
		if(re != 1) {
			view = "login.jsp";
			if(re == 0) {
				request.setAttribute("msg", "잘못된 암호니다.");
			}else {
				request.setAttribute("msg", "존재하지 않는 아이디입니다.");
			}
		}else {
			HttpSession session = 
					request.getSession();
			session.setAttribute("id", id);
		}
		return view;
	}

}








