package olx.User;

public class User {
	UserModel userModel;
	User(UserModel userModel) {
		this.userModel = userModel;
	}
	void displayUserOptions() {
		System.out.println("Menu");
		if(userModel.isAdmin()) {
			System.out.println("1.delte users \n 2.manage categories");
		} else {
			System.out.println("1.Buy \n 2.Sell \n 3.Order History 4.manage classifieds, 5.view cart");			
		}
		System.out.println("settings");
		if(buy)
		  call classifiedView.buy(userModel) //give userId as 0 for admin
		 if(sell) call classifiedView.addClassified(userModel)
		 if(manageClassifieds) call {classifiedView.manage()}
		 if(view cart) call cartView.displayAllClassifieds(userModel)
		 if(Order History) cartView.showOrderHistory();
		 if(settings) showUserSettings(userModel)
		 
		 if(delte users) delUsers(userModel)
		 if(manage categories) categoriesView.manage(userModel)
	}
	
	void showUserSettings(userModel) {
		
			
			show options 1.Rename username, 2.change pwd, 
			3.phone;
			4 email;
			5 address;
	}
	
	void delUsers(userModel) {
		if(userModel.isAdmin()) {
			show a list of existing users (select * from users;)
			how many users you want to del? scan number
			for(int i = 0; i < number; i++) {
				print(enter the userId);
				userDao.del(userId);
			}
			
		}
	}
	
}
//to be added in the classified view
buy() {
	List<Classifieds> toBeAddedToCart= listClassifieds();
	addThemto the cartDB(cartDao)
}
manage() {
	give options 1.update & 2.delete
}

markSoldClassifieds(List<classifiedIds>) {
	
}

//to be added in the categories view
manageCategories() {
	show manage categories option
	1.add new category
	2.delete category
}

//to be added in the cart view
displayAllClassifieds(userModel) {
	list<cartItems> = select * from cart where userId = userModel.getId();
	option for payment/edit cart.
	if(confirm buying) paymentView.showPaymentOptions();
			//once payment is done, mark list<cartItmes> done/purchased //paymentDao
			//classifiedDao.markSoldClassifieds(List<classifiedIds>)
}

showOrderHistory(userModel) {
	select * from cart where userId = userModel.getId() and status == sold/ordered/cancelled.
		and	display the results
}