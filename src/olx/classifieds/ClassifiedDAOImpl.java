package olx.classifieds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import dbConnection.DBConnection;
import olx.category.CategoryConstants.CategoryColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedStatus;
import olx.constants.OlxConstants;
import olx.constants.OlxConstants.DateFormats;
import olx.constants.OlxConstants.TableNames;

public class ClassifiedDAOImpl implements IClassifiedDAO {

	// TODO CHECK FOR INSERTION AFTER UPDATING THE TABLE
	@Override
	public void addClassified(ClassifiedModel classified) {
		SimpleDateFormat formatter = new SimpleDateFormat(DateFormats.DEFAULT);
		String sqlCreateDate = formatter.format(classified.getDateCreated());
		String sqlUpdateDate = formatter.format(classified.getDateUpdated());

		String query = "insert into classifieds(" + ClassifiedColumnNames.CATEGORY_ID + ","
				+ ClassifiedColumnNames.PRICE + "," + ClassifiedColumnNames.TITLE + ","
				+ ClassifiedColumnNames.DESCRIPTION + "," + ClassifiedColumnNames.EMAIL + ","
				+ ClassifiedColumnNames.PHONE + "," + ClassifiedColumnNames.DATE_CREATED + ","
				+ ClassifiedColumnNames.DATE_UPDATED + "," + ClassifiedColumnNames.STATE + ","
				+ ClassifiedColumnNames.USER_ID + ")" + " values (" + classified.getCategoryID() + ","
				+ classified.getPrice() + "," + "'" + classified.getTitle() + "','" + classified.getDescription()
				+ "','" + classified.getEmail() + "','" + classified.getPhone() + "',to_date('" + sqlCreateDate + "', '"
				+ DateFormats.DB + "'),to_date('" + sqlUpdateDate + "', '" + DateFormats.DB + "'),'"
				+ classified.getState() + "'," + classified.getUserID() + ")";
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully created classified: " + classified.getTitle());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void updateClassified(ClassifiedModel classifiedModel) {
		String query = "Update " + TableNames.CLASSIFIEDS + " set " + ClassifiedColumnNames.TITLE + " ='"
				+ classifiedModel.getTitle() + "' , " + ClassifiedColumnNames.DESCRIPTION + " ='"
				+ classifiedModel.getDescription() + "', " + ClassifiedColumnNames.PRICE + " = "
				+ classifiedModel.getPrice() + ", " + ClassifiedColumnNames.STATE + " = '" + classifiedModel.getState()
				+ "' where " + ClassifiedColumnNames.ID + " = " + classifiedModel.getID();

		try {
			DBConnection.executeQuery(query);
			System.out.println("Sucessfully updated the classified: " + classifiedModel.getTitle());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateStatusById(List<Integer> classifiedIDs) {
		if(classifiedIDs.size() == 0) return;
		
		String query = "Update " + OlxConstants.TableNames.CLASSIFIEDS + " set " + ClassifiedColumnNames.STATE + " = '"
				+ ClassifiedsConstants.ClassifiedStatus.SOLD + "' where";
		String idConditions = "";
		String prefix = " ";
		for (int i = 0; i < classifiedIDs.size(); i++) {
			if(i != 0) prefix = " or " ;
			idConditions += prefix + ClassifiedColumnNames.ID + " = " + classifiedIDs.get(i) ;
		}
		String updateQuery = query + idConditions;
		try {
			DBConnection.executeUpdate(updateQuery);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteClassified(ClassifiedModel classifiedModel) {

		String query = "delete from " + OlxConstants.TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.ID + "="
				+ classifiedModel.getID();
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully created classified: " + classifiedModel.getTitle());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteClasssifiedsByCategory(String categoryName) {
		String categoryIDquery = "select " + CategoryColumnNames.ID + " from " + OlxConstants.TableNames.CATEGORY
				+ " where " + CategoryColumnNames.PRIMARY_CATEGORY + " = '" + categoryName + "'";
		String query = "delete from " + OlxConstants.TableNames.CLASSIFIEDS + " where ("
				+ ClassifiedColumnNames.CATEGORY_ID + ") in (" + categoryIDquery + ")";
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully deleted classifieds for : " + categoryName);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet getClassifiedsByUserId(int userID) {
		String query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.USER_ID + "=" + userID
				+ " and " + ClassifiedColumnNames.STATE + "='" + ClassifiedsConstants.ClassifiedStatus.APPROVED
				+ "' or " + ClassifiedColumnNames.STATE + "='" + ClassifiedsConstants.ClassifiedStatus.POSTED + "'";

		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet filterClassifiedsByState(ClassifiedStatus approved) {
		String query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.STATE + "='"
				+ approved + "'";
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
