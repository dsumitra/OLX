package olx.cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ICartDAO {
	public int addToCart(CartModel c) throws ClassNotFoundException, SQLException;
	public int deleteCart(Long cartID) throws ClassNotFoundException, SQLException;	
	public int updateCart(CartModel c) throws ClassNotFoundException, SQLException;
	public ResultSet getAllCarts(Long userID) throws ClassNotFoundException, SQLException;	
	
	public int approveBid(Long CartID) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForClassified(Long ClassifiedId) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForBidder(Long Bidder) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForSeller(Long Seller) throws ClassNotFoundException, SQLException;
}
