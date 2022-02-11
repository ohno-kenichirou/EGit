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
 * Servlet implementation class ServletCommentDelConfirm
 */
@WebServlet("/ServletCommentDelConfirm")
public class ServletCommentDelConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCommentDelConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentDelConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		int threadId = Integer.parseInt(request.getParameter("threadId"));
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		String delete = request.getParameter("delete");
		
		CommentDAO commentDao = new CommentDAO();
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentDelConfirm.jsp");
		
		if (delete != null && delete.equals("yes")) {
			request.setAttribute("sendDeleteMessage", commentDao.deleteComment(threadId, commentId));
			request.setAttribute("sendCommentList", commentDao.searchAndSetList(threadId));
			request.setAttribute("sendThreadInfo", new ThreadDAO().threadDisp(threadId));
			dispatcher = request.getRequestDispatcher("WEB-INF/threadDetail.jsp");
			
		} else {
			request.setAttribute("sendDeleteCommentInfo", commentDao.deleteCommentDisp(threadId, commentId));
		}
		
		dispatcher.forward(request, response);
		
	} 

}
