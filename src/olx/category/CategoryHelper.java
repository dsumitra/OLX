package olx.category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import olx.category.CategoryConstants.CategoryColumnNames;

/**
 * @author dsumitra
 *
 */
public class CategoryHelper {
	CategoryDAOImpl categoryDAOImpl;

	public CategoryHelper() {
		categoryDAOImpl = new CategoryDAOImpl();
	}

	/**
	 * Displays the category table
	 * 
	 * @return a Map with CategoryId as key and CategoryModel as its value
	 */
	public Map<Integer, CategoryModel> displayCatergoriesTable() {
		return getCategoriesWithDisplay(true);
	}

	/**
	 * @return a Map with CategoryId as key and CategoryModel as its value
	 */
	public Map<Integer, CategoryModel> getAllCategories() {
		return getCategoriesWithDisplay(false);
	}

	private Map<Integer, CategoryModel> getCategoriesWithDisplay(boolean display) {
		Map<Integer, CategoryModel> categoryMap = new HashMap<>();

		ResultSet rs = categoryDAOImpl.getAllCategories();
		if (display)
			System.out.println("ID \t\t\t  Primary \t\t\t Sub_Category");
		try {
			while (rs.next()) {
				int id = rs.getInt(CategoryColumnNames.ID);
				String primaryCategory = rs.getString(CategoryColumnNames.PRIMARY_CATEGORY);
				String subCategory = rs.getString(CategoryColumnNames.SUB_CATEGORY);
				CategoryModel categoryModel = new CategoryModel(id, primaryCategory, subCategory);
				categoryMap.put(id, categoryModel);
				if (display)
					System.out.println(id + "\t\t\t" + primaryCategory + "\t\t\t" + subCategory);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Input");
			getCategoriesWithDisplay(true);
		}
		return categoryMap;
	}
	/**
	 * 
	 * @param Resultset with Distinct Primary categories
	 * @return a Map of primary category id and name
	 */
	public Map<Integer, String> displayPrimaryCategories(ResultSet rs) {
		Map<Integer, String> categories = new HashMap<Integer, String>();
		System.out.println("ID\t\t CATEGORY");
		int i = 1;
		// displaying distinct Categories.
		try {
			while (rs.next()) {
				int id = i++;
				String primaryCategory;
				primaryCategory = rs.getString("PRIMARY_CATEGORY");
				categories.put(id, primaryCategory);
				System.out.println(id + "\t\t" + primaryCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}

}
