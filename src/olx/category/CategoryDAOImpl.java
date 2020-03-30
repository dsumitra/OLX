package olx.category;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnection.DBConnection;
import olx.category.CategoryConstants.CategoryColumnNames;
import olx.classifieds.ClassifiedDAOImpl;
import olx.constants.OlxConstants;

/**
 * @author dsumitra
 *
 */
public class CategoryDAOImpl implements ICategoryDAO {

	/**
	 * @param categoryName Fetches sub-catergory data from the database by filtering
	 *                     the data with primary-category
	 * @return ResultSet
	 */
	public ResultSet getSubCategories(String categoryName) {
		String query = "Select " + CategoryColumnNames.SUB_CATEGORY + " from " + OlxConstants.TableNames.CATEGORY
				+ " where " + CategoryColumnNames.PRIMARY_CATEGORY + " = '" + categoryName + "'";
		ResultSet rs = null;
		try {
			rs = DBConnection.executeQuery(query);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Fetches distinct primary category from database
	 * 
	 * @return ResultSet
	 */
	public ResultSet getPrimaryCategories() {

		String categoryQuery = "select distinct " + CategoryColumnNames.PRIMARY_CATEGORY + " from "
				+ OlxConstants.TableNames.CATEGORY + " order by " + CategoryColumnNames.PRIMARY_CATEGORY;
		ResultSet rs = null;
		try {
			rs = DBConnection.executeQuery(categoryQuery);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * Deletes a row in the Category table by a primary category name
	 * 
	 * @param primary category name
	 * 
	 */
	public void deleteCategoryByName(String categoryName) {
		ClassifiedDAOImpl classifiedDAOImpl = new ClassifiedDAOImpl();
		try {
			classifiedDAOImpl.deleteClasssifiedsByCategory(categoryName);
			String delQuery = "Delete from " + OlxConstants.TableNames.CATEGORY + " where "
					+ CategoryColumnNames.PRIMARY_CATEGORY + " ='" + categoryName + "'";
			DBConnection.executeUpdate(delQuery);
			System.out.println("Successfully deleted Category: " + categoryName);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes a sub category from the category table by primary category name and
	 * sub category name.
	 * 
	 * @param primary category name, sub category name
	 */
	public void deleteSubCategory(String categoryName, String subCategory) {
		String delQuery = "Delete from " + OlxConstants.TableNames.CATEGORY + " where "
				+ CategoryColumnNames.PRIMARY_CATEGORY + " = '" + categoryName + "' and "
				+ CategoryColumnNames.SUB_CATEGORY + " = '" + subCategory + "'";
		try {
			DBConnection.executeUpdate(delQuery);
			System.out.println("Successfully deleted Sub-Category: " + subCategory);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a primary category and sub category name into the Category Table in
	 * database
	 * 
	 * @param primary category name, sub-category name
	 */
	public void addCategory(String category, String subCategory) {
		String query = "INSERT INTO " + OlxConstants.TableNames.CATEGORY + "(" + CategoryColumnNames.PRIMARY_CATEGORY
				+ " , " + CategoryColumnNames.SUB_CATEGORY + ") VALUES ('" + category + "','" + subCategory + "')";
		try {
			DBConnection.executeUpdate(query);
			System.out.println("Successfully created Category: " + category);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Updates the sub category name in the Category table in database.
	 * 
	 * @param sub category name, updated sub category name, primary category name
	 */
	public void updateSubcategory(String subCategory, String updateSubCategoryName, String selectedCategory) {
		String updateSubCategoryQuery = "update " + OlxConstants.TableNames.CATEGORY + " set "
				+ CategoryColumnNames.SUB_CATEGORY + " ='" + updateSubCategoryName + "' where "
				+ CategoryColumnNames.SUB_CATEGORY + "='" + subCategory + "' and "
				+ CategoryColumnNames.PRIMARY_CATEGORY + "='" + selectedCategory + "'";

		try {
			DBConnection.executeUpdate(updateSubCategoryQuery);
			System.out.println("Successfully updated Sub-Category: " + subCategory.toUpperCase() + " to "
					+ updateSubCategoryName.toUpperCase());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update primary category name in the Category table in database.
	 * 
	 * @param primary category name, updated primary category name
	 */
	public void updatePrimaryCategory(String selectedCategory, String updatePrimaryCategory) {
		String updatePrimaryCategoryQuery = "update " + OlxConstants.TableNames.CATEGORY + " set "
				+ CategoryColumnNames.PRIMARY_CATEGORY + " ='" + updatePrimaryCategory + "' where "
				+ CategoryColumnNames.PRIMARY_CATEGORY + " = '" + selectedCategory + "'";
		try {
			DBConnection.executeUpdate(updatePrimaryCategoryQuery);
			System.out.println("Successfully updated Category: " + selectedCategory.toUpperCase() + " to "
					+ updatePrimaryCategory.toString());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches all the data from category table
	 * 
	 * @return Resultset all category data
	 */
	public ResultSet getAllCategories() {

		ResultSet rs = null;
		try {
			String query = "Select * from " + OlxConstants.TableNames.CATEGORY;
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * Fetches data from Category table by categoryId.
	 * 
	 * @param categoryId
	 * @return ResultSet
	 */
	public ResultSet getCategoryById(int categoryId) {
		ResultSet rs = null;
		try {
			String query = "Select * from " + OlxConstants.TableNames.CATEGORY + " where " + CategoryColumnNames.ID
					+ " = " + categoryId;
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

}
