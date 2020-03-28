package olx.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedStatus;
import olx.constants.OlxConstants.TableNames;
import olx.user.UserConstants.UserColumnNames;
import olx.user.UserConstants.UserStatus;
import dbConnection.DBConnection;

public class UserDAOImpl implements IUserDAO {

	@Override
	public void deleteUser(UserModel userModel) {
		String query = "delete from users where " + UserColumnNames.USER_ID + "=" + userModel.getId();
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully deleted the user : " + userModel.getId());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(UserModel userModel) {
	
		String query = "Update " + TableNames.USER + " set " + UserColumnNames.EMAIL + " ='" + userModel.getEmail()
				+ "' , " + UserColumnNames.PHONE + " ='" + userModel.getPhone() + "', " + UserColumnNames.FIRSTNAME
				+ " = '" + userModel.getFirstName() + "' where " + UserColumnNames.USER_ID + " = " + userModel.getId()
				+ "";
System.out.println(query);
		// password needs to be added
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully updated the user details: " + userModel.getFirstName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void addUser(UserModel user) {
		String query = "insert into users(" + UserColumnNames.EMAIL + "," + UserColumnNames.PASSWORD + ","
				+ UserColumnNames.IS_ADMIN + "," + UserColumnNames.FIRSTNAME + "," + UserColumnNames.LASTNAME + ","
				+ UserColumnNames.ADDRESS + "," + UserColumnNames.PHONE + "," + UserColumnNames.STATUS + ") values ('"
				+ user.getEmail() + "','" + user.getPassword() + "','" + "N" + "','" + user.getFirstName() + "','"
				+ user.getLastName() + "','" + user.getAddress() + "','" + user.getPhone() + "','" + user.getStatus()
				+ "')";
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully added user: " + user.getFirstName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet getAllUsers() {
		String query = "select * from " + TableNames.USER + " where " + UserColumnNames.IS_ADMIN + " = 'N'";
		ResultSet rs = null;
		try {
			rs = DBConnection.executeQuery(query);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public UserModel getUser(String email) {
		String query = "Select * from " + TableNames.USER + " where " + UserColumnNames.EMAIL + "='" + email + "'";
		UserModel user = null;
		try {
			ResultSet rs = DBConnection.executeQuery(query);
			while (rs.next()) {
				long userID = rs.getInt(UserColumnNames.USER_ID);
				String firstName = rs.getString(UserColumnNames.FIRSTNAME);
				String lastName = rs.getString(UserColumnNames.LASTNAME);
				String phone = rs.getString(UserColumnNames.PHONE);
				String emailID = rs.getString(UserColumnNames.EMAIL);
				boolean admin = rs.getString(UserColumnNames.IS_ADMIN).equalsIgnoreCase("y") ? true : false;
				String address = rs.getString(UserColumnNames.ADDRESS);
				UserStatus status = UserStatus.valueOf(rs.getString(UserColumnNames.STATUS));
				user = new UserModel(userID, firstName, lastName, phone, emailID, address, admin, status);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
