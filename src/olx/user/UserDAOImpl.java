package olx.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.constants.OlxConstants.TableNames;
import olx.user.UserConstants.UserColumnNames;
import dbConnection.DBConnection;

public class UserDAOImpl implements IUserDAO {

	@Override
	public void deleteUser(UserModel userModel) {
		// TODO Auto-generated method stub
		String query = "delete from users where user_id  =" + userModel.getId();
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully deleted the user : " + userModel.getId());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(UserModel userModel) {
		// TODO Auto-generated method stub
		String query = "Update " + TableNames.USER + " set " + UserColumnNames.EMAIL + " ='" + userModel.getEmail()
				+ "' , " + UserColumnNames.PHONE + " ='" + userModel.getPhone() + "', " + UserColumnNames.FIRSTNAME
				+ " = '" + userModel.getFirstName() + "' where " + UserColumnNames.USER_ID + " = " + userModel.getId()
				+ "";
		System.out.println(query);
		System.out.println(userModel.toString());

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
		// TODO Auto-generated method stub
		String query = "insert into users(" + UserColumnNames.EMAIL + "," + UserColumnNames.PASSWORD + ","
				+ UserColumnNames.IS_ADMIN + "," + UserColumnNames.FIRSTNAME + "," + UserColumnNames.LASTNAME + ","
				+ UserColumnNames.ADDRESS + "," + UserColumnNames.PHONE + ") values ('" + user.getEmail() + "','"
				+ user.getPassword() + "','" + "n" + "','" + user.getFirstName() + "','" + user.getLastName() + "','"
				+ user.getAddress() + "','" + user.getPhone() + "')";
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully added user: " + user.getFirstName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet selectAllUser(int userID) {
		// TODO Auto-generated method stub
//		userID=0;
		String query;
		if (userID != 0) {
			// TODO: to use UserID in the query
			query = "Select * from " + TableNames.USER + " where " + UserColumnNames.USER_ID + "=" + 0;
		} else {
			query = "select * from " + TableNames.USER;
		}
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(UserColumnNames.USER_ID));
			}

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
				int userID = rs.getInt(UserColumnNames.USER_ID);
				String firstName = rs.getString(UserColumnNames.FIRSTNAME);
				String lastName = rs.getString(UserColumnNames.LASTNAME);
				String phone = rs.getString(UserColumnNames.PHONE);
				String emailID = rs.getString(UserColumnNames.EMAIL);
				boolean admin = rs.getString(UserColumnNames.IS_ADMIN).equalsIgnoreCase("y") ? true : false;
				String address = rs.getString(UserColumnNames.ADDRESS);
				user = new UserModel(userID, firstName, lastName, phone, emailID, address, admin);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
