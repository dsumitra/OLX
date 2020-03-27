package olx.paymentMethods;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IPaymentMethodsDAO {
	int addPaymentMethods(PaymentMethodsModel pm) throws ClassNotFoundException, SQLException;
	int updateCard(PaymentMethodsModel pm)throws ClassNotFoundException, SQLException;
	int deletePaymentMethods(Long pmID)throws ClassNotFoundException, SQLException;
	ResultSet getPaymentMethods(Long userID)throws ClassNotFoundException, SQLException;
}

