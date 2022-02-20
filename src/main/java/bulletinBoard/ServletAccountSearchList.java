/*
	処理内容:	アカウント一覧サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletAccountSearchList
 */
@WebServlet("/ServletAccountSearchList")
public class ServletAccountSearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccountSearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		String searchName = (String)session.getAttribute("searchUserName");
		String selectMatch = (String)session.getAttribute("selectMatch");
		ArrayList<UserInfo> accountList = (ArrayList<UserInfo>)session.getAttribute("AccountSearchList");
		ArrayList<UserInfo> userList = (ArrayList<UserInfo>)session.getAttribute("UserList");
		String pageNo = request.getParameter("accountPageNo");
		if (pageNo == null || pageNo.equals("")) {
			pageNo = "1";
		}
		request.setAttribute("accountPageNo", pageNo);
		UserDAO userDao = new UserDAO();
		int userCnt = userDao.findCntUserInfo(searchName, selectMatch);
		int totalNum = ((Double)Math.ceil(userCnt/10.0)).intValue();
		request.setAttribute("totalNum", totalNum);
		accountList = userDao.findUserInfoList(searchName, selectMatch, pageNo);
		session.setAttribute("AccountSearchList", accountList);
		if (userList == null) {
			userList = userDao.getAllUserList();
			session.setAttribute("UserList", userList);
		}
		ArrayList<GenderInfo> genderList = (ArrayList<GenderInfo>)session.getAttribute("GenderList");
		if (genderList == null) {
			GenderDAO genderDao = new GenderDAO();
			genderList = genderDao.findGenderList();
			session.setAttribute("GenderList", genderList);
		}		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountSearchList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String searchName = request.getParameter("searchUserName");
		String selectMatch = request.getParameter("selectMatch");
		if (searchName == null) {
			doGet(request,response);
			return;
		}
		HttpSession session = request.getSession(false);
		session.setAttribute("searchUserName", searchName);
		session.setAttribute("selectMatch", selectMatch);
		session.setMaxInactiveInterval(60 * 60 * 24);
		String btn = request.getParameter("btn");
		if (btn.equals("search")) {
			UserDAO userDao = new UserDAO();
			int userCnt = userDao.findCntUserInfo(searchName, selectMatch);
			int totalNum = ((Double)Math.ceil(userCnt/10.0)).intValue();
			request.setAttribute("totalNum", totalNum);
			String pageNo = "1";
			request.setAttribute("accountPageNo", pageNo);
			ArrayList<UserInfo> accountList = userDao.findUserInfoList(searchName, selectMatch, pageNo);
			request.setAttribute("AccountSearchList", accountList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountSearchList.jsp");
			dispatcher.forward(request, response);
		} if (btn.equals("Register")) {
			session.removeAttribute("accountyRegister");
			response.sendRedirect("ServletAccountRegister");
		} else {
			String userId = request.getParameter("userId");
			String email = request.getParameter("email");
			String userName = request.getParameter("userName");
			Date birth = Date.valueOf(request.getParameter("birth"));
			int genderId = Integer.parseInt(request.getParameter("genderId"));
			int manager = Integer.parseInt(request.getParameter("manager"));
			int errorCount = Integer.parseInt(request.getParameter("errorCount"));
			UserInfo info = new UserInfo(userId, userName, email, birth, genderId, manager, errorCount);
			if (btn.equals("modify")) {
				session.setAttribute("AccountModify", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletAccountModify");
			} else if (btn.equals("delete")) {
				session.setAttribute("AccountDel", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletAccountDelConfirm");
			}
		}
	}

}
