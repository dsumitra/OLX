package olx.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import olx.classifieds.ClassifiedDAOImpl;
import olx.classifieds.ClassifiedsConstants;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;

public class Reports {
	Scanner sc = new Scanner(System.in);
	ClassifiedDAOImpl classifiedDao;

	Reports() {
		classifiedDao = new ClassifiedDAOImpl();
	}

	void showReportOptions() {
		int selectedOption = 0;
		while (selectedOption != 5) {
			System.out.println("Report Types: ");
			System.out.println(" 1.Category-Classifieds Report\n 2.User-Classifieds Report\n "
					+ "3.Classified Status Report\n 4.Sale Report\n 5.Exit Menu");
			do {
				System.out.println("Enter a number of a Report type: ");
				try {
					selectedOption = Integer.parseInt(sc.nextLine().trim());
				} catch(NumberFormatException e) {
					System.out.println("Invalid input");
					showReportOptions();
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
			case 4:
				showSaleReport();
				break;
			case 5: // TODO go back to user menu
				break;
			default:
				showReportOptions();
			}
		}

	}

	private void showSaleReport() {
		// TODO Implement Sale Report

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
		System.out.println("CATEGORY ID \t\t CLASSIFIED COUNT");
		ResultSet rs = classifiedDao.getClassifiedsPerCategory();
		try {
			while (rs.next()) {
				System.out
						.println(rs.getString(ClassifiedColumnNames.CATEGORY_ID) + "\t\t\t\t" + rs.getString("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
