/*
	処理内容:	アカウント登録確認サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/14(月)
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
 * Servlet implementation class ServletAccountRegisterConfirm
 */
@WebServlet("/ServletAccountRegisterConfirm")
public class ServletAccountRegisterConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccountRegisterConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountRegisterConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		UserInfo account = (UserInfo)session.getAttribute("AccountRegister");
		UserInfo user = (UserInfo)session.getAttribute("User");
		UserDAO dao = new UserDAO();
		if (dao.registerAccount(user, account)) {
			request.setAttribute("message", "新規アカウントを登録しました。");
			session.removeAttribute("AccountRegister");
			response.sendRedirect("ServletAccountSearchList");
		} else {
			request.setAttribute("message", "[システムエラー]処理に失敗しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountRegisterConfirm.jsp");
			dispatcher.forward(request, response);
		}
	}

}
