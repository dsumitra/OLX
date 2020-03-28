package olx.payments;


import java.sql.SQLException;
import dbConnection.*;

public class PaymentsDAO implements IPaymentsDAO {
	
	public int addPayment(Long ClassifiedId,Long UserId,Long PaymentMethodId,Long CartId,Double Amount)
			throws ClassNotFoundException, SQLException {
		
		
		return DBConnection.executeUpdateFormat(
				" INSERT INTO PAYMENTS(CLASSIFIED_ID,USER_ID,PAYMENT_METHOD_ID,CART_ID,AMOUNT) " + 
				" VALUES(%d, %d, %d, %d, %10.2f)", 
				 ClassifiedId, UserId, PaymentMethodId,CartId, Amount);
	}
	
	public int addPayment(PaymentsModel p) throws ClassNotFoundException, SQLException {
		
		System.out.println(p);
		
		return addPayment(p.classifiedID, p.userID, p.paymentMethodID, p.cartID, p.amount);
	}
}
