package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDao;
import dao.MemberDao;

/**
 * Servlet implementation class HandleMemberTable
 */
@WebServlet("/HandleMemberTable")
public class HandleMemberTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleMemberTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idRequest = Integer.parseInt(request.getParameter("idRequest"));
		if(idRequest == 2) {
			String dinhdanh = request.getParameter("dinhdanh");
			String memberName = request.getParameter("memberName");
			int idLibrarian = Integer.parseInt(request.getParameter("memberName"));
			int idMember = new MemberDao().addMember(dinhdanh, memberName, idLibrarian);
			if(idMember != -1) {
				response.setStatus(201);
				response.getWriter().println(""+idMember);
			}
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(Integer.parseInt(req.getParameter("idRequest")) == 1){ // delete member
			int idmember=Integer.parseInt(req.getParameter("idmember"));
			new MemberDao().deleteMember(idmember);
			resp.getWriter().print("OK");
		}
	}

}
