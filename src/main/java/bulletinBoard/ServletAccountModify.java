/*
	処理内容:	アカウント修正確認サーブレット
			
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
 * Servlet implementation class ServletAccountModify
 */
@WebServlet("/ServletAccountModify")
public class ServletAccountModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccountModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountModify.jsp");
		if (session == null) {
			request.setAttribute("message", "セッションが切れました。");
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("ServletLogin");
		if (session == null) {
			request.setAttribute("message", "セッションが切れました。");
			dispatcher.forward(request, response);
		} else {
			String fromPage = request.getParameter("fromPage");
			if (fromPage == null || !fromPage.equals("accountModify")) {
				doGet(request,response);
				return;
			}
			String userId = request.getParameter("userId");
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			String userName = request.getParameter("userName");
			String birth = request.getParameter("birth");
			String genderId = request.getParameter("genderId");
			String manager = request.getParameter("manager");
			String lift = request.getParameter("lift");
			int errorCount = Integer.parseInt(request.getParameter("errorCount"));
			String msg = null;
			InputCheck check = new InputCheck(); 
			if (email == null || email.equals("")) {
				msg = "メールアドレスが入力されていません。";
			} else if (!check.isCheckEmail(email)) {
				msg = "メールアドレスが正しく入力されていません。";
			} else if (pass == null || pass.equals("")) {
				msg = "パスワードが入力されていません。";
			} else if (!check.checkLogic(pass)) {
				msg = "パスワードは半角英数字のみです。";
			} else if (userName == null || userName.equals("")) {
				msg = "ユーザー名が入力されていません。";
			} else if (birth == null || birth.equals("")) {
				msg = "生年月日が入力されていません。";
			} else if (genderId == null || genderId.equals("")) {
				msg = "性別が選択されていません。";
			} else if (manager == null || manager.equals("")) {
				msg = "管理者権限の有無が選択されていません。";
			} else {
				if (errorCount >= 3) {
					if (lift == null || lift.equals("")) {
						msg = "ロック解除の有無の有無が選択されていません。";
					}
				}
			}
			request.setAttribute("message", msg);
			if (msg != null) {
				dispatcher = request.getRequestDispatcher("WEB-INF/accountModify.jsp");
				dispatcher.forward(request, response);
			} else {
				UserInfo account = (UserInfo)session.getAttribute("AccountModify");
				if (account == null) {
					doGet(request,response);
					return;
				}
				account.setUserId(userId);
				account.setUserName(userName);
				account.setPass(pass);
				account.setEmail(email);
				account.setBirth(java.sql.Date.valueOf(birth));
				account.setGenderId(Integer.parseInt(genderId));
				account.setManager(Integer.parseInt(manager));
				session.setAttribute("AccountModify", account);
				session.setAttribute("lift", lift);
				session.setMaxInactiveInterval(60 * 60 * 24);		// セッションの有効期限
				response.sendRedirect("ServletAccountModifyConfirm");
			}
		}
	}

}
