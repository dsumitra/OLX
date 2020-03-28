package olx.payments;

import java.util.*;

import olx.cart.*;
import olx.paymentMethods.*;

import java.sql.*;

public class Payments {
	
	PaymentsModel p = new PaymentsModel();
	PaymentsDAO pdb = new PaymentsDAO();
	Scanner s = new Scanner(System.in);	
	Cart c = new Cart();
	PaymentMethods pm = new PaymentMethods();
	

	public void makePaymentforCart(Long cartId) {
		try {
			
	
		CartModel cm = c.getCart(cartId);
		pm.viewPaymentMethods(cm.getBidderID());
		
		System.out.println("Enter option:");
		System.out.println("1) Make Payment");
		System.out.println("2) Add new Payment Method");
		
		
		Integer op =Integer.valueOf(s.nextLine().trim());
		if(op == 1)
		{		 
				System.out.println("Enter Payment method id");
				p.paymentMethodID = Long.valueOf( s.nextLine().trim());
				p.classifiedID = cm.getClassifiedId();
				p.userID = cm.getBidderID();
				p.cartID = cm.getCartId();
				p.amount = cm.getBidPrice();

				pdb.addPayment(p);
				System.out.println(" --: Thanks for Payment :--");

		}
		else if (op == 2){
			pm.addPaymentMethod(cm.getBidderID());
			makePaymentforCart(cartId);
		}
		}
		catch(  ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Payment failed. \nPlease try again.");
//			makePaymentforCart(cartId);
		}
		
	}
	
	public void addPayment() throws ClassNotFoundException, SQLException {
		readPayment();
		int count = pdb.addPayment(p.classifiedID, p.userID, p.paymentMethodID, p.cartID, p.amount);
		System.out.println(count + " payment saved.");
	}
	
	public void cancelPayment() throws ClassNotFoundException, SQLException {
		readPayment();
		int count = pdb.addPayment(p.classifiedID, p.userID, p.paymentMethodID, p.cartID, -p.amount);
		System.out.println(count + " payment cancelled.");
	}


	void readPayment() {
		
		System.out.println("Enter Payment ID:");
		p.paymentID = Long.valueOf(s.nextLine().trim());

		System.out.println("Enter Classified ID");
		p.classifiedID = Long.valueOf(s.nextLine().trim());

		if (p.userID == null) {
			// may not be required if session is maintained
			System.out.println("Enter User ID");
			p.userID = Long.valueOf(s.nextLine().trim());
		}

		// show PaymentMethodID for user
		// let the user select an payment method ID

		System.out.println("Enter Payment Method ID");
		p.paymentMethodID = Long.valueOf(s.nextLine().trim());

		System.out.println("Enter Cart Id:");
		p.cartID = Long.valueOf(s.nextLine().trim());

		System.out.println("Enter payment amount:");
		p.amount = Double.valueOf(s.nextLine().trim());
	}

	void writeHead() {
		System.out.printf("%10s %13s %10s %13s %10s %10s%n", "PAYMENT_ID", "CLASSIFIED_ID", "USER_ID", "PMT_METHOD_ID",
				"CART_ID", "AMOUNT");
	}

	void readRecord(ResultSet r) throws SQLException {
		p.paymentID = r.getLong(1);
		p.classifiedID = r.getLong(2);
		p.userID = r.getLong(3);
		p.paymentMethodID = r.getLong(4);
		p.cartID = r.getLong(5);
		p.amount = r.getDouble(6);
	}

	void writeRow() {
		System.out.printf("%10d %13d %10d %13d %10d %10.2f%n", p.paymentID, p.classifiedID, p.userID, p.paymentMethodID, p.cartID,
				p.amount);
	}

	void writeResultSet(ResultSet r) throws SQLException {
		writeHead();
		while (r.next()) {
			readRecord(r);
			writeRow();
		}
	}

}
