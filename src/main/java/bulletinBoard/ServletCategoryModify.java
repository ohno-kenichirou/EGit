/*
	処理内容:	カテゴリー修正サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletCategoryModify
 */
@WebServlet("/ServletCategoryModify")
public class ServletCategoryModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategoryModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryModify.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		
		String categoryName = request.getParameter("categoryName");
		String categoryKana = request.getParameter("categoryKana");
		
		String msg = null;
		if (categoryName == null || categoryName.equals("")) {
			msg = "カテゴリー名が入力されていません。";
		} else if (categoryKana == null || categoryKana.equals("")) {
			msg = "カテゴリー名(カナ)が入力されていません。";
		}
		request.setAttribute("message", msg);
		if (msg != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryModify.jsp");
			dispatcher.forward(request, response);
		} else {
			HttpSession session = request.getSession(false);
			CategoryInfo category = (CategoryInfo)session.getAttribute("CategoryModify");
			category.setCategoryName(categoryName);
			category.setCategoryKana(categoryKana);
			session.setAttribute("CategoryModify", category);
			session.setMaxInactiveInterval(60 * 60 * 24);		// セッションの有効期限
			response.sendRedirect("ServletCategoryModifyConfirm");
		}
	}

}
