package olx.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import olx.category.CategoryConstants.CategoryColumnNames;
import olx.classifieds.ClassifiedDAOImpl;
import olx.classifieds.ClassifiedsConstants;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.user.User;
import olx.user.UserModel;

/**
 * @author dsumitra
 *
 */
public class Reports {
	Scanner sc = new Scanner(System.in);
	ClassifiedDAOImpl classifiedDao;

	public Reports() {
		classifiedDao = new ClassifiedDAOImpl();
	}

	public void showReportOptions(UserModel user) {
		int selectedOption = 0;
		while (selectedOption != 5) {
			System.out.println("\nReport Types: ");
			System.out.println(" 1.Category-Classifieds Report\n 2.User-Classifieds Report\n "
					+ "3.Classified Status Report \n 4.Exit Menu");
			do {
				System.out.println("Enter a number of a Report type: ");
				try {
					selectedOption = Integer.parseInt(sc.nextLine().trim());
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					showReportOptions(user);
				}
			} while (!(selectedOption > 0 && selectedOption <= 5));

			switch (selectedOption) {
			case 1:
				showClassifiedsPerCategoryReport();
				break;

			case 2:
				showClassifiedsPerUserReport();
				break;
			case 3:
				showClassifiedStatusReport();
				break;
			case 4: goBackToUserMenu(user);
				break;
			default:
				showReportOptions(user);
			}
		}

	}

	private void goBackToUserMenu(UserModel userModel) {
		User userView = new User();
		userView.displayUserOptions(userModel);
		
	}

	private void showClassifiedStatusReport() {
		System.out.println("Number of Classifieds by Status");
		System.out.println("STATUS \t\t CLASSIFIED COUNT");
		ResultSet rs = classifiedDao.getClassifiedStatus();
		try {
			while (rs.next()) {
				System.out.println(rs.getString(ClassifiedColumnNames.STATE) + "\t\t" + rs.getString("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void showClassifiedsPerUserReport() {
		System.out.println("Number of Classifieds per User");
		System.out.println("USER ID \t\t CLASSIFIED COUNT");
		ResultSet rs = classifiedDao.getClassifiedsPerUser();
		try {
			while (rs.next()) {
				System.out.println(rs.getString(ClassifiedColumnNames.USER_ID) + "\t\t\t\t" + rs.getString("count"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void showClassifiedsPerCategoryReport() {
		System.out.println("Number of Classifieds per Category");
		System.out.println("CATEGORY \t\t CLASSIFIES COUNT");
		ResultSet rs = classifiedDao.getClassifiedsPerCategory();
		try {
			while (rs.next()) {
				System.out.println(rs.getString(CategoryColumnNames.PRIMARY_CATEGORY) + "\t\t\t\t"
						+ rs.getString("Classifieds_count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
