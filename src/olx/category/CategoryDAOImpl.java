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
