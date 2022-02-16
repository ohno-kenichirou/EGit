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
			session.removeAttribute("CategorySearchInfo");
			
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
				page = 1;
				session.removeAttribute("ThreadSearchInfo");
			}
			
			ThreadSearchInfo threadSearch = (ThreadSearchInfo)session.getAttribute("ThreadSearchInfo");
			if (threadSearch != null) {
				
				String searchWord = threadSearch.getSearchWord();
				String match = threadSearch.getMatch();
				Integer categoryId = threadSearch.getCategoryId();
				
				if (searchWord != null && match != null && categoryId != null) {
					
					ThreadDAO threadDao = new ThreadDAO();
					ArrayList<ThreadDispInfo> threadList = threadDao.threadSearch(threadSearch, page);
					session.setAttribute("ThreadList", threadList);
					session.setAttribute("ThreadPagination", new ThreadDAO().threadSearchCount(threadSearch));
				} else {
					ThreadDAO threadDao = new ThreadDAO();
					ArrayList<ThreadDispInfo> threadList = threadDao.searchAndSetList(page);
					session.setAttribute("ThreadList", threadList);
					session.setAttribute("ThreadPagination", new ThreadDAO().searchAndSetListCount());
				}
			} else {
				ThreadDAO threadDao = new ThreadDAO();
				ArrayList<ThreadDispInfo> threadList = threadDao.searchAndSetList(page);
				session.setAttribute("ThreadList", threadList);
				session.setAttribute("ThreadPagination", new ThreadDAO().searchAndSetListCount());
			}
			
			CategoryListDAO categoryDao = new CategoryListDAO();
			ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList();
			session.setAttribute("CategoryList", categoryList);
			
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
		
		String logout = request.getParameter("logout");
		
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			page = 1;
		}
		
		RequestDispatcher dispatcher;
		if (logout != null && logout.equals("logoutExecution")) {
			
			ThreadDAO threadDao = new ThreadDAO();
			ArrayList<ThreadDispInfo> threadList = threadDao.searchAndSetList(page);
			session.setAttribute("ThreadList", threadList);
			session.setAttribute("ThreadPagination", new ThreadDAO().searchAndSetListCount());
			
			CategoryListDAO categoryDao = new CategoryListDAO();
			ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList();
			session.setAttribute("CategoryList", categoryList);
			
			dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");		
			
		} else {
			
			String searchWord = request.getParameter("searchWord");
			String match = request.getParameter("match");
			int categoryId;
			
			try {
				categoryId = Integer.parseInt(request.getParameter("categoryId"));
			} catch (NumberFormatException e) {
				categoryId = 0;	
			}
			
			if (match == null) {
				
				ThreadDAO threadDao = new ThreadDAO();
				ArrayList<ThreadDispInfo> threadList = threadDao.searchAndSetList(page);
				session.setAttribute("ThreadList", threadList);
				session.setAttribute("ThreadPagination", new ThreadDAO().searchAndSetListCount());
				
				CategoryListDAO categoryDao = new CategoryListDAO();
				ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList();
				session.setAttribute("CategoryList", categoryList);
				
				dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");	
				
			} else {
				
				if (session != null) {
					ThreadSearchInfo threadSearch = new ThreadSearchInfo(searchWord, match, categoryId);
					session.setAttribute("ThreadSearchInfo", threadSearch);
					
					ThreadDAO threadDao = new ThreadDAO();
					ArrayList<ThreadDispInfo> threadList = threadDao.threadSearch(threadSearch, page);
					session.setAttribute("ThreadList", threadList);
					session.setAttribute("ThreadPagination", new ThreadDAO().threadSearchCount(threadSearch));
					
					CategoryListDAO categoryDao = new CategoryListDAO();
					ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList();
					session.setAttribute("CategoryList", categoryList);
					
					dispatcher = request.getRequestDispatcher("WEB-INF/threadSearchList.jsp");			
				} else {
					dispatcher = request.getRequestDispatcher("ServletLogin");
				}
			}
				
		}
				
		dispatcher.forward(request, response);
	}

}
