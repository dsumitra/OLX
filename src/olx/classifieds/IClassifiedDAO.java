package olx.classifieds;

import java.sql.ResultSet;

import olx.user.UserModel;

//import java.util.Map;

public interface IClassifiedDAO {

	public void addClassified(ClassifiedModel classified);

	public void deleteClassified(ClassifiedModel classifiedModel);

	public ResultSet getClassifiedsByUserId(long userID);

	public void updateClassified(ClassifiedModel classifiedModel);
	
	public  ResultSet OrderHistory(UserModel userModel);

}
