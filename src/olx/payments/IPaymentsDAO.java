package olx.payments;

import java.sql.SQLException;

public interface IPaymentsDAO {
	public int addPayment(PaymentsModel p) throws ClassNotFoundException, SQLException;
}
