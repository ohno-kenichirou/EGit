/*
	処理内容:	アカウント一覧サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.io.IOException;
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
		UserDAO userDao = new UserDAO();
		String searchName = (String)session.getAttribute("searchUserName");
		String selectMatch = (String)session.getAttribute("selectMatch");
		ArrayList<UserInfo> accountList = (ArrayList<UserInfo>)session.getAttribute("AccountSearchList");
		ArrayList<UserInfo> userList = (ArrayList<UserInfo>)session.getAttribute("UserList");
		accountList = userDao.getUserInfoList(searchName, selectMatch);
		session.setAttribute("AccountSearchList", accountList);
		if (userList == null) {
			userList = userDao.getAllUserList();
			session.setAttribute("UserList", userList);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountSearchList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
