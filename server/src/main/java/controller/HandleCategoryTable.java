package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDao;
import dao.CategoryDao;

/**
 * Servlet implementation class HandleCategoryTable
 */
@WebServlet("/HandleCategoryTable")
public class HandleCategoryTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleCategoryTable() {
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
		if(idRequest == 2) {
			String categoryCode = request.getParameter("categoryCode");
			String categoryName = request.getParameter("categoryName");
			int idLibrarian = Integer.parseInt(request.getParameter("idLibrarian"));
			int idCategory = new CategoryDao().addCategory(categoryCode, categoryName, idLibrarian);
			if(idCategory != -1) {
				response.getWriter().println(""+idCategory);
			}
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(Integer.parseInt(req.getParameter("idRequest")) == 1){ // delete category
			int idCategory=Integer.parseInt(req.getParameter("idCategory"));
			new CategoryDao().deleteCategory(idCategory);
			resp.getWriter().print("OK");
		}
	}

}
