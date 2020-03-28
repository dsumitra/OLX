package olx.cart;

import java.sql.SQLException;

public class CartTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Cart c = new Cart();
		c.viewBuyerBids(2L);
		
		System.out.println("Thank you");
		
//		CartModel m = c.getCart(2l);
//		System.out.println(m);
		
	}
}
