package olx.user;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
//import olx.classifieds.*;

import olx.classifieds.Classifieds;
import olx.constants.OlxConstants.TableNames;
import olx.user.UserConstants.UserColumnNames;
import dbConnection.DBConnection;

public class User {
	UserDAOImpl userDaoImpl = new UserDAOImpl();
	Classifieds classifieds = new Classifieds();
//	Category categories=new Categories();
	Scanner sc = new Scanner(System.in);

	void login() {
		int value;
		do {
			System.out.println("Enter the option you would like to proceed with");
			System.out.println("1. Sign In");
			System.out.println("2. Sign Up");
			value = sc.nextInt();
			if (value == 1) {
				signIn();
				break;
			} else if (value == 2) {
				signUp();
				break;
			} else {
				System.out.println("Please select the correct option");
			}
		} while (value <= 0 || value > 2);
	}

	void signIn() {
		UserLogin u = new UserLogin();
		String email = u.verifyEmail();
		u.verifyPassword(email);
		System.out.println("Login Successful");
		UserModel user = userDaoImpl.getUser(email);
		displayUserOptions(user);
	}

	void signUp() {
		UserModel user=new UserModel();
		UserLogin u = new UserLogin();
		u.addProfileName(user);
		u.addEmail(user);
		u.addAddress(user);
		u.addPhoneNo(user);
		u.addPassword(user);
		userDaoImpl.addUser(user);
		displayUserOptions(user);
	}

	void displayUserOptions(UserModel user) {
		System.out.println("Menu");
		System.out.println("Please select from the given options");
		if (user.isAdmin()==true) {
			System.out.println(" 1.Delete Users \n 2.Manage categories \n 3. Settings");
			int option = sc.nextInt();
			if (option == 1) {
				deleteUser(user);
			} else if (option == 2) {
//				categories.manageCategories(user);
			} else if (option == 3) {
				showUserSettings(user);
			} else {
				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}

		} else {
			System.out.println(
					" 1.Buy \n 2.Sell \n 3.Order History \n 4.Manage classifieds \n 5.View cart \n 6. Settings");
			int option = sc.nextInt();
			if (option == 1) {
				classifieds.buy(user);

			} else if (option == 2) {

//				classifieds.sellClassifieds(user);

			} else if (option == 3) {

//				classifieds.OrderHistory(user);

			} else if (option == 4) {

				classifieds.manageClassifieds(user);
			
			} else if (option == 5) {
				// TODO: View cart

			} else if (option == 6) {

				showUserSettings(user);

			} else {

				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}
		}
	}

	void showUserSettings(UserModel user) {
		try {
			String email=user.getEmail();
			String query = " Select email, first_name, last_name, address, phone, user_id from " + TableNames.USER
					+ " where " + UserColumnNames.EMAIL + "='" + email + "'";
			ResultSet rs = DBConnection.executeQuery(query);
			int count = 0;
			System.out.println("Email \t\t First Name \t Last Name \t  Address \t Phone");
			while (rs.next()) {
				System.out.print(rs.getString("email") + " \t " + rs.getString("first_name") + " \t\t "
						+ rs.getString("last_name") + " \t\t " + rs.getString("address") + " \t "
						+ rs.getString("phone") + " \n ");
				
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("first_name"));
				user.setPhone(rs.getString("phone"));
				user.setLastName(rs.getString("last_name"));
				user.setId(rs.getInt("user_id"));
			}
			System.out.println(
					"1.Rename username \n 2.Change password \n 3.Update PhoneNumber \n 4.Update Email \n 5.Update Address");
			System.out.println("Enter How many values you want to update");
			int value = sc.nextInt();
			int temp = value;
			while (temp > count) {
				for (int i = 0; i < value; i++) {
					System.out.println("Enter the Attribute number you want to edit :");
					int attributeId = sc.nextInt();
					if (attributeId == 4) {
						System.out.println("Enter the new emailId");
						sc.nextLine();
						String newEmail = sc.nextLine();
						System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
						String option = sc.nextLine();
						if (option.trim().equalsIgnoreCase("y")) {
							user.setEmail(newEmail);
							System.out.println("Update Successful!");
							count++;
						} else {
							System.out.println("No updates made");
						}
					} else if (attributeId == 3) {
						System.out.println("Enter the new PhoneNumber");
						sc.nextLine();
						String newPhoneNo = sc.nextLine();
						System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
						String option = sc.nextLine();
						if (option.trim().equalsIgnoreCase("y")) {
							user.setPhone(newPhoneNo);
							System.out.println("Update Successful!");
							count++;
						} else {
							System.out.println("No updates made");
						}
					} else if (attributeId == 1) {
						System.out.println("Enter the updated name");
						sc.nextLine();
						String newName = sc.nextLine();
						System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
						String option = sc.nextLine();
						if (option.trim().equalsIgnoreCase("y")) {
							user.setFirstName(newName);
							System.out.println("Update Successful!");
							count++;
						} else {
							System.out.println("No updates made");
						}
					} else if (attributeId == 5) {
						System.out.println("Enter the address");
						sc.nextLine();
						String newAddress = sc.nextLine();
						System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
						String option = sc.nextLine();
						if (option.trim().equalsIgnoreCase("y")) {
							user.setFirstName(newAddress);
							System.out.println("Update Successful!");
							count++;
						} else {
							System.out.println("No updates made");
						}
					} else if (attributeId == 2) {
						System.out.println("Enter the password");
						sc.nextLine();
						String newPassword = sc.nextLine();
						System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
						String option = sc.nextLine();
						if (option.trim().equalsIgnoreCase("y")) {
							user.setFirstName(newPassword);
							System.out.println("Update Successful!");
							count++;
						} else {
							System.out.println("No updates made");
						}
					}
					userDaoImpl.updateUser(user);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception caught");
			e.printStackTrace();
		}
	}

	public void deleteUser(UserModel user) {
		if (user.isAdmin()==true) {
			ResultSet rs = userDaoImpl.selectAllUser(0);
			System.out.println("How many users you want to del?");
			int number = sc.nextInt();
			for (int i = 0; i < number; i++) {
				System.out.println("Enter the userId");
				int input = sc.nextInt();
				user.setId(input);
				userDaoImpl.deleteUser(user);
				System.out.println("The user that has been deleted is " + user.getId());
			}
		}
	}

}
