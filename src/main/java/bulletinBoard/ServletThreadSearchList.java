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
 * Servlet implementation class ServletThreadSearchList
 */
@WebServlet("/ServletThreadSearchList")
public class ServletThreadSearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletThreadSearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		RequestDispatcher dispatcher;
		if (session != null) {
			int page;
			
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
				page = 1;
			}
			
			ThreadDAO threadDao = new ThreadDAO();
			ArrayList<ThreadDispInfo> threadList = threadDao.searchAndSetList(page);
			request.setAttribute("sendThreadList", threadList);
			
			CategoryListDAO categoryDao = new CategoryListDAO();
			ArrayList<CategoryListInfo> categoryList = categoryDao.findCategoryList();
			request.setAttribute("sendCategoryList", categoryList);
			
			dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");			
		} else {
			dispatcher = request.getRequestDispatcher("ServletLogin");
		}
				
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		String searchWord = request.getParameter("searchWord");
		String match = request.getParameter("match");
//		int categoryId = Integer.parseInt("categoryId");
		
//		if ((searchWord == null || !searchWord.equals("")) && )
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");
		dispatcher.forward(request, response);
	}

}
