package olx.cart;

import dbConnection.*;

import java.sql.*;

public class CartDB {

	public int addToCart(Object ClassifiedId, Object BidPrice, Object BidderID)
			throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"INSERT INTO CART(CLASSIFIED_ID, BIDPRICE, BIDDER_ID) VALUES(%s, %s, %s)", 
				ClassifiedId.toString(),BidPrice.toString(), BidderID.toString());
	}

	public int approveBid(Object CartID) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat("UPDATE CART SET STATUS = 'APPROVE' WHERE CART_ID = %s",
				CartID.toString());
	}

	public ResultSet getBidsForClassified(Object ClassifiedId) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat(
				"SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID FROM CART WHERE CLASSIFIED_ID = %s ",
				ClassifiedId.toString());
	}

	public ResultSet getBidsForBidder(Object Bidder) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat(
				"SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID FROM CART WHERE BIDDER_ID = %s ",
				Bidder.toString());
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
		//4,5,
		
		sb.deleteCharAt(sb.length()-1);  //discard last comma
		return sb.toString(); // 4,5
	}

	public ResultSet getBidsForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		String s = getClassifiedsCSVForSeller(Seller);
		System.out.println("Classifieds for Seller : " + s);
		
		return DBConnection.executeQueryFormat("SELECT CART_ID, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID "
				+ "FROM CART WHERE CLASSIFIED_ID IN " + 
				"(select classified_id from classifieds where state = 'APPROVED' and seller_id = %s) ", Seller.toString());
	}

}
