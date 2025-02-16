package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDao;
import dao.LoanSlipDao;

/**
 * Servlet implementation class HandleLoanSlipTable
 */
@WebServlet("/HandleLoanSlipTable")
public class HandleLoanSlipTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleLoanSlipTable() {
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
			String receiptNumber = request.getParameter("receiptNumber");
			int idLibrarian=  Integer.parseInt(request.getParameter("idLibrarian"));
			int idBook = Integer.parseInt(request.getParameter("idBook"));
			int idMember = Integer.parseInt(request.getParameter("idMember"));
			int states = Integer.parseInt(request.getParameter("states"));
			String rentalDate = request.getParameter("rentalDate");
			String deadline = request.getParameter("deadline");
			int idLoanSlip = new LoanSlipDao().addLoanSlip(receiptNumber, idLibrarian, idBook, idMember, states, rentalDate, deadline);
			if(idLoanSlip != -1) {
				response.setStatus(201);
				response.getWriter().println(""+idLoanSlip);
			}
		}
		if(idRequest == 3) {
			// update states
			int idLibrarian=Integer.parseInt(request.getParameter("idLibrarian"));
			String receiptNumber=request.getParameter("receiptNumber");
			int states=Integer.parseInt(request.getParameter("states"));
			new LoanSlipDao().updateStates(idLibrarian, receiptNumber, states);
		}
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(Integer.parseInt(req.getParameter("idRequest")) == 1){ // delete book
			String receiptNumber= req.getParameter("receiptNumber");
			int idLibrarian= Integer.parseInt(req.getParameter("idLibrarian"));
			new LoanSlipDao().deleteLoanSlip(idLibrarian,receiptNumber);
			resp.getWriter().print("OK");
		}
	}

}
