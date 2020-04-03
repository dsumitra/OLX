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
	UserModel userModel = null;

	/**
	 * Shows CART options view to user
	 * 
	 * @param UserModel
	 */
	public void viewCart(UserModel user) {
		userModel = user;
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
				System.out.println("invalid Input");
				viewCart(user);
			} catch (SQLException e) {
				System.out.println("invalid Input");
				viewCart(user);
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

	/**
	 * Shows all the bids the user has received for his posted classifieds and gives
	 * an option to approve them.
	 * 
	 * @param sellerId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void approveBidForSeller(Long seller) throws ClassNotFoundException, SQLException {
		ResultSet r = cdb.getBidsForSeller(seller);
		System.out.println("\nBids for your classifieds:");
		System.out.println("Cart ID \t Bidder Id \t\t Title\t\tBid Price\t\tPosted Price\t\tStatus");
		long selectedCartId = 0;
		Map<Long, CartModel> cartData = new HashMap<Long, CartModel>();
		while (r.next()) {
			long cartId = r.getLong("id");
			long classifiedId = r.getLong("classified_id");
			double bidPrice = r.getDouble("bidprice");
			String status = r.getString("status");
			long bidder = r.getLong("bidder_id");
			CartModel cm = new CartModel(cartId, classifiedId, bidPrice, status, bidder);
			System.out.println(cartId + "\t\t\t" + bidder + "\t\t" + r.getString("title") + "\t\t" + bidPrice + "\t\t"
					+ r.getString("expected_price") + "\t\t" + status);
			if (status.equalsIgnoreCase("BID")) {
				cartData.put(cartId, cm);
			}
		}
		String approve;
		do {
			System.out.println("Do you wish to approve any of the above bids?(Y/N): ");
			approve = s.nextLine().trim();
		} while (!(approve.equalsIgnoreCase("Y") || approve.equalsIgnoreCase("N")));

		if (approve.equalsIgnoreCase("Y")) {
			int approveNum = 0;

			do {
				System.out.println("How many bids you wish to approve?: ");
				approveNum = Integer.parseInt(s.nextLine().trim());
			} while (approveNum < 0 || approveNum > cartData.size());

			do {
				List<Long> removeFromMap = new ArrayList<>();
				for (int i = 0; i < approveNum; i++) {
					System.out.println("Enter a valid Cart ID you wish to approve: ");
					selectedCartId = Long.parseLong(s.nextLine());
					long classifiedId = cartData.get(selectedCartId).getClassifiedId();
					// deleting bid from the local map.
					for (Map.Entry<Long, CartModel> entry : cartData.entrySet()) {
						if (entry.getKey() != selectedCartId) {
							CartModel model = entry.getValue();
							if (classifiedId == model.getClassifiedId()) {
								removeFromMap.add(model.getCartId());
							}
						}
					}

					for (int j = 0; j < removeFromMap.size(); j++) {
						cartData.remove(removeFromMap.get(i));
					}
					// approving the selected bid in the database
					cdb.approveBid(selectedCartId);
					// deleting bid from the database
					cdb.deleteBid(classifiedId);
					System.out.println("Approved bid succesfully!!");
				}

			} while (cartData.get(selectedCartId) == null);
		}

	}

	public void viewSellerCart(Long sellerID) throws ClassNotFoundException, SQLException {
		approveBidForSeller(sellerID);
	}

	/**
	 * Shows all the classifieds added to cart by the user and gives an option to
	 * buy them.
	 * 
	 * @param buyerId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void viewBuyerCart(Long buyerId) throws ClassNotFoundException, SQLException {
		ResultSet r = cdb.getBidsForBidder(buyerId);
		boolean invalidIp = false;
		boolean bidApproved = false;
		List<Long> approvedCartList = new ArrayList<>();
		String status;
		System.out.println(
				"Cart ID \t Classified ID \t Bid Price \t Status \t Bidder ID \t Title \t\t Exp Price \t Seller Id");
		while (r.next()) {
			String id = r.getString("id");
			String classfId = r.getString("classified_id");
			String bidprice = r.getString("bidprice");
			String state = r.getString("status");
			String bidId = r.getString("bidder_id");
			String title = r.getString("title");
			String exPrice = r.getString("expected_price");
			String sellerId = r.getString("seller_id");
			System.out.println(id + "\t\t" + classfId + "\t\t" + bidprice + "\t\t" + state + "\t\t" + bidId + "\t\t"
					+ title + "\t\t" + exPrice + "\t\t" + sellerId);
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
