package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDao {
	public void deleteMember(int memberId){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			String execute = "DELETE FROM Member WHERE id= "+memberId;
			statement.executeUpdate(execute);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int addMember(String dinhdanh, String memberName, int idLibrarian){
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Member (dinhdanh,memberName,idLibrarian) VALUES (?,?,?)");
			preparedStatement.setString(1,dinhdanh);
			preparedStatement.setString(2,memberName);
			preparedStatement.setInt(3, idLibrarian);
			preparedStatement.executeUpdate();
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from Member where idLibrarian="+idLibrarian+" and dinhdanh='"+dinhdanh+"'");
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
