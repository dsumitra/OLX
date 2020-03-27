package cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Cart {
	static CartDAO cdb = new CartDAO();
	CartModel c = new CartModel();
	Scanner s = new Scanner(System.in);	

	void readCartId() {
		System.out.println("Enter CartModel Id:");
		c.cartId =  Long.valueOf(s.nextLine().trim());
	}
	
	void readClassifiedId() {
		System.out.println("Enter Classified Id");
		c.classifiedId = Long.valueOf(s.nextLine().trim());
	}

	void readBidPrice() {
		System.out.println("Enter Bid Price");
		c.bidPrice = Double.valueOf(s.nextLine().trim());
	}

	void readBidderID() {
		if (c.bidderID == null) {
			// may not be required if session is maintained
			System.out.println("Enter Bidder ID");
			c.bidderID = Long.valueOf(s.nextLine().trim());
		}
	}

	void readCart() {
		readClassifiedId();
		readBidPrice();
		readBidderID();
	}
	
	void writeCartHead() {
		//get classified title
		System.out.printf("%10s %13s %10s %10s %10s%n", "Cart_ID", "CLASSIFIED_ID", "bidPrice", "status",
				"BIDDER_ID");
	}
	
	
	void readRecord(ResultSet r) throws SQLException {
		c.cartId = r.getLong(1);
		c.classifiedId = r.getLong(2);
		c.bidPrice = r.getDouble(3);
		c.status = r.getString(4);
		c.bidderID = r.getLong(5);
	}

	void writeCartRow() {
		System.out.printf("%10d %13d %10.2f %10s %10d %n", c.cartId, c.classifiedId, c.bidPrice, c.status,
				c.bidderID);
	}

	void writeResultSet(ResultSet r) throws SQLException {
		writeCartHead();
		while (r.next()) {
			readRecord(r);
			writeCartRow();
		}
	}

	public void addToCart() throws ClassNotFoundException, SQLException {
		bidForClassified();
	}

	private void bidForClassified() throws ClassNotFoundException, SQLException {
		readCart();
		int count = cdb.addToCart(c.classifiedId, c.bidPrice, c.bidderID);
		System.out.println(count + " records added");
	}

	public void approveBidForClassified() throws ClassNotFoundException, SQLException {
		readClassifiedId();
		
		ResultSet r = cdb.getBidsForClassified(c.classifiedId);
		writeResultSet(r);
		
		readCartId();
		int apCnt = cdb.approveBid(c.cartId);
		System.out.println(apCnt + " bids approved in Cart.");
	}

	public void approveBidForSeller() throws ClassNotFoundException, SQLException {

//		Seller = User.getLoggedinUserID();

		System.out.println("Enter Seller Id");
		String Seller = s.nextLine().trim();
		
		ResultSet r = cdb.getBidsForSeller( Seller);
		System.out.println("Bids for your classifieds");
		writeResultSet(r);
		
		readCartId();
		int apCnt = cdb.approveBid(c.cartId);
		System.out.println(apCnt + " bids approved in CartModel.");
		
	}
	
	public void viewBuyerCart(Long BuyerID) {
		//get bids for BuyerCart
		//show all
	}
	
	public void viewSellerCart(Long BuyerID) {
		//get bids for sellectCart
		//show all

	}	
	public static void main(String [] a) {
		
	}

}
