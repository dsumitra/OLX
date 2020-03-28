package olx.cart;

import java.sql.SQLException;

import olx.user.UserModel;

public class CartTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Cart c = new Cart();
		UserModel m = new UserModel();
		m.setId(1);
		c.viewCart(m);
		
		System.out.println("Thank you");
		
//		CartModel m = c.getCart(2l);
//		System.out.println(m);
		
	}
}
