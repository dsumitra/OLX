package cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ICartDAO {
	public int addToCart(CartModel c) throws ClassNotFoundException, SQLException;
	public int deleteCart(Long cartID) throws ClassNotFoundException, SQLException;	
	public int updateCart(CartModel c) throws ClassNotFoundException, SQLException;
	public ResultSet displayAllCarts(Integer userID) throws ClassNotFoundException, SQLException;	
	
	public int approveBid(Object CartID) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForClassified(Object ClassifiedId) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForBidder(Object Bidder) throws ClassNotFoundException, SQLException;
	public ResultSet getBidsForSeller(Object Seller) throws ClassNotFoundException, SQLException;
}
