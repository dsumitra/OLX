package olx.classifieds;

import java.util.ArrayList;
import java.util.List;

import olx.User.UserModel;

public class ClassifiedsTest {
	public static void main(String[] args) {

		UserModel user = new UserModel(1, "user", "usrLastName", "7895321890", "user@gmail.com", "address given",
				false);
		UserModel admin = new UserModel(3, "admin", "adminLastName", "1235664532", "admin@gmail.com", "address given",
				true);
		UserModel uModel = user;

		Classifieds classified = new Classifieds();
//	classified.buy(uModel);
//		classified.manageClassifieds(uModel);
//		classified.displayPostedClassifieds();
//		classified.addClassifieds(uModel);
//		classified.updateClassified(1);
//		classified.displayAllClassifieds(0);//passing this by Default.
//		classified.displayClassifiedsByUser(1);
// 		classified.deleteClassifieds(1);
		// TODO: remove dummyIds and pass classifiedIds
//		List<Integer> dummyIds = new ArrayList<>();
//		dummyIds.add(121);
//		dummyIds.add(122);
//		classified.markClassifiedsAsSold(103);
//		classified.buy(uModel);
		
	}
}
