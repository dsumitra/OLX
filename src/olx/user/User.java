package olx.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import olx.cart.Cart;
import olx.category.Category;
import olx.classifieds.Classifieds;
import olx.constants.OlxConstants.TableNames;
import olx.reports.Reports;
import olx.user.UserConstants.UserColumnNames;
import dbConnection.DBConnection;

/**
 * @author albuquea
 *
 */
public class User {
	UserDAOImpl userDaoImpl = new UserDAOImpl();
	Classifieds classifieds = new Classifieds();
	Category categories = new Category();
	Cart cart = new Cart();

	Scanner sc = new Scanner(System.in);

	/**
	 * To take input from user to either login or signUp
	 */
	void login() {
		int value;
		try {
			do {
				System.out.println("1. Sign In");
				System.out.println("2. Sign Up");
				System.out.println("\nEnter the option you would like to proceed with: ");
				value = Integer.parseInt(sc.nextLine());
				if (value == 1) {
					signIn();
					break;
				} else if (value == 2) {
					signUp();
					break;
				} else {
					System.out.println("Please select the correct option: ");
				}
			} while (value <= 0 || value > 2);
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input.");
			login();
		}
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
		UserModel user = new UserModel();
		UserLogin u = new UserLogin();
		u.addProfileName(user);
		u.addEmail(user);
		u.addAddress(user);
		u.addPhoneNo(user);
		u.addPassword(user);
		user.setStatus(UserConstants.UserStatus.ACTIVE);
		userDaoImpl.addUser(user);
		displayUserOptions(user);
	}

	/**
	 * @param user. Verifies if the person logging in is admin or user and provide
	 *              the list of menu.
	 */
	public void displayUserOptions(UserModel user) {

		if (user.isAdmin() == true) {
			System.out.println("\nAdmin Menu");
			System.out.println(
					" 1.Delete Users \n 2.Manage categories \n 3.Approve Classifieds \n 4.View Reports \n 5.Settings \n 6.Sign out");

			System.out.println("Please select from the given options: ");
			int option = Integer.parseInt(sc.nextLine());
			if (option == 1) {
				deleteUser(user);
			} else if (option == 2) {
				categories.manageCategories(user);
			} else if (option == 3) {
				classifieds.displayPostedClassifieds();
			} else if (option == 4) {
				Reports report = new Reports();
				report.showReportOptions(user);
			} else if (option == 5) {
				showUserSettings(user);
			} else if (option == 6) {
				signOut();
			} else {
				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}

		} else {
			System.out.println("\nUser Menu");
			System.out
					.println(" 1.Buy \n 2.Sell \n 3.Manage classifieds \n 4.View cart \n 5.Settings \n 6.Sign out \n");
			System.out.println("Please select from the given options: ");
			int option = Integer.parseInt(sc.nextLine());
			if (option == 1) {
				classifieds.buy(user);
			} else if (option == 2) {
				classifieds.sellClassifieds(user);
			} else if (option == 3) {
				classifieds.manageClassifieds(user);
			} else if (option == 4) {

				cart.viewCart(user);
			} else if (option == 5) {
				showUserSettings(user);
			} else if (option == 6) {
				signOut();
			} else {

				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}
		}
		displayUserOptions(user);
	}

	/**
	 * @param user. Provides options and request for an input through scanner class
	 */
	void showUserSettings(UserModel user) {
		try {
			String email = user.getEmail();
			String query = " Select email, first_name, last_name, address, phone, id from " + TableNames.USER
					+ " where " + UserColumnNames.EMAIL + "='" + email + "'";
			ResultSet rs = DBConnection.executeQuery(query);
			int count = 0;
			System.out.println("\nEmail \t\t\t First Name \t Last Name \t  Address \t Phone");
			while (rs.next()) {
				System.out.print(rs.getString("email") + " \t " + rs.getString("first_name") + " \t\t "
						+ rs.getString("last_name") + " \t\t " + rs.getString("address") + " \t "
						+ rs.getString("phone") + " \n ");

				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setFirstName(rs.getString("first_name"));
				user.setPhone(rs.getString("phone"));
				user.setLastName(rs.getString("last_name"));
				user.setId(rs.getInt("id"));
			}
			System.out.println(
					"\nUser Settings Menu:\n 1.Rename username \n 2.Change password \n 3.Update PhoneNumber \n 4.Update Email \n 5.Update Address \n 6.Return to Menu");
			System.out.println("Enter how many values you want to update: ");
			int value = Integer.parseInt(sc.nextLine());
			int temp = value;
			while (temp > count) {
				for (int i = 0; i < value; i++) {
					System.out.println("Enter the Attribute number you want to edit :");
					int attributeId = Integer.parseInt(sc.nextLine());
					if (attributeId == 1) {
						try {
							System.out.println("Enter the updated name: ");
							String newName = sc.nextLine();
							System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
							String option = sc.nextLine();
							if (option.trim().equalsIgnoreCase("y")) {
								user.setFirstName(newName);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
								showUserSettings(user);
							}
						} catch (Exception e) {
							System.out.println("Invalid name provided");
							showUserSettings(user);
						}
					} else if (attributeId == 2) {
						try {
							System.out.println("Enter the password");
							String newPassword = sc.nextLine();
							System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
							String option = sc.nextLine();
							if (option.trim().equalsIgnoreCase("y")) {
								user.setPassword(newPassword);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
								showUserSettings(user);
							}
						} catch (Exception e) {
							System.out.println("Invalid password provided");
							showUserSettings(user);
						}
					} else if (attributeId == 3) {
						try {
							System.out.println("Enter the new Phone Number");
							String newPhoneNo = sc.nextLine();
							System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
							String option = sc.nextLine();
							if (option.trim().equalsIgnoreCase("y")) {
								user.setPhone(newPhoneNo);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
								showUserSettings(user);
							}
						} catch (Exception e) {
							System.out.println("Invalid Phone Number provided");
							showUserSettings(user);
						}
					} else if (attributeId == 4) {
						try {
							System.out.println("Enter the new emailId");
							String newEmail = sc.nextLine();
							System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
							String option = sc.nextLine();
							if (option.trim().equalsIgnoreCase("y")) {
								user.setEmail(newEmail);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
								showUserSettings(user);
							}
						} catch (Exception e) {
							System.out.println("Invalid emailId provided");
							showUserSettings(user);
						}
					} else if (attributeId == 5) {
						try {
							System.out.println("Enter the address");
							String newAddress = sc.nextLine();
							System.out.println("Confirm if you would like to proceed with changes? (Y/N):");
							String option = sc.nextLine();
							if (option.trim().equalsIgnoreCase("y")) {
								user.setFirstName(newAddress);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
								showUserSettings(user);
							}
						} catch (Exception e) {
							System.out.println("Invalid address provided");
							showUserSettings(user);
						}
					} else if (attributeId == 6) {
						displayUserOptions(user);
					}
					userDaoImpl.updateUser(user);
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid input provided");
			showUserSettings(user);
		}
//		showUserSettings(user);
	}

	/**
	 * @param user. Provides a list and takes input through scanner class
	 */
	public void deleteUser(UserModel user) {
		if (user.isAdmin() == true) {
			int number = 0;
			boolean invalidNumber = false;
			ResultSet rs = userDaoImpl.getAllUsers();
			List<Long> userIds = new ArrayList<>();
			userIds.contains(1);
			System.out.println("\nID \t\t EMAIL \t\t FIRST NAME \t\t LAST NAME \t\t PHONE \t\t STATUS");
			try {
				while (rs.next()) {
					long userId = rs.getLong(UserColumnNames.USER_ID);
					String email = rs.getString(UserColumnNames.EMAIL);
					String fname = rs.getString(UserColumnNames.FIRSTNAME);
					String lname = rs.getString(UserColumnNames.LASTNAME);
					String phone = rs.getString(UserColumnNames.PHONE);
					String status = rs.getString(UserColumnNames.STATUS);
					System.out.println(userId + "\t\t" + email + "\t\t" + fname + "\t\t" + lname + "\t\t" + phone
							+ "\t\t" + status);
					userIds.add(userId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			do {
				System.out.println("How many users you want to del?");
				number = Integer.parseInt(sc.nextLine());
				if (number > userIds.size()) {
					invalidNumber = true;
					System.out.println("Invalid number");
				} else {
					invalidNumber = false;
				}
			} while (invalidNumber == true);

			for (int i = 0; i < number; i++) {
				System.out.println("Enter the userId");
				int input = Integer.parseInt(sc.nextLine().trim());
				user.setId(input);
				userDaoImpl.deleteUser(user);
			}
		}
	}

	void signOut() {
		exit(0);
		login();
	}

	/**
	 * @param i
	 * @return null
	 */
	public void exit(int i) {
		return;
	}

	public static void main(String[] args) {
		User u = new User();
		u.login();
	}

}
