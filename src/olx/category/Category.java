package olx.category;

import java.sql.*;
import java.util.*;

import olx.user.User;
import olx.user.UserModel;

/**
 * @author dsumitra
 *
 */
/**
 * @author dsumitra
 *
 */
/**
 * @author dsumitra
 *
 */
public class Category {
	CategoryHelper helper;
	CategoryDAOImpl CategoryDAOImpl;
	Scanner sc = new Scanner(System.in);

	public Category() {
		helper = new CategoryHelper();
		CategoryDAOImpl = new CategoryDAOImpl();
	}

	/**
	 * Generating the default categories using a Hash map. But putting the Primary
	 * category as a key and Sub categories as values for each.
	 */
	void addDefaultCategories() {
		Map<String, List<String>> categoryMap = new HashMap<>();
		ArrayList<String> electronicsSubCategory = new ArrayList<String>();
		electronicsSubCategory.add("Mobiles");
		electronicsSubCategory.add("Laptop");
		electronicsSubCategory.add("Home assistant");
		electronicsSubCategory.add("Speakers");

		List<String> vehiclesSubCategory = new ArrayList<>();
		vehiclesSubCategory.add("Cars");
		vehiclesSubCategory.add("Motor-cycles");
		vehiclesSubCategory.add("Bi-cycles");

		List<String> booksSubCategory = new ArrayList<>();
		List<String> musicalSubCategory = new ArrayList<>();
		List<String> lostUnclaimedSubCategory = new ArrayList<>();

		categoryMap.put("Electronics", electronicsSubCategory);
		categoryMap.put("Vehicles", vehiclesSubCategory);
		categoryMap.put("Books", booksSubCategory);
		categoryMap.put("Musical Instruments", musicalSubCategory);
		categoryMap.put("Lost and Unclaimed", lostUnclaimedSubCategory);
		addCategoryMap(categoryMap);

	}

	/**
	 * Generating the custom categories using a Hash Map . It takes input using the
	 * Scanner class for primary categories(Key) and sub categories(Values).
	 */

	void addCustomCategories() {
		Map<String, List<String>> categoryMap = new HashMap<>();
		this.displayCatergoriesTable();
		System.out.println("Enter the number of Categories you want to add to the above existing categories\n");
		int addCategoryNumber = Integer.parseInt(sc.nextLine().trim());
		for (int j = 0; j < addCategoryNumber; j++) {
			System.out.println("Enter category name\n");
			String newCategory = sc.nextLine();
			ArrayList<String> newSubCategoryList = new ArrayList<String>();
			System.out.println("Does " + newCategory + " have any sub categories? (Y/N)\n");
			String hasSubCategory = sc.nextLine().trim();// made changes
			if (hasSubCategory.equalsIgnoreCase("Y")) {
				System.out.println(
						"Enter the number of sub-categories you want to add to " + newCategory + " categories\n");
				int subCategoryNo = Integer.parseInt(sc.nextLine().trim());
				for (int k = 0; k < subCategoryNo; k++) {
					System.out.println("Enter a sub-category name\n");
					String newSubCategory = sc.nextLine();
					newSubCategoryList.add(newSubCategory);
				}
			}
			categoryMap.put(newCategory, newSubCategoryList);
			System.out.println("");
		}
		addCategoryMap(categoryMap);
	}

	/**
	 * @param categoryMap Receives a map from custom category and default category
	 *                    functions to add the categories into the database
	 * 
	 */
	void addCategoryMap(Map<String, List<String>> categoryMap) {
		for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
			String category = entry.getKey();
			List<String> subCategoryList = entry.getValue();
			if (subCategoryList.size() > 0) {
				for (int i = 0; i < subCategoryList.size(); i++) {
					CategoryDAOImpl.addCategory(category, subCategoryList.get(i));
				}
			} else {
				CategoryDAOImpl.addCategory(category, category);
			}
		}
	}

	/**
	 * After displaying the current categories. Takes the user input through Scanner
	 * class for choosing the categories to be deleted.
	 */

	void deleteCategory() {
		ResultSet primaryCategoryRs = CategoryDAOImpl.getPrimaryCategories();
		Map<Integer, String> categories = helper.displayPrimaryCategories(primaryCategoryRs);
		System.out.println("Enter number of categories you want to delete :");
		int delSubCategoryNum = Integer.parseInt(sc.nextLine().trim());
		int delCategoryID;

		for (int j = 0; j < delSubCategoryNum; j++) {
			System.out.println("Select a category number you want to delete");
			delCategoryID = Integer.parseInt(sc.nextLine().trim());
			String selectedCategory = categories.get(delCategoryID);
			deleteSubCategories(selectedCategory);
		}

	}

	/**
	 * @param selectedCategory. checks if the Admin wants to delete the sub
	 *                          categories of the selected Primary Category. Takes
	 *                          input with Scanner calls to select the
	 *                          Sub-categories
	 */
	void deleteSubCategories(String selectedCategory) {
		int delSubCategoryID;
		String delCategory, subCategory;
		ResultSet subCategoryRs = CategoryDAOImpl.getSubCategories(selectedCategory);
		Map<Integer, String> subCategoryMap = displaySubCategories(subCategoryRs);
		if (subCategoryMap.size() == 1) {
			// Only 1 entry of category is present. Hence, deleting the category directly.
			CategoryDAOImpl.deleteCategoryByName(selectedCategory);
			return;
		}
		do {
			System.out.println(
					"Do you want to delete all the Sub-Categories and Classifieds of the above chosen category(Y/N):\n");
			delCategory = sc.nextLine();
		} while (!("Y".equalsIgnoreCase(delCategory) || "N".equalsIgnoreCase(delCategory)));

		if ("Y".equalsIgnoreCase(delCategory)) {
			CategoryDAOImpl.deleteCategoryByName(selectedCategory);
		} else if ("N".equalsIgnoreCase(delCategory)) {
			System.out.println("Enter number of sub-categories you want to delete");
			int delSubCategoryNum = Integer.parseInt(sc.nextLine().trim());
			// deleting sub categories
			for (int j = 0; j < delSubCategoryNum; j++) {
				System.out.println("Select a sub-category number you want to delete");
				delSubCategoryID = Integer.parseInt(sc.nextLine().trim());
				subCategory = subCategoryMap.get(delSubCategoryID);
				CategoryDAOImpl.deleteSubCategory(selectedCategory, subCategory);
			}
		}
	}

	/**
	 * Displays all the categories in the catalog
	 */
	void displayCatergoriesTable() {
		helper.displayCatergoriesTable();
	}

	/**
	 * Displays all the primary categories, Takes user input for choosing which
	 * attribute does the user wants to update. Checks if the user wants to update
	 * only for the primary category/Sub categories or Both
	 */
	void updateCategory() {
		ResultSet primaryCategoryRs = CategoryDAOImpl.getPrimaryCategories();
		Map<Integer, String> categories = helper.displayPrimaryCategories(primaryCategoryRs);
		int updateCategoryAction = 0;
		String selectedCategory = null;
		try {
			do {
				System.out.println("Select the number of categories you want to update: ");
				int updateCategory = Integer.parseInt(sc.nextLine());
				for (int i = 0; i < updateCategory; i++) {
					System.out.println("Select a category number which you want to update: ");
					int updateCategoryID = Integer.parseInt(sc.nextLine());
					selectedCategory = categories.get(updateCategoryID);
					System.out.println("Update options:\n" + "1. Update Primary Category only \n"
							+ "2. Update Sub Category only \n" + "3. Update Primary Category and Sub-Category \n"
							+ "Select the action do you want to perform: ");
					updateCategoryAction = Integer.parseInt(sc.nextLine());
					if (updateCategoryAction == 2) {
						updateSubCategory(selectedCategory);
					} else if (updateCategoryAction == 1) {
						updatePrimaryCategory(selectedCategory);
					} else if (updateCategoryAction == 3) {
						updateSubCategory(selectedCategory);
						updatePrimaryCategory(selectedCategory);
					}
				}
			} while (updateCategoryAction > 3 || updateCategoryAction <= 0);

		} catch (java.util.InputMismatchException e) {
			e.printStackTrace();
			System.out.println("Kindly enter a valid Input");
		}
	}

	/**
	 * @param selectedCategory Updates the selected primary category by taking input
	 *                         using Scanner Class for the new values
	 */

	void updatePrimaryCategory(String selectedCategory) {
		System.out.println("Enter a new name for Primary Category " + selectedCategory + ":\n");
		String updatePrimaryCategory = sc.nextLine();
		CategoryDAOImpl.updatePrimaryCategory(selectedCategory, updatePrimaryCategory);
	}

	/**
	 * @param selectedCategory Updates the sub categories of the selected primary
	 *                         category by taking input using Scanner Class for the
	 *                         new values
	 */

	void updateSubCategory(String selectedCategory) {
		ResultSet subCategoryRs = CategoryDAOImpl.getSubCategories(selectedCategory);
		Map<Integer, String> subCategoryMap = displaySubCategories(subCategoryRs);
		System.out.println("Enter number of sub-categories you want to update: ");
		int delSubCategoryNum = Integer.parseInt(sc.nextLine().trim());
		// updating sub categories
		for (int j = 0; j < delSubCategoryNum; j++) {
			System.out.println("Select a Sub-Category number you want to update: ");
			int UpdateSubCategoryID = Integer.parseInt(sc.nextLine().trim());
			String subCategory = subCategoryMap.get(UpdateSubCategoryID);
			System.out.println("Enter a new name for Sub-Category: " + subCategory);
			String updateSubCategoryName = sc.nextLine();
			CategoryDAOImpl.updateSubcategory(subCategory, updateSubCategoryName, selectedCategory);
		}
	}

	/**
	 * @param rs Displays the result set of the query run for sub categories by
	 *           storing the values in a map
	 * @return map
	 */

	Map<Integer, String> displaySubCategories(ResultSet rs) {
		Map<Integer, String> subCategoryMap = new HashMap<Integer, String>();
		System.out.println("ID \t\t Sub-Category");
		int i = 1;
		try {
			while (rs.next()) {
				int subCategoryID = i++;
				String subCategory = rs.getString("SUB_CATEGORY");
				subCategoryMap.put(subCategoryID, subCategory);
				System.out.println(subCategoryID + "\t\t" + subCategory);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Input");
			displaySubCategories(rs);
		}
		return subCategoryMap;
	}

	/**
	 * @param userModel Gives a view to the Admin for managing the categories. Takes
	 *                  user input using a Scanner class and calls the functions for
	 *                  performing the chose option
	 */
	public void manageCategories(UserModel userModel) {
		int option = 0;
		while (option != 5) {
			System.out.println(
					"\nCategory Options:\n 1.Add Category \n 2.Delete Category \n 3.Update Category \n 4.View All Catgeories \n 5.Exit Menu");
			System.out.println("Enter the action you want to perform: ");
			option = Integer.parseInt(sc.nextLine().trim());
			if (option == 1) {
				addCustomCategories();
			} else if (option == 2) {
				deleteCategory();
			} else if (option == 3) {
				updateCategory();
			} else if (option == 4) {
				displayCatergoriesTable();
			} else if (option == 5) {
				User user = new User();
				user.displayUserOptions(userModel);
			}
		}
	}

}