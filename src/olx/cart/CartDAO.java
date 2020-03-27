package olx.cart;



import java.sql.*;
import dbConnect.*;

public class CartDAO implements ICartDAO, ICartConstants {

	public int addToCart(CartModel c) throws ClassNotFoundException, SQLException {
		return addToCart(c.classifiedId, c.bidPrice, c.bidderId);
	}

	int addToCart(Object ClassifiedId, Object BIDPRICE, Object BIdderId)
			throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"INSERT INTO " + CART +"(" + CLASSIFIED_ID + "," + BIDPRICE +"," + BIDDER_ID + ") VALUES(%s, %s, %s)", ClassifiedId.toString(),
				BIDPRICE.toString(), BIdderId.toString());
	}

	public int approveBId(Object CartId) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat("UPDATE " + CART + "SET " + STATUS + " = 'APPROVE' WHERE " + ID + " = %s",
				CartId.toString());
	}

	public ResultSet getBIdsForClassified(Object ClassifiedId) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat(
				"SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS + ", " + BIDDER_ID +
				" FROM " + CART + " WHERE " + CLASSIFIED_ID + " = %s ",
				ClassifiedId.toString());
	}

	public ResultSet getBIdsForBIdder(Object BIdder) throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat(
				"SELECT " + ID + ", " + CLASSIFIED_ID + ", " + BIDPRICE + ", " + STATUS + ", " + BIDDER_ID +
				" FROM " + CART + " WHERE "  + " BIDDER_ID = %s ",
				BIdder.toString());
	}

	public ResultSet getAllBIds() throws ClassNotFoundException, SQLException {
		return DBConnection.executeQueryFormat("SELECT CART_Id, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID FROM CART");
	}

	private String getClassifiedsCSVForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		ResultSet rs = DBConnection.executeQueryFormat(
				"select CLASSIFIED_ID from classifieds where state = 'APPROVED' and seller_Id = %s ",
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

	public ResultSet getBIdsForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		String s = getClassifiedsCSVForSeller(Seller);
		System.out.println("Classifieds for Seller : " + s);

		return DBConnection.executeQueryFormat(
				"SELECT CART_Id, CLASSIFIED_ID, BIDPRICE, STATUS, BIDDER_ID " + "FROM CART WHERE CLASSIFIED_ID IN "
						+ "(select CLASSIFIED_ID from classifieds where state = 'APPROVED' and seller_Id = %s) ",
				Seller.toString());
	}

	
	public int deleteCart(Long cartId) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat("DELETE FROM CART WHERE CART_Id = %s", cartId.toString());
	}

	
	public ResultSet displayAllCarts(Integer userId) throws ClassNotFoundException, SQLException {
		ResultSet r;
		if (userId == 0)
			r = getAllBIds();
		else
			r = getBIdsForBIdder(userId);
		return r;

	}

	
	public int updateCart(CartModel c) throws ClassNotFoundException, SQLException {
		return DBConnection.executeUpdateFormat(
				"UPDATE CART SET CLASSIFIED_ID = %d BIDPRICE = f% STATUS=%s WHERE CART_Id = %d", c.classifiedId,
				c.bidderId, c.status, c.bidderId, c.cartId);
	}

	@Override
	public int approveBid(Object CartID) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getBidsForClassified(Object ClassifiedId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getBidsForBidder(Object Bidder) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getBidsForSeller(Object Seller) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
