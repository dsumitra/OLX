package olx.classifieds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dbConnection.DBConnection;
import olx.category.CategoryConstants.CategoryColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
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
				+ ClassifiedColumnNames.USER_ID + ")"
						+ " values (" + classified.getCategoryID() + ","
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
				+ classifiedModel.getPrice() + " where " + ClassifiedColumnNames.ID + " = '" + classifiedModel.getID()
				+ "'";

////		String query = "update" + TableNames.CLASSIFIEDS + "set " + ClassifiedColumnNames.TITLE + "= %s ,"
//				+ classifiedModel.getTitle() + "," + ClassifiedColumnNames.DESCRIPTION + " = %s ,"
//				+ classifiedModel.getDescription() + "," + ClassifiedColumnNames.PRICE + "= %s ,"
//				+ classifiedModel.getPrice() + " where " + ClassifiedColumnNames.ID + "= %s ,"
//				+ classifiedModel.getID();

		try {
			DBConnection.executeQuery(query);
			System.out.println("Sucessfullu updated the classified: " + classifiedModel.getTitle());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// TODO Auto-generated method stub

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
		String categoryIDquery = "select " + CategoryColumnNames.ID + " from "
				+ OlxConstants.TableNames.CATEGORY + " where " + CategoryColumnNames.PRIMARY_CATEGORY + " = '" + categoryName + "'";
		String query = "delete from " + OlxConstants.TableNames.CLASSIFIEDS + " where (" + ClassifiedColumnNames.CATEGORY_ID + ") in ("
				+ categoryIDquery + ")";
		try {
			DBConnection.executeQuery(query);
			System.out.println("Successfully deleted classifieds for : " + categoryName);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// TODO Auto-generated method stub
	@Override
	public ResultSet getAllClassifieds(int userID) {
		userID = 1; // TODO getUserID
		String query;
		if (userID != 0) {
			// TODO: to use UserID in the query
			query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.USER_ID + "=" + 0;
		} else {
			query = "select * from " + TableNames.CLASSIFIEDS;
		}

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

}
