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
 * Servlet implementation class ServletLogout
 */
@WebServlet("/ServletLogout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/logout.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String logout = request.getParameter("logout");
		HttpSession session = request.getSession(false);
		
		switch (logout) {
			case "yes":
				session.setAttribute("User", null);
				break;
			case "no":
				break;
		}
		
		ThreadDAO dao = new ThreadDAO();
		ArrayList<ThreadDispInfo> threadList = dao.searchAndSetList(1);
		request.setAttribute("sendThreadList", threadList);
		
		CategoryListDAO categoryDao = new CategoryListDAO();
		ArrayList<CategoryListInfo> categoryList = categoryDao.findCategoryList();
		request.setAttribute("sendCategoryList", categoryList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");
		dispatcher.forward(request, response);
	}

}
