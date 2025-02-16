package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDao {
	public void deleteCategory(int categoryId){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			String execute = "DELETE FROM category WHERE id= "+categoryId;
			statement.executeUpdate(execute);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int addCategory(String categoryCode, String categoryName,int idLibrarian){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO category (categoryCode,categoryName,idLibrarian) VALUES (?, ?, ?)");
			preparedStatement.setString(1,categoryCode);
			preparedStatement.setString(2, categoryName);
			preparedStatement.setInt(3, idLibrarian);
			preparedStatement.executeUpdate();
			/// thuc hien tiep truy van lay ra id
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from category where idLibrarian="+idLibrarian+" and categoryCode='"+categoryCode+"'");
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
