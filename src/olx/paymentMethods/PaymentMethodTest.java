package olx.paymentMethods;

import java.sql.SQLException;

public class PaymentMethodTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PaymentMethods p= new PaymentMethods();
		try {
			p.viewPaymentMethods(2L);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
