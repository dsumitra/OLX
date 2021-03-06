package olx.user;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

import dbConnection.DBConnection;
import olx.user.UserConstants.UserColumnNames;

/**
 * @author albuquea
 *
 */
public class UserLogin {
	UserModel user;
	User u = new User();
	UserDAOImpl userDaoImpl = new UserDAOImpl();
	Scanner s = new Scanner(System.in);
	int count = 0;
	String emailId;
	String z;

	void UserLogin() {
		this.user = user;
	}

	/**
	 * Takes email Id using Scanner class and verifies if its present in the
	 * database.
	 * 
	 * @return emailId
	 */
	HashMap<String, String> verifyEmail() {

		HashMap<String, String> hm = null;
		try {
			System.out.println("Enter the email");
			emailId = s.nextLine().trim();
			int count = 0;
			String query = "select email from users";
			ResultSet rs = DBConnection.executeQuery(query);
			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {
				z = rs.getString("email");
				list.add(z);
			}
			for (String element : list) {
				if (element.contains(emailId)) {
					count++;
				}
			}
			hm = new HashMap<String, String>();
			hm.put("email", emailId);
			hm.put("count", Integer.toString(count));

//			if(count == 0) {
//				System.out.println("Email ID provided is incorrect");
//				verifyEmail();
//			}
			
		} catch (Exception e) {
			verifyEmail();
		}
		return hm;
	}

	/**
	 * @param email. Takes password using Scanner class and verifies if its present
	 *               in the database
	 */
	void verifyPassword(String email) {
		try {
			String z = null;
			System.out.println("Enter the password: ");
			String password = s.nextLine().trim();
			int count = 0;
			String query = "Select password from users where " + UserColumnNames.EMAIL + " = '" + email + "'";
			ResultSet rs = DBConnection.executeQuery(query);
			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {
				z = rs.getString("password");
				list.add(z);
			}
			for (String element : list) {
				if (element.contains(password)) {
					count++;
				}
			}
			if (count == 0) {
				System.out.println("Password provided is incorrect");
				verifyPassword(email);
			}
			
		} catch (Exception e) {
			System.out.println("Please re-enter the password");
			verifyPassword(email);
		}
	}

	/**
	 * @param user. Takes email Id using Scanner class and verifies if the email
	 *              entered is in the correct format.
	 */
	void addEmail(UserModel user) {
		try {
			HashMap<String, String> hm = verifyEmail();
			int count = Integer.parseInt(hm.get("count"));
			String emailId = hm.get("email");

			if (count == 1) {
				System.out.println("Email ID provided is already existing, please proceed with Sign-In");
				u.signIn();
			} else {
				if (emailId.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
					user.setEmail(emailId);

				} else {
					System.out.println("Invalid email address provided is invalid format");
					if (count < 3) {
						System.out.println("Please re-enter the email");
						count++;
						addEmail(user);
					} else {
						System.out.println("More than 3 failed attempts. Your account has been locked");
						System.exit(0);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid email");
			addEmail(user);
		}
	}

	/**
	 * @param user Takes first name and last name using Scanner class.
	 */
	void addProfileName(UserModel user) {
		String first_name, last_name;
		try {
			System.out.println("Enter the first name: ");
			first_name = s.nextLine().trim();
			user.setFirstName(first_name);
			System.out.println("Enter the last name: ");
			last_name = s.nextLine().trim();
			user.setLastName(last_name);

		} catch (Exception e) {
			System.out.println("Invalid inputs.");
			addProfileName(user);
		}
	}

	/**
	 * @param user Takes password using Scanner class
	 */
	void addPassword(UserModel user) {
		try {
			System.out.println("Enter the Password: ");
			String password = s.nextLine().trim();
			user.setPassword(password);
		} catch (Exception e) {
			System.out.println("Invalid Password");
			addPassword(user);
		}
	}

	/**
	 * @param user Takes Phone-number using Scanner class
	 */
	void addPhoneNo(UserModel user) {
		String phone_number;
		try {
			System.out.println("Enter the phone number: ");
			phone_number = s.nextLine().trim();
			if (phone_number.matches("\\d{10}")) {
				user.setPhone(phone_number);
			}
			// validating phone number with -, . or spaces
			else if (phone_number.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
				user.setPhone(phone_number);
			}
			// validating phone number with extension length from 3 to 5
			else if (phone_number.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
				user.setPhone(phone_number);
			}
			// validating phone number where area code is in braces ()
			else if (phone_number.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
				user.setPhone(phone_number);
				// return false if nothing matches the input
			} else {
				System.out.println("Is valid");
				user.setPhone(phone_number);
			}

		} catch (Exception e) {
			System.out.println("Invalid Phone number.");
			addPhoneNo(user);
		}
	}

	/**
	 * Takes parameter as user. Also takes address using Scanner class and sets the
	 * value of Address
	 */
	void addAddress(UserModel user) {
		try {
			String address;
			System.out.println("Enter the address: ");
			address = s.nextLine().trim();
			user.setAddress(address);
		} catch (Exception e) {
			System.out.println("Invalid address");
			addAddress(user);
		}
	}
}
