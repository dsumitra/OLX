package olx;

import java.util.*;
import java.sql.*;

public class Authentication {

	public class email {

		Scanner s = new Scanner(System.in);
		int count = 0;
		String emailId;

		void readEmail() {
			try {
				System.out.println("Enter the email");
				emailId = s.nextLine();
				if (emailId.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
					// verify the emailid in the database
					System.out.println("Email address provided is valid format");
				} else {
					System.out.println("Invalid email address provided is invalid format");
					if (count < 3) {
						System.out.println("Please re-enter the email");
						count++;
					} else {
						System.out.println("More than 3 failed attempts. Your account has been locked");
						System.exit(0);
					}
					readEmail();
				}
			} catch (Exception e) {
				System.out.println("Exception has been caught");
			}
		}

		void verifyEmail() {
			try {
				System.out.println("Enter the email");
				emailId = s.nextLine();
				String str = "select email from users where email='" + emailId + "'";
				System.out.println("");
				// Need to add DB connection here to verify the email ID from the DB
//			while (true) {
//				if (db.executeQueryFormat(str).equals(emailId)) {
//					System.out.println("Email address provided is valid");
//					break;
//				} else {
//					System.out.println("Email address provided is invalid");
//					if (count < 3) {
//						System.out.println("Please re-enter the email");
//						count++;
//					} else {
//						System.out.println("More than 3 failed attempts. Your account has been locked");
//						System.exit(0);
//					}
//					verifyEmail();
//				}
//			}
			} catch (Exception e) {
				System.out.println("Exception caught");
			}

		}

		void updateEmail() {
			email e = new email();
			try {
				System.out.println("Please enter your registered email");
				String emailId = s.nextLine();
				String emailId2;
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orc", "amazon",
						"amazon");
				Statement stmt = con.createStatement();
				String str = "select * from users";
				ResultSet rs = stmt.executeQuery(str);
				while (rs.next()) {
					String z = rs.getString("email");
					System.out.println(z);
					if (z.equals(emailId)) {
						System.out.println("Email address provided is valid");
						System.out.println("Please enter the new  email address");
						emailId2 = s.nextLine();
						e.readEmail();
						System.out.println("Please re-enter your new email");
						String emailId3 = s.nextLine();
						if (emailId2.equals(emailId3)) {
							String sql = "Update users set email='" + emailId2 + "' where email='" + emailId + "'";
							System.out.println(1);
							PreparedStatement ps = con.prepareStatement(sql);
							System.out.println(2);
							// ps.setString(2, emailId2);
							// ps.setString(2, emailId);
							System.out.println(3);
							ps.executeUpdate();
						}
					} else {
						System.out.println("Email address provided is invalid");
						if (count < 3) {
							System.out.println("Please re-enter the email");
							count++;
						} else {
							System.out.println("More than 3 failed attempts. Your account has been locked");
							System.exit(0);
						}
						updateEmail();
					}
				}
			} catch (Exception x) {
				System.out.println("Exception caught");
				x.printStackTrace();
			}
		}
	}

	class name {
		Scanner s = new Scanner(System.in);
		String first_name, last_name;

		void profileName() {
			try {
				System.out.println("Enter the first name");
				first_name = s.nextLine();
				System.out.println("Enter the last name");
				last_name = s.nextLine();
			} catch (Exception e) {
				System.out.println("Exception has been caught");
			}
		}
	}

	class password {
		// class should have capital as the first letter
		Scanner s = new Scanner(System.in);
		int count = 0;

		void readPassword() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orc", "amazon",
						"amazon");
				Statement stmt = con.createStatement();
				System.out.println("Enter the password");
				String password = s.nextLine();
// should be 6 letters and not more than 20 letter & have atleast 1 number,special character
				if (password.matches(".*[0-9]{1,}.*") && password.matches(".*[@#$]{1,}.*") && password.length() >= 6
						&& password.length() <= 20) {
					System.out.println("Password entered is valid");
					System.out.println("Login was successful");
				} else {
					System.out.println("Password entered is invalid");
					if (count < 3) {
						System.out.println("Please re-enter the password");
						count++;
					} else {
						System.out.println("More than 3 failed attempts. Your account has been locked");
						System.exit(0);
					}
					readPassword();
				}
			} catch (Exception e) {
				System.out.println("Exception has been caught");
			}
		}

		void verifyPassword() {
			try {
				System.out.println("Enter the Password");
				String password = s.nextLine();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orc", "amazon",
						"amazon");
				Statement stmt = con.createStatement();
				String str = "select * from users";
				ResultSet rs = stmt.executeQuery(str);
				while (rs.next()) {
					String z = rs.getString("password");
					if (z.equals(password)) {
						System.out.println("password provided is valid");
					} else {
						System.out.println("password provided is invalid");
						if (count < 3) {
							System.out.println("Please re-enter the password");
							count++;
						} else {
							System.out.println("More than 3 failed attempts. Your account has been locked");
							System.exit(0);
						}
						verifyPassword();
					}
				}
			} catch (Exception e) {
				System.out.println("Exception caught");
			}
		}

		void updatePassword() {
			// need to verify the values in the DB and then update the password

		}
	}

	class phoneNo {
		Scanner s = new Scanner(System.in);
		String phone_number;

		void validatePhoneNo() {
			try {
				System.out.println("Enter the phoneno");
				phone_number = s.nextLine();
				if (phone_number.matches("\\d{10}"))
					System.out.println("Is valid");
				// validating phone number with -, . or spaces
				else if (phone_number.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
					System.out.println("Is valid");
				// validating phone number with extension length from 3 to 5
				else if (phone_number.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
					System.out.println("Is valid");
				// validating phone number where area code is in braces ()
				else if (phone_number.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
					System.out.println("Is valid");
				// return false if nothing matches the input
				else
					System.out.println("Is valid");

			} catch (Exception e) {
				System.out.println("Exception has been caught in phoneno class");
			}
		}

		void updateName() {

		}

	}
}
