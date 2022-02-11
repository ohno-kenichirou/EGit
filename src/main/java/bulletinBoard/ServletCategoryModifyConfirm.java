/*
	処理内容:	カテゴリー修正確認サーブレット
			
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
 * Servlet implementation class ServletCategoryModifyConfirm
 */
@WebServlet("/ServletCategoryModifyConfirm")
public class ServletCategoryModifyConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategoryModifyConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryModifyConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		CategoryModifyInfo category = (CategoryModifyInfo)session.getAttribute("CategoryModify");
		UserInfo user = (UserInfo)session.getAttribute("User");
		CategoryDAO dao = new CategoryModifySetPstmt(user, category);
		if (dao.modifyCategory()) {
			request.setAttribute("message", "カテゴリーが修正されました。");
			session.removeAttribute("CategoryModify");
			response.sendRedirect("ServletCategorySearchList");
		} else {
			request.setAttribute("message", "[システムエラー]処理に失敗しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryModifyConfirm.jsp");
			dispatcher.forward(request, response);
		}
	}

}
