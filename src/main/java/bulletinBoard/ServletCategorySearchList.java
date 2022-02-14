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
 * Servlet implementation class ServletCategorySearchList
 */
@WebServlet("/ServletCategorySearchList")
public class ServletCategorySearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategorySearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		CategoryListDAO categoryDao = new CategoryListDAO();
		ArrayList<CategoryInfo> categoryList = categoryDao.findCategoryList();
		request.setAttribute("sendCategoryList", categoryList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categorySearchList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		String btn = request.getParameter("btn");
		if (btn.equals("search")) {
			CategoryListDAO categoryDao = new CategoryListDAO();
			String searchName = request.getParameter("searchCategoryWord");
			String selectMatch = request.getParameter("selectMatch");
			ArrayList<CategoryInfo> categoryList = categoryDao.findCategoryList(searchName,selectMatch);
			request.setAttribute("sendCategoryList", categoryList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categorySearchList.jsp");
			dispatcher.forward(request, response);
		} if (btn.equals("add")) {
			response.sendRedirect("ServletCategoryAdd");
		} else {
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			String categoryName = request.getParameter("categoryName");
			String categoryKana = request.getParameter("categoryKana");
			CategoryInfo info = new CategoryInfo(categoryId, categoryName, categoryKana);
			if (btn.equals("modify")) {
				session.setAttribute("CategoryModify", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryModify");
			} else if (btn.equals("delete")) {
				session.setAttribute("CategoryDel", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryDelConfirm");
			}
		}
		
	}

}
