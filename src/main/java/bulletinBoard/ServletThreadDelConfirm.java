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
 * Servlet implementation class ServletThreadDelConfirm
 */
@WebServlet("/ServletThreadDelConfirm")
public class ServletThreadDelConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletThreadDelConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		UserInfo user = (UserInfo)session.getAttribute("User");
		
		RequestDispatcher dispatcher;
		if (user == null || session == null) {
			dispatcher = request.getRequestDispatcher("ServletLogin");
		} else {
			dispatcher = request.getRequestDispatcher("ServletThreadSearchList");
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		int threadId = Integer.parseInt(request.getParameter("threadId"));
		String delete = request.getParameter("delete");
		
		RequestDispatcher dispatcher;
		
		if (delete != null && delete.equals("execution")) {
			
			ThreadDAO threadDao = new ThreadDAO();
			threadDao.deleteThread(threadId);
			request.setAttribute("sendMessage", "スレッドが削除されました");
			
			dispatcher = request.getRequestDispatcher("ServletThreadSearchList");
		} else {
			ThreadDAO threadDao = new ThreadDAO();
			request.setAttribute("sendThreadInfo", threadDao.threadDisp(threadId));
			
			CommentDAO commentDao = new CommentDAO();
			ArrayList<CommentInfo> commentList = commentDao.searchAndSetList(threadId);
			request.setAttribute("sendCommentList", commentList);			
			
			dispatcher = request.getRequestDispatcher("WEB-INF/threadDelConfirm.jsp");	
		}
		dispatcher.forward(request, response);
	}

}
