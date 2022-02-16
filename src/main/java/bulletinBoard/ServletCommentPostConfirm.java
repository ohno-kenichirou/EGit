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
 * Servlet implementation class ServletCommentPostConfirm
 */
@WebServlet("/ServletCommentPostConfirm")
public class ServletCommentPostConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCommentPostConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentPostConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int threadId = Integer.parseInt(request.getParameter("threadId"));
		String comment = request.getParameter("comment");
		String newComment = request.getParameter("newComment");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentPostConfirm.jsp");
		HttpSession session = request.getSession(false);
		
		NewCommentInfo commentInfo = new NewCommentInfo(threadId, comment);
		
		CommentDAO commentDao = new CommentDAO();
		
		if (newComment != null && newComment.equals("yes")) {
			UserInfo user = (UserInfo)session.getAttribute("User");
			if (commentDao.postComment(user, commentInfo)) {
				dispatcher = request.getRequestDispatcher("ServletThreadDetail");
				request.setAttribute("threadId", threadId);
				ArrayList<CommentInfo> commentList = commentDao.searchAndSetList(threadId);
				session.setAttribute("CommentList", commentList);
				session.removeAttribute("NewCommentInfo");
				if (commentList.size() >= 50) {
					request.setAttribute("sendCommentMessage", "コメント数が50件以上のため、コメントすることができません");
				}	
			}
						
		} else {
			session.setAttribute("NewCommentInfo", commentInfo);
		}
					
		dispatcher.forward(request, response);
	}

}
