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

import org.apache.catalina.Session;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			session = request.getSession();
			session.setAttribute("User", null);
			session.setMaxInactiveInterval(60 * 60 * 24);
		} else {
			session.removeAttribute("ThreadSearchInfo");
			session.removeAttribute("CategorySearchInfo");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
		
		if (email == null || pass == null) {
			request.setAttribute("message", "Emailまたはパスワードを入力して下さい");
		} else {
			TryUserLogin tryLogin = new TryUserLogin(email, pass);
			UserDAO userDao = new UserDAO();
			
			switch (userDao.findUser(tryLogin)) {
				case 0:
					dispatcher = request.getRequestDispatcher("ServletThreadSearchList");
					HttpSession session = request.getSession(false);
					session.setAttribute("User", userDao.getUserInfo(tryLogin));
					ArrayList<ThreadDispInfo> threadList = null;
					ThreadDAO threadDao = new ThreadDAO();
					threadList = threadDao.searchAndSetList(1);
					session.setAttribute("ThreadList", threadList);
					break;
				case 1:
					request.setAttribute("message", "このアカウントはロックされています");
					break;
				case 2:
					request.setAttribute("message", "Emailまたはパスワードが違います");
					break;
			}
			
		}
		
		dispatcher.forward(request, response);
	}

}
