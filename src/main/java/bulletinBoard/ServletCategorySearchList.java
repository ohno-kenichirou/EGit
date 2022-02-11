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
		
		CategoryListSetPstmt categoryDao = new CategoryListSetPstmt();
		ArrayList<CategoryListInfo> categoryList = categoryDao.findCategoryList();
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
			CategoryListSetPstmt categoryDao = new CategoryListSetPstmt();
			ArrayList<CategoryListInfo> categoryList = categoryDao.findCategoryList();
			request.setAttribute("sendCategoryList", categoryList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categorySearchList.jsp");
			dispatcher.forward(request, response);
		} else {
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			String categoryName = request.getParameter("categoryName");
			String categoryKana = request.getParameter("categoryKana");
			if (btn.equals("modify")) {
				CategoryModifyInfo info = new CategoryModifyInfo(categoryId, categoryName, categoryKana);
				session.setAttribute("CategoryModify", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryModify");
			} else if (btn.equals("delete")) {
				CategoryDelInfo info = new CategoryDelInfo(categoryId, categoryName, categoryKana);
				session.setAttribute("CategoryDel", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryDelConfirm");
			}
		}
		
	}

}
