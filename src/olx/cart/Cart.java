
package olx.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Cart {
	Long CartId;
	Long ClassifiedId;
	Double BidPrice;
	String Status;
	Long BidderID;

	private static Scanner s = new Scanner(System.in);

	public Cart() {
		Status = "BID";
//		BidderID = User.getLoggedinUserID();
	}

	void readCartId() {
		System.out.println("Enter Cart Id:");
		CartId =  Long.valueOf(s.nextLine().trim());
	}
	
	void readClassifiedId() {
		System.out.println("Enter Classified Id");
		ClassifiedId = Long.valueOf(s.nextLine().trim());
	}

	void readBidPrice() {
		System.out.println("Enter Bid Price");
		BidPrice = Double.valueOf(s.nextLine().trim());
	}

	void readBidderID() {
		if (BidderID == null) {
			// may not be required if session is maintained
			System.out.println("Enter Bidder ID");
			BidderID = Long.valueOf(s.nextLine().trim());
		}
	}

	void readCart() {
		readClassifiedId();
		readBidPrice();
		readBidderID();
	}
	
	void writeCartHead() {
		System.out.printf("%10s %13s %10s %10s %10s%n", "Cart_ID", "CLASSIFIED_ID", "BIDPRICE", "STATUS",
				"BIDDER_ID");
	}
	
	
	void readRecord(ResultSet r) throws SQLException {
		CartId = r.getLong(1);
		ClassifiedId = r.getLong(2);
		BidPrice = r.getDouble(3);
		Status = r.getString(4);
		BidderID = r.getLong(5);
	}

	void writeCartRow() {
		System.out.printf("%10d %13d %10.2f %10s %10d %n", CartId, ClassifiedId, BidPrice, Status,
				BidderID);
	}

	void writeResultSet(ResultSet r) throws SQLException {
		writeCartHead();
		while (r.next()) {
			readRecord(r);
			writeCartRow();
		}
	}
	
}
