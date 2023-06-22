




package com.sist.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;

public class InsertBoardOKAction implements SistAction {

	@Override
	public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = BoardDAO.getInstance();
		
		//새로운 글번호를 위한 메소드를 통하여 글번호를 발해합니다.
		int no = dao.getNextNo();
		
		//일단 새글이라고 봅니다.
		int b_ref = no;	//b_ref는 글번호와 동일하게 설정
		int b_level = 0;	//b_level은 0으로 설정
		int b_step = 0;		//b_step은 0으로 설정
		
		request.setCharacterEncoding("utf-8");
		String path = request.getRealPath("board");
		System.out.println("path:"+path);
		MultipartRequest multi =
		new MultipartRequest(request, path, 1024*1024*5, "utf-8");
		
		int pno = Integer.parseInt(multi.getParameter("no"));
		//부모글의 글번호를 갖고 옵니다.
		//새글 : 0
		//답글 : 0이 아닌값(부모글의 글번호)
		
		//만약 답글작성이면
		if(pno != 0) {
			
			//부모글의 정보를 갖고 옵니다.
			BoardVO p= dao.findByNo(pno, false);
			
			//부모글의 b_ref, b_level, b_step을 갖고 옵니다.
			b_ref = p.getB_ref();
			b_level = p.getB_level();
			b_step = p.getB_step();
			
			//이미 달려 있는 답글들의 b_step을 1씩 증가합니다.
			dao.updateStep(b_ref, b_step);
			
			b_level++;	//부모글의 b_level + 1 로 설정
			b_step++;	//부모글의 b_step + 1로 설정
		}
		
		BoardVO b = new BoardVO();
		b.setNo(no);
		b.setTitle(multi.getParameter("title"));
		b.setWriter(multi.getParameter("writer"));
		b.setPwd(multi.getParameter("pwd"));
		b.setContent(multi.getParameter("content"));
		b.setFname(multi.getFilesystemName("fname"));
		b.setB_ref(b_ref);
		b.setB_level(b_level);
		b.setB_step(b_step);
		
		int re = dao.insertBoard(b);
		if(re == 1) {
			request.setAttribute("msg", "등록성공");
		}else {
			request.setAttribute("msg", "등록실패");
		}
		
		
		return "insertBoardOK.jsp";
	}

}










