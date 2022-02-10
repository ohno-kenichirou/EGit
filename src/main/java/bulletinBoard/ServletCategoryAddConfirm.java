/*
	処理内容:	カテゴリー追加確認サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/07(月)
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
 * Servlet implementation class ServletCategoryAddConfirm
 */
@WebServlet("/ServletCategoryAddConfirm")
public class ServletCategoryAddConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategoryAddConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryAddConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		CategoryAddInfo category = (CategoryAddInfo)session.getAttribute("CategoryAdd");
		UserInfo user = (UserInfo)session.getAttribute("User");
		CategoryDAO dao = new CategoryAdd(user, category);
		if (dao.addCategory()) {
			request.setAttribute("message", "カテゴリーが追加されました。");
			session.removeAttribute("CategoryAdd");
			response.sendRedirect("ServletCategorySearchList");
		} else {
			request.setAttribute("message", "[システムエラー]処理に失敗しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryAddConfirm.jsp");
			dispatcher.forward(request, response);
		}
	}

}
