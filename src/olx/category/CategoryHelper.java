package olx.category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CategoryHelper {
	Connection con;
	Statement stmt;

	CategoryDAOImpl CategoryDAOImpl;

	public CategoryHelper() {
		CategoryDAOImpl = new CategoryDAOImpl();
	}

	public Map<Integer, CategoryModel> displayCatergoriesTable() {
		Map<Integer, CategoryModel> categoryMap = new HashMap<>();

		ResultSet rs = CategoryDAOImpl.getAllCategories();

		System.out.println("ID \t\t\t  Primary \t\t\t Sub_Category");
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				String primaryCategory = rs.getString(2);
				String subCategory = rs.getString(3);
				CategoryModel categoryModel = new CategoryModel(id, primaryCategory, subCategory);
				categoryMap.put(id, categoryModel);
				System.out.println(id + "\t\t\t" + primaryCategory + "\t\t\t" + subCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryMap;
	}
}
