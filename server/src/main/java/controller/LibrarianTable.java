package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.LibrarianDao;

/**
 * Servlet implementation class LibrarianTable
 */
@WebServlet("/LibrarianTable")
public class LibrarianTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianTable() {
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
		int idRequest = Integer.parseInt(request.getParameter("idRequest"));
		if(idRequest == 1) {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String dinhdanh = request.getParameter("dinhdanh");
			int idLibrarian = new LibrarianDao().addLibrarian(username, password, username, dinhdanh);
			if(idLibrarian != -1) {
				response.setStatus(201);
				response.getWriter().println(idLibrarian);
			}
			else response.setStatus(403);	
		}
		if(idRequest == 2){
			String newPassword=request.getParameter("newpassword");
			String dinhdanh=request.getParameter("dinhdanh");
			new LibrarianDao().updatePassword(newPassword, dinhdanh);
		}
	}
}
