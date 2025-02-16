package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoanSlipDao {
	public void deleteLoanSlip(int librarianId, String receiptNumber){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM LoanSlip WHERE idLibrarian="+librarianId+" and receiptNumber='"+receiptNumber+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateStates(int idLibrarian, String receiptNumber, int states) {
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			PreparedStatement preparedStatement =connection.prepareStatement("update loanSlip set states=? where idLibrarian=? and receiptNumber= ?");
			preparedStatement.setInt(1,states);
			preparedStatement.setInt(2,idLibrarian);
			preparedStatement.setString(3,receiptNumber);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int addLoanSlip(String receiptNumber, int idLibrarian, int idBook, int idMember, int states,String rentalDate, String deadline){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO loanSlip(receiptNumber,idLibrarian,idBook,idMember,states,rentalDate,deadline) VALUES (?,?,?,?,?,?,?)");
			preparedStatement.setString(1,receiptNumber);
			preparedStatement.setInt(2,idLibrarian);
			preparedStatement.setInt(3, idBook);
			preparedStatement.setInt(4, idMember);
			preparedStatement.setInt(5,states);
			preparedStatement.setString(6,rentalDate);
			preparedStatement.setString(7,deadline);
			preparedStatement.executeUpdate();
			Statement statement = connection.createStatement();
			ResultSet resultQuery= statement.executeQuery("select * from LoanSlip where idLibrarian="+idLibrarian+" and receiptNumber='"+receiptNumber+"'");
			if(resultQuery.next())
			{
				return resultQuery.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
