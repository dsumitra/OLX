package olx.category;

import java.sql.ResultSet;
import java.util.Map;

public interface ICategoryDAO {

	public void addCategory(String category, String subCategory);

	public void deleteCategoryByName(String categoryName);

	public void deleteSubCategory(String categoryName, String subCategory);

	public ResultSet getSubCategories(String categoryName);

	ResultSet getPrimaryCategories();

	public ResultSet getAllCategories();

	public void updateSubcategory(String subCategory, String updateSubCategoryName, String selectedCategory);

	public void updatePrimaryCategory(String selectedCategory, String updatePrimaryCategory);
}
