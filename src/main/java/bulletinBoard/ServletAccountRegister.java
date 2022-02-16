/*
	処理内容:	アカウント登録サーブレット
			
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
 * Servlet implementation class ServletAccountRegister
 */
@WebServlet("/ServletAccountRegister")
public class ServletAccountRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccountRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		
		HttpSession session = request.getSession(false);
		UserInfo account = (UserInfo)session.getAttribute("AccountRegister");
		if (account == null) {
			UserInfo user = new UserInfo();
			MakeUserIdDAO dao = new MakeUserIdDAO();
			user.setUserId(dao.getUserId());
			session.setAttribute("AccountRegister",user);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountRegister.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		
		String userId = request.getParameter("userId");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String userName = request.getParameter("userName");
		String birth = request.getParameter("birth");
		String genderId = request.getParameter("genderId");
		String manager = request.getParameter("manager");
				
		String msg = null;
		InputCheck check = new InputCheck(); 
		if (email == null || email.equals("")) {
			msg = "メールアドレスが入力されていません。";
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
		} 
		request.setAttribute("message", msg);
		if (msg != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountRegister.jsp");
			dispatcher.forward(request, response);
		} else {
			HttpSession session = request.getSession(false);
			UserInfo user = (UserInfo)session.getAttribute("AccountRegister");
			user.setUserId(userId);
			user.setUserName(userName);
			user.setPass(pass);
			user.setEmail(email);
			user.setBirth(java.sql.Date.valueOf(birth));
			user.setGenderId(Integer.parseInt(genderId));
			user.setManager(Integer.parseInt(manager));
			session.setAttribute("AccountRegister", user);
			session.setMaxInactiveInterval(60 * 60 * 24);		// セッションの有効期限
			response.sendRedirect("ServletAccountRegisterConfirm");
		}
	}

}
