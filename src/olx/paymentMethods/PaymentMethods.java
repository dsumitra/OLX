package olx.paymentMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PaymentMethods {
	PaymentMethodsModel m = new PaymentMethodsModel();
	PaymentMethodsDAO d = new PaymentMethodsDAO();
	Scanner s = new Scanner(System.in);	
	
	public void addPaymentMethod(Long userId) throws ClassNotFoundException, SQLException {

		m.userID=userId;
		System.out.println("Enter an option:");
		System.out.println("1) CASH ON DELIVERY\r\n" + 
				"2) CREDIT CARD\r\n" + 
				"3) DEBIT CARD\r\n" + 
				"4) NET BANKING");
		
		Integer op =  Integer.valueOf(s.nextLine().trim());
		switch(op){
		case 1:
			m.paymentMethod="CASH ON DELIVERY";
			break;
		case 2:
			m.paymentMethod="CREDIT CARD";
			break;
		case 3:
			m.paymentMethod="DEBIT CARD";
			break;
		case 4:
			m.paymentMethod="NET BANKING";
			break;
		default:
			m.paymentMethod="CASH ON DELIVERY";
			break;
		}
		
		if(op ==1 || op ==2) {
			System.out.println("Enter Card Number:");
			m.cardNumber = s.nextLine().trim();
			System.out.println("Enter Expiry Month:");			
			m.expMonth = Long.valueOf(s.nextLine().trim());
			System.out.println("Enter Expiry Year:");			
			m.expYear = Long.valueOf(s.nextLine().trim());
			System.out.println("Enter Name on Card:");			
			m.nameOnCard = s.nextLine().trim();
		}
		d.addPaymentMethods(m);
	}

	public void deletePaymentMethod(Long userId) throws ClassNotFoundException, SQLException {
		viewPaymentMethods(userId);
		System.out.println("Enter payment method id to delete");
		m.pmID = Long.valueOf(s.nextLine().trim());
		d.deletePaymentMethods(m.pmID);
	}

	public void updatePaymentMethod(Long userId) {
		System.out.println("Please delete and add a peyment method");
	}

	public void viewPaymentMethods(Long userID) throws ClassNotFoundException, SQLException {
		ResultSet r = d.getPaymentMethods(userID);
		System.out.printf("%10s %10s %-30s %20s %10s %10s %-30s %n", "Method ID", "User ID",
				"Payment Method", "Card Number",
				"Exp Month", "Exp Year", "Name On Card");
		while (r.next()) {
			System.out.printf("%10s %10s %-30s %20s %10s %10s %-30s %n", r.getLong(1), r.getLong(2), r.getString(3),
					r.getString(4), r.getLong(5), r.getLong(6), r.getString(7));
		}
	}

}
