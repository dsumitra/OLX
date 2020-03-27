package cart;

import java.sql.*;
import dbConnect.*;

public class CartDAO implements ICartDAO, ICartConstants {

	public int addToCart(CartModel c) throws ClassNotFoundException, SQLException {
		return addToCart(c.classifiedId, c.bidPrice, c.bidderID);
	}

	int addToCart(Object ClassifiedId, Object BidPrice, Object BidderID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"INSERT INTO " + CART + "(" + CLASSIFIED_ID + "," + BIDPRICE + "," + BIDDER_ID + ") VALUES(%s, %s, %s)",
				ClassifiedId.toString(), BidPrice.toString(), BidderID.toString());
	}

	public int approveBid(Object CartID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"UPDATE " + CART + "SET " + STATUS + " = 'APPROVE' WHERE " + ID + " = %s", CartID.toString());
	}

	public ResultSet getBidsForClassified(Object ClassifiedId) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat("SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS
				+ ", " + BIDDER_ID + " FROM " + CART + " WHERE " + CLASSIFIED_ID + " = %s ", ClassifiedId.toString());
	}

	public ResultSet getBidsForBidder(Object Bidder) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat("SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS
				+ ", " + BIDDER_ID + " FROM " + CART + " WHERE " + " BIDDER_ID = %s ", Bidder.toString());
	}

	public ResultSet getAllBids() throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat("SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID FROM CART");
	}

	private String getClassifiedsCSVForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		ResultSet rs = DBConnection.executeQueryFormat(
				"select classified_id from classifieds where state = 'APPROVED' and seller_id = %s ",
				Seller.toString());

		StringBuilder sb = new StringBuilder();

		while (rs.next()) {
			sb.append(rs.getLong(1));
			sb.append(",");
		}
		// 4,5,

		sb.deleteCharAt(sb.length() - 1); // discard last comma
		return sb.toString(); // 4,5
	}

	public ResultSet getBidsForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		String s = getClassifiedsCSVForSeller(Seller);
		System.out.println("Classifieds for Seller : " + s);

		return DBConnection.executeQueryFormat(
				"SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID " + "FROM CART WHERE CLASSIFIED_ID IN "
						+ "(select classified_id from classifieds where state = 'APPROVED' and seller_id = %s) ",
				Seller.toString());
	}

	@Override
	public int deleteCart(Long cartID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat("DELETE FROM CART WHERE CART_ID = %s", cartID.toString());
	}

	@Override
	public ResultSet displayAllCarts(Integer userID) throws ClassNotFoundException, SQLException {
		ResultSet r;
		if (userID == 0)
			r = getAllBids();
		else
			r = getBidsForBidder(userID);
		return r;

	}

	@Override
	public int updateCart(CartModel c) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"UPDATE CART SET CLASSIFIED_ID = %d BIDPRICE = f% STATUS=%s WHERE CART_ID = %d", c.classifiedId,
				c.bidPrice, c.status, c.bidderID, c.cartId);
	}

}
