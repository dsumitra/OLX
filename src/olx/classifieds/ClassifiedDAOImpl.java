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
import olx.user.UserModel;

/**
 * @author dsumitra
 *
 */
public class ClassifiedDAOImpl implements IClassifiedDAO {
	@Override
	public void addClassified(ClassifiedModel classified) {
		SimpleDateFormat formatter = new SimpleDateFormat(DateFormats.DEFAULT);
		String sqlCreateDate = formatter.format(classified.getDateCreated());
		String sqlUpdateDate = formatter.format(classified.getDateUpdated());

		String query = "insert into " + TableNames.CLASSIFIEDS + "(" + ClassifiedColumnNames.CATEGORY_ID + ","
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

	public void markClassifiedsAsSold(long classifiedID) {
		String query = "Update " + OlxConstants.TableNames.CLASSIFIEDS + " set " + ClassifiedColumnNames.STATE + " = '"
				+ ClassifiedsConstants.ClassifiedStatus.SOLD + "' where " + ClassifiedColumnNames.ID + " = " + classifiedID;
		try {
			DBConnection.executeUpdate(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteClassified(ClassifiedModel classifiedModel) {

		String query = "delete from " + OlxConstants.TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.ID
				+ " = " + classifiedModel.getID();
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
	public ResultSet getClassifiedsByUserId(long userID) {
		String query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.USER_ID + "="
				+ userID + " and " + ClassifiedColumnNames.STATE + "='" + ClassifiedsConstants.ClassifiedStatus.APPROVED
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

	public ResultSet filterClassifiedsByState(ClassifiedStatus status) {
		String query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.STATE + "='"
				+ status + "'";
		ResultSet rs = null;
		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getApprovedClassifiedsOfOthers(long userId) {
		String query = "Select * from " + TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.STATE + "='"
				+ ClassifiedsConstants.ClassifiedStatus.APPROVED + "' and " + ClassifiedColumnNames.USER_ID + " != " + userId ;
		ResultSet rs = null;
		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	} 

	public ResultSet OrderHistory(UserModel userModel) {
		String query = "Select " + ClassifiedColumnNames.ID + ", " + ClassifiedColumnNames.TITLE + ", "
				+ ClassifiedColumnNames.PRICE + "," + ClassifiedColumnNames.DATE_UPDATED + " from "
				+ TableNames.CLASSIFIEDS + " where " + ClassifiedColumnNames.USER_ID + "= " + userModel.getId();
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// used for Reports
	public ResultSet getClassifiedsPerCategory() {
		String query = "select "+ OlxConstants.TableNames.CATEGORY+"."+CategoryColumnNames.PRIMARY_CATEGORY+", count("+OlxConstants.TableNames.CLASSIFIEDS+"."+ClassifiedColumnNames.ID+") as Classifieds_count from "+ OlxConstants.TableNames.CATEGORY+" right join "+
				OlxConstants.TableNames.CLASSIFIEDS +" ON ("+ OlxConstants.TableNames.CATEGORY+"."+CategoryColumnNames.ID+
				" = "+ OlxConstants.TableNames.CLASSIFIEDS+"."+ClassifiedColumnNames.CATEGORY_ID+") GROUP BY "
				+ OlxConstants.TableNames.CATEGORY+"."+CategoryColumnNames.PRIMARY_CATEGORY ;
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getClassifiedsPerUser() {
		String query = "SELECT Count (" + ClassifiedColumnNames.USER_ID + ") as count," + ClassifiedColumnNames.USER_ID
				+ " FROM " + OlxConstants.TableNames.CLASSIFIEDS + " GROUP BY " + ClassifiedColumnNames.USER_ID;
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getClassifiedStatus() {
		String query = "SELECT Count (" + ClassifiedColumnNames.STATE + ") as count," + ClassifiedColumnNames.STATE
				+ " FROM " + OlxConstants.TableNames.CLASSIFIEDS + " GROUP BY " + ClassifiedColumnNames.STATE;
		ResultSet rs = null;

		try {
			rs = DBConnection.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}