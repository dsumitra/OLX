package olx.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CartUI {
	static CartDB cdb = new CartDB();
	Cart c = new Cart();
	Scanner s = new Scanner(System.in);	


	public void addToCart() throws ClassNotFoundException, SQLException {
		bidForClassified();
	}

	private void bidForClassified() throws ClassNotFoundException, SQLException {
		c.readCart();
		int count = cdb.addToCart(c.ClassifiedId, c.BidPrice, c.BidderID);
		System.out.println(count + " records added");
	}

	public void approveBidForClassified() throws ClassNotFoundException, SQLException {
		c.readClassifiedId();
		
		ResultSet r = cdb.getBidsForClassified(c.ClassifiedId);
		c.writeResultSet(r);
		
		c.readCartId();
		int apCnt = cdb.approveBid(c.CartId);
		System.out.println(apCnt + " bids approved in CartDB.");
	}

	public void approveBidForSeller() throws ClassNotFoundException, SQLException {

//		Seller = User.getLoggedinUserID();

		System.out.println("Enter Seller Id");
		String Seller = s.nextLine().trim();
		
		ResultSet r = cdb.getBidsForSeller( Seller);
		System.out.println("Bids for your classifieds");
		c.writeResultSet(r);
		
		c.readCartId();
		int apCnt = cdb.approveBid(c.CartId);
		System.out.println(apCnt + " bids approved in Cart.");
		
	}

}
