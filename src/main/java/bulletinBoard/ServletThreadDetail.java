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
 * Servlet implementation class ServletThreadDetail
 */
@WebServlet("/ServletThreadDetail")
public class ServletThreadDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletThreadDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		int threadId = Integer.parseInt(request.getParameter("threadInfo"));
		
		ThreadDAO threadDao = new ThreadDAO();
		request.setAttribute("sendThreadInfo", threadDao.threadDisp(threadId));
		
		CommentDAO commentDao = new CommentDAO();
		ArrayList<CommentInfo> commentList = commentDao.searchAndSetList(threadId);
		request.setAttribute("sendCommentList", commentList);
		if (commentList.size() >= 50) {
			request.setAttribute("sendCommentMessage", "コメント数が50件以上のため、コメントすることができません");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/threadDetail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
