package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자
	public PhoneController() {
		super();
	}
	// 메소드 gs

	// 메소드 일반
	// get방식으로 요청시 호출 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 포스트 방식일때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// System.out.println("controller");
		// 액션 파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);

		if ("list".equals(action)) { // 리스트
			// 데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> phoneList = phoneDao.getPersonList();
			System.out.println(phoneList);

			// reuest에 데이터 추가
			request.setAttribute("pList", phoneList);

			// 데이터 + html ->jsp로 시킴
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		} else if ("writeForm".equals(action)) {// 등록폼
			// System.out.println("등록폼");
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
		} else if ("write".equals(action)) { // 등록일때
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// vo만들어서 값 초기화
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo);

			// 저장
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personInsert(personVo);
			System.out.println(count);

			// 리다이랙트 list
			response.sendRedirect("/phonebook2/pbc?action=list");
		} else if ("delete".equals(action)) { // 삭제
			// 피라미터에서 id값 꺼냄
			int id = Integer.parseInt(request.getParameter("id"));

			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personDelete(id);
			System.out.println(count);

			// 리다이랙트 list
			response.sendRedirect("./pbc?action=list");

		} else if ("updateForm".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/updateForm.jsp");
			rd.forward(request, response);
			
		} else if ("update".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// PersonVo 만들기
			PersonVo personVo = new PersonVo(id, name, hp, company);
			
			// PhoneDao personUpdate()로 수정하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personUpdate(personVo);
			System.out.println(count);

			// 리스트로 리다이렉트 시키기
			response.sendRedirect("./pbc?action=list");
		} else {
			System.out.println("action 파라미터 없음");
		}

	}

	// post방식으로 요청시 호출 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("여기는 post");
		doGet(request, response);
	}

}
