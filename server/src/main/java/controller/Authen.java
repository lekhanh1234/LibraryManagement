package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.AdminDao;
import dao.BookDao;
import dao.LibrarianDao;

@WebServlet("/Authen")
public class Authen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idRequest = Integer.parseInt(request.getParameter("idRequest"));
		if(idRequest == 1) { // su li librarian login
			String userName = request.getParameter("usedname");
			String password = request.getParameter("password");
		    new LibrarianDao().checkLogin(userName, password,request, response);
		}
		if(idRequest == 2) { // su li librarian admin
			String userName= request.getParameter("username");
			String password= request.getParameter("password");
		    new AdminDao().checkLogin(userName, password,request, response);
			
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
