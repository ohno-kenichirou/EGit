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
 * Servlet implementation class ServletThreadCreateConfirm
 */
@WebServlet("/ServletThreadCreateConfirm")
public class ServletThreadCreateConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletThreadCreateConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/threadCreateConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		String title = request.getParameter("title");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String comment = request.getParameter("comment");
		String createThread = request.getParameter("createThread");
		
		RequestDispatcher dispatcher;
		
		if (createThread != null && createThread.equals("execution")) {
			
			UserInfo user = (UserInfo)session.getAttribute("User");
			NewThreadInfo newThread = new NewThreadInfo(title, categoryId, comment);
			ThreadDAO dao = new ThreadDAO();
			
			if (dao.newThread(newThread, user)) {
				dispatcher = request.getRequestDispatcher("ServletThreadSearchList");
			} else {
				dispatcher = request.getRequestDispatcher("ServletThreadCreate");
			}
						
		} else {
			NewThreadInfo newThread = new NewThreadInfo(title, categoryId, comment);
			request.setAttribute("sendNewThreadInfo", newThread);
			
			CategoryListSetPstmt categoryDao = new CategoryListSetPstmt();
			ArrayList<CategoryListInfo> categoryList = categoryDao.findCategoryList();
			request.setAttribute("sendCategoryList", categoryList);
			
			dispatcher = request.getRequestDispatcher("WEB-INF/threadCreateConfirm.jsp");
		}
		
		dispatcher.forward(request, response);
	}

}
