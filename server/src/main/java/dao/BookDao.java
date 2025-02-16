package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDao{
	public void deleteBook(int bookId){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			String execute = "DELETE FROM book WHERE id= "+bookId;
			statement.executeUpdate(execute);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int addBook(String bookCode, String bookName, int price, int idCategory, int idLibrarian){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO book (masach,namesach,giathue,idLoaisach,idthuthu) VALUES (?,?,?,?,?)");
			preparedStatement.setString(1,bookCode);
			preparedStatement.setString(2,bookName);
			preparedStatement.setInt(3, price);
			preparedStatement.setInt(4, idCategory);
			preparedStatement.setInt(5, idLibrarian);
			preparedStatement.executeUpdate();
			/// thuc hien tiep truy van lay ra id
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from book where idthuthu="+idLibrarian+" and masach='"+bookCode+"'");
			if(resuft2.next())
			{
				return resuft2.getInt("id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
