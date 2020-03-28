package olx.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import olx.payments.Payments;
import olx.user.User;
import olx.user.UserModel;

public class Cart {
	static CartDAOImpl cdb = new CartDAOImpl();
	CartModel c = new CartModel();
	Scanner s = new Scanner(System.in);

	public void viewCart(UserModel user) {
		int o = 0;
		while (o != 3) {
			System.out.println("\nCart Menu: \n 1.Requested Bids \n 2.Recieved Bids \n 3.Exit Menu ");
			System.out.println("Enter an option: ");
			o = Integer.parseInt(s.nextLine().trim());

			try {
				if (o == 1) {
					viewBuyerCart(user.getId());
				} else if (o == 2) {
					viewSellerCart(user.getId());
				} else if (o == 3) {
					User userView = new User();
					userView.displayUserOptions(user);
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	void readCartId() {
		System.out.println("Enter Cart Id:");
		c.cartId = Long.valueOf(s.nextLine().trim());
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
		if (c.bidderId == null) {
			// may not be required if session is maintained
			System.out.println("Enter Bidder ID");
			c.bidderId = Long.valueOf(s.nextLine().trim());
		}
	}

	void readCart() {
		readClassifiedId();
		readBidPrice();
//		readBidderID();
	}

	void writeCartHead() {
		System.out.printf("%10s %13s %10s %10s %10s %-30s %10s %10s %n", "Cart ID", "Classified ID", "Bid Price",
				"Status", "Bidder ID", "Title", "Exp Price", "Seller Id");
	}

	void writeCartRow(ResultSet r) throws SQLException {
		System.out.printf("%10d %13d %10.2f %10s %10d %30s %10.2f %10d %n", r.getLong(1), r.getLong(2), r.getDouble(3),
				r.getString(4), r.getLong(5), r.getString(6), r.getDouble(7), r.getLong(8));
	}

	void writeResultSet(ResultSet r) throws SQLException {
		writeCartHead();
		while (r.next()) {
			writeCartRow(r);
		}
	}

	public void addToCart(UserModel user, Map<Integer, Double> classifiedIdBidPriceMap)
			throws ClassNotFoundException, SQLException {
		int count = 0;

		for (Entry<Integer, Double> entry : classifiedIdBidPriceMap.entrySet()) {
			Integer classifiedId = entry.getKey();
			Double bidPrice = entry.getValue();
			count += bidForClassified(classifiedId.longValue(), bidPrice, user.getId());
		}

	}

	private int bidForClassified(Long classifiedId, Double bidPrice, Long userId)
			throws ClassNotFoundException, SQLException {
		return cdb.addToCart(classifiedId, bidPrice, userId);
	}

	public CartModel getCart(Long cartID) throws ClassNotFoundException, SQLException {
		ResultSet r = cdb.getCart(cartID);
		CartModel m = new CartModel();
		while (r.next()) {
			m.cartId = r.getLong(1);
			m.classifiedId = r.getLong(2);
			m.bidPrice = r.getDouble(3);
			m.status = r.getString(4);
			m.bidderId = r.getLong(5);
			break;
		}
		return m;
	}

	void readRecord(ResultSet r) throws SQLException {

		c.cartId = r.getLong(1);
		c.classifiedId = r.getLong(2);
		c.bidPrice = r.getDouble(3);
		c.status = r.getString(4);
		c.bidderId = r.getLong(5);
	}

	public void approveBidForClassified(Long classifiedId) throws ClassNotFoundException, SQLException {
		readClassifiedId();

		ResultSet r = cdb.getBidsForClassified(c.classifiedId);
		writeResultSet(r);

		readCartId();
		int apCnt = cdb.approveBid(c.cartId);
		System.out.println(apCnt + " bids approved in Cart.");
	}

	public void approveBidForSeller(Long seller) throws ClassNotFoundException, SQLException {
		ResultSet r = cdb.getBidsForSeller(seller);
		System.out.println("\nBids for your classifieds:");
		List<Long> cartIds = new ArrayList<Long>();
		while (r.next()) {
			System.out.println("Title: " + r.getString("title"));
			System.out.println("Expected Price: " + r.getString("expected_price"));
			System.out.println("Bid Price: " + r.getString("bidprice"));
			System.out.println("Bidder Id:" + r.getString("bidder_id"));
			System.out.println("Do you approve this bid? (Y/N)");
			String approve = s.nextLine().trim();
			if (approve.equalsIgnoreCase("Y")) {
				cartIds.add(Long.valueOf(r.getString("id")));
			}
		}
		for (int i = 0; i < cartIds.size(); i++) {
			cdb.approveBid(cartIds.get(i));
		}
//		int apCnt = cdb.approveBid(c.cartId);
//		System.out.println(apCnt + " bids approved in CartModel.");

	}

	public void viewSellerCart(Long sellerID) throws ClassNotFoundException, SQLException {
		approveBidForSeller(sellerID);
	}

	public void viewBuyerCart(Long buyerId) throws ClassNotFoundException, SQLException {
		ResultSet r = cdb.getBidsForBidder(buyerId);
		boolean invalidIp = false;
		boolean bidApproved = false;
//		writeResultSet(r);
		List<Long> approvedCartList = new ArrayList<>();
		String status;
		System.out.println(
				"Cart ID \t Classified ID \t Bid Price \t Status \t Bidder ID \t Title \t Exp Price \t Seller Id");
		while (r.next()) {
			String id = r.getString("id");
			String classfId = r.getString("classified_id");
			String bidprice = r.getString("bidprice");
			String state = r.getString("status");
			String bidId = r.getString("bidder_id");
			String title = r.getString("title");
			String exPrice = r.getString("expected_price");
			String sellerId = r.getString("seller_id");
			System.out.println(id + "\t\t" + classfId + "\t\t" + bidprice + "\t\t" + state + "\t\t" + bidId + "\t\t" + title
					+ "\t\t" + exPrice + "\t\t" + sellerId);
			status = r.getString("Status");
			if (status.equalsIgnoreCase("APPROVE")) {
				bidApproved = true;
				approvedCartList.add(r.getLong("id"));
			}
		}
		if (bidApproved == true) {
			System.out.println("Do you want to proceed to payment(Y/N): ");
			String op = s.nextLine().trim();
			if (op.equalsIgnoreCase("Y")) {
				do {
					System.out.println("Enter Approved Cart Id you want to pay for:");

					Long cartId = Long.valueOf(s.nextLine().trim());
					if (approvedCartList.contains(cartId)) {
						Payments p = new Payments();
						p.makePaymentforCart(cartId);
						invalidIp = false;
					} else {
						invalidIp = true;
						System.out.println("Invalid approved Cart Id ");
					}
				} while (invalidIp == true);
			}
		} else {
			System.out.println("\nYour Bids are not approved yet. Please check back later!!");
		}

	}

}
