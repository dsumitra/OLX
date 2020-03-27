package olx.paymentMethods;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnection.*;

public class PaymentMethodsDAO implements IPaymentMethodsDAO {

	@Override
	public int addPaymentMethods(PaymentMethodsModel p) throws ClassNotFoundException, SQLException {		
		String sql= "insert into payment_methods (USER_ID,PAYMENT_METHOD,CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME_ON_CARD)" + 
				"values( %d, '%s', '%s', %d, %d, '%s')";
		return DBConnection.executeUpdateFormat(sql, 
				p.userID,
				p.paymentMethod, p.cardNumber,
				p.expMonth, p.expYear,
				p.nameOnCard);
	}

	@Override
	public int updateCard(PaymentMethodsModel p) throws ClassNotFoundException, SQLException {
		String sql ="UPDATE payment_methods " + 
				"SET PAYMENT_METHOD = '%s', CARD_NUMBER=%s, EXP_MONTH= %d, EXP_YEAR=%d," + 
				"NAME_ON_CARD='%s' WHERE ID = %d";
		
		return DBConnection.executeUpdateFormat(sql,
				p.paymentMethod, p.cardNumber, p.expMonth, p.expYear, p.nameOnCard, p.pmID);
	}

	@Override
	public int deletePaymentMethods(Long pmID) throws ClassNotFoundException, SQLException {
		String sql = "DELETE payment_methods WHERE id = %d";
		return DBConnection.executeUpdateFormat(sql,pmID);
	}

	@Override
	public ResultSet getPaymentMethods(Long userID) throws ClassNotFoundException, SQLException {
		String sql = "select * from payment_methods where user_id = %d";
		return DBConnection.executeQueryFormat(sql, userID);
	}


}
