/*
	処理内容:	アカウント削除確認サーブレット
			
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
 * Servlet implementation class ServletAccountDelConfirm
 */
@WebServlet("/ServletAccountDelConfirm")
public class ServletAccountDelConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccountDelConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountDelConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		UserInfo user = (UserInfo)session.getAttribute("User");
		UserInfo account = (UserInfo)session.getAttribute("AccountDel");
		if (account == null) {
			doGet(request,response);
			return;
		}
		UserDAO dao = new UserDAO();
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountDelConfirm.jsp");
		if (dao.delAccount(user, account)) {
			request.setAttribute("message", account.getUserId() + "が削除されました。");
			session.removeAttribute("AccountDel");
			dispatcher = request.getRequestDispatcher("ServletAccountSearchList");
		} else {
			request.setAttribute("message", "[システムエラー]処理に失敗しました。");
		}
		dispatcher.forward(request, response);
	}

}
