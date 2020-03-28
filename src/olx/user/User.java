package olx.user;

import java.sql.ResultSet;
import java.util.Scanner;

import olx.cart.Cart;
import olx.category.Category;
import olx.classifieds.Classifieds;
import olx.constants.OlxConstants.TableNames;
import olx.reports.Reports;
import olx.user.UserConstants.UserColumnNames;
import dbConnection.DBConnection;

public class User {
	UserDAOImpl userDaoImpl = new UserDAOImpl();
	Classifieds classifieds = new Classifieds();
	Category categories = new Category();
	Cart c = new Cart();
	Scanner sc = new Scanner(System.in);

	void login() {
		int value;
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

	public void displayUserOptions(UserModel user) {

		if (user.isAdmin() == true) {
			System.out.println("\nAdmin Menu");
			System.out.println(" 1.Delete Users \n 2.Manage categories \n 3.View Reports \n 4.Settings \n 5.Sign out");
			System.out.println("Please select from the given options: ");
			int option = Integer.parseInt(sc.nextLine());
			if (option == 1) {
				deleteUser(user);
			} else if (option == 2) {
				categories.manageCategories(user);
			} else if (option == 3) {
				Reports report = new Reports();
				report.showReportOptions(user);
			} else if (option == 4) {
				showUserSettings(user);
			} else if (option == 5) {
				// TODO implement sign out
//				signOut();
			} else {
				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}

		} else {
			System.out.println("\nUser Menu");
			System.out.println(" 1.Buy \n 2.Sell \n 3.Manage classifieds \n 4.View cart \n 5.Settings \n 6.Sign out \n");
			System.out.println("Please select from the given options: ");
			int option = Integer.parseInt(sc.nextLine());
			if (option == 1) {
				classifieds.buy(user);
			} else if (option == 2) {
				classifieds.sellClassifieds(user);
			} else if (option == 3) {
				classifieds.manageClassifieds(user);
			} else if (option == 4) {
//				Long uid=user.getId();
//				c.viewBuyerBids();
			} else if (option == 5) {
				showUserSettings(user);
			} else if (option == 6) {
				// TODO implement signout
			} else {

				System.out.println("Incorrect options provided");
				displayUserOptions(user);
			}
		}
	}

	void showUserSettings(UserModel user) {
		try {
			String email = user.getEmail();
			String query = " Select email, first_name, last_name, address, phone, id from " + TableNames.USER
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
				user.setId(rs.getInt("id"));
			}
			System.out.println(
					"1.Rename username \n 2.Change password \n 3.Update PhoneNumber \n 4.Update Email \n 5.Update Address \n 6.Return to Menu");
			System.out.println("Enter How many values you want to update");
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
								user.setFirstName(newPassword);
								System.out.println("Update Successful!");
								count++;
							} else {
								System.out.println("No updates made");
							}
						} catch (Exception e) {
							System.out.println("Invalid password provided");
							showUserSettings(user);
						}
					} else if (attributeId == 3) {
						try {
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
						} catch (Exception e) {
							System.out.println("Invalid PhoneNumber provided");
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
							}
						} catch (Exception e) {
							System.out.println("Invalid address provided");
							showUserSettings(user);
						}
					} else if (attributeId==6) {
						displayUserOptions(user);
					}
					userDaoImpl.updateUser(user);
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid input provided");
			showUserSettings(user);
		}
		showUserSettings(user);
	}

	public void deleteUser(UserModel user) {
		if (user.isAdmin() == true) {
			ResultSet rs = userDaoImpl.selectAllUser(0);
			System.out.println("How many users you want to del?");
			int number = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < number; i++) {
				System.out.println("Enter the userId");
				int input = Integer.parseInt(sc.nextLine());
				user.setId(input);
				userDaoImpl.deleteUser(user);
				System.out.println("\nThe user that has been deleted is " + user.getId());
			}
		}
	}

	void signOut() {
		System.exit(0);
		login();
	}

}
