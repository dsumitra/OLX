package olx.classifieds;

import olx.user.UserConstants;
import olx.user.UserModel;

public class ClassifiedsTest {
	public static void main(String[] args) {

//		UserModel user = new UserModel();
//
		UserModel user = new UserModel(1, "user", "usrLastName", "7895321890", "user@gmail.com", "address given",
				false, UserConstants.UserStatus.ACTIVE);
//		UserModel admin = new UserModel(3, "admin", "adminLastName", "1235664532", "admin@gmail.com", "address given",
//				true);

		Classifieds classified = new Classifieds();
		classified.buy(user);
//		classified.sellClassifieds(user);

		classified.manageClassifieds(user);
		classified.displayPostedClassifieds();
		classified.addClassifieds(user);
		classified.updateClassified(user);
//classified.displayAllClassifieds(0);//passing this by Default.
//classified.displayClassifiedsByUser(uModel);
//classified.deleteClassifieds(uModel);
		// TODO: remove dummyIds and pass classifiedIds
//		List<Integer> dummyIds = new ArrayList<>();
//		dummyIds.add(121);
//		dummyIds.add(122);
//		classified.markClassifiedsAsSold(103);
//		classified.buy(uModel);

	}
}
