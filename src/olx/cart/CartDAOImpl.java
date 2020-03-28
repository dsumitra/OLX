package olx.cart;

import java.sql.ResultSet;
import java.sql.SQLException;


import dbConnection.*;

public class CartDAOImpl implements ICartDAO, ICartConstants {

	public int addToCart(CartModel c) throws ClassNotFoundException, SQLException {
		return addToCart(c.classifiedId, c.bidPrice, c.bidderId);
	}

	int addToCart(Object ClassifiedId, Object BidPrice, Object BidderID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"INSERT INTO " + CART + "(" + CLASSIFIED_ID + "," + BIDPRICE + "," + BIDDER_ID + ") VALUES(%s, %s, %s)",
				ClassifiedId.toString(), BidPrice.toString(), BidderID.toString());
	}

	public int approveBid(Long CartID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"UPDATE " + CART + " SET " + STATUS + " = 'APPROVE' WHERE " + ID + " = %s", CartID.toString());
	}

	public ResultSet getCart(Long cartID) throws ClassNotFoundException, SQLException {
		String sql ="select id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id from vcart" +
		" where id = %d ";
		return DBConnection.executeQueryFormat(sql, cartID);
	}

	
	
	public ResultSet getBidsForClassified(Long ClassifiedId) throws ClassNotFoundException, SQLException {
//		return DBConnection.executeQueryFormat("SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS
//				+ ", " + BIDDER_ID + " FROM " + CART + " WHERE " + CLASSIFIED_ID + " = %s ", ClassifiedId.toString());
		
		String sql ="select id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id from vcart" +
		" where CLASSIFIED_ID = %d ";
		return DBConnection.executeQueryFormat(sql, ClassifiedId);
		
	}

	public ResultSet getBidsForBidder(Long Bidder) throws ClassNotFoundException, SQLException {
//		return DBConnection.executeQueryFormat("SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS
//				+ ", " + BIDDER_ID + " FROM " + CART + " WHERE " + " BIDDER_ID = %s ", Bidder.toString());
		String sql ="select id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id from vcart" +
		" where BIDDER_ID = %d ";
		return DBConnection.executeQueryFormat(sql, Bidder);
	}

	public ResultSet getAllBids() throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat("select id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id from vcart");
	}

	private String getClassifiedsCSVForSeller(Long Seller) throws ClassNotFoundException, SQLException {
		ResultSet rs = DBConnection.executeQueryFormat(
				"select id from classifieds  where state = 'APPROVED' and user_id = %s ",
				Seller.toString());

		StringBuilder sb = new StringBuilder();

		while (rs.next()) {
			sb.append(rs.getLong(1));
			sb.append(",");
		}
		// 4,5,
		if(sb.length() > 1) sb.deleteCharAt(sb.length() - 1); // discard last comma
		return sb.toString(); // 4,5
	}

	public ResultSet getBidsForSeller(Long Seller) throws ClassNotFoundException, SQLException {
		String s = getClassifiedsCSVForSeller(Seller);

//		return DBConnection.executeQueryFormat(
//				"SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID " + "FROM CART WHERE CLASSIFIED_ID IN "
//						+ "(select classified_id from classifieds  where state = 'APPROVED' and seller_id = %s) ",
//				Seller.toString());

		String sql ="select id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id from vcart" +
		" where seller_id = %d ";
		return DBConnection.executeQueryFormat(sql, Seller);	
		
	}

	@Override
	public int deleteCart(Long cartID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat("DELETE FROM CART WHERE ID = %s", cartID.toString());
	}

	@Override
	public ResultSet getAllCarts(Long userID) throws ClassNotFoundException, SQLException {
		ResultSet r;
		if (userID == 0)
			r = getAllBids();
		else
			r = getBidsForSeller(userID);
		return r;

	}

	@Override
	public int updateCart(CartModel c) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"UPDATE CART SET CLASSIFIED_ID = %d BIDPRICE = f% STATUS=%s WHERE CART_ID = %d", c.classifiedId,
				c.bidPrice, c.status, c.bidderId, c.cartId);
	}

}
