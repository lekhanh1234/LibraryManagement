package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDao;

/**
 * Servlet implementation class HandleBookTable
 */
@WebServlet("/HandleBookTable")
public class HandleBookTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleBookTable() {
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
			String bookCode = request.getParameter("masach");
			String bookName = request.getParameter("tensach");
			int price = Integer.parseInt(request.getParameter("giathue"));			
			int idCategory = Integer.parseInt(request.getParameter("idloaisach"));
			int idLibrarian = Integer.parseInt(request.getParameter("idthuthu"));
			int idBook = new BookDao().addBook(bookCode, bookName, price, idCategory, idLibrarian);
			if(idBook != -1) {
				response.getWriter().println(""+idBook);
			}
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(Integer.parseInt(req.getParameter("idRequest")) == 1){ // delete book
			int idBook=Integer.parseInt(req.getParameter("idbook"));
			new BookDao().deleteBook(idBook);
			resp.getWriter().print("OK");
		}
	}
}
