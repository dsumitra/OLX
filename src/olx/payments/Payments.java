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
	

	public void makePaymentforCart(Long cartId,Long buyerId ) throws ClassNotFoundException, SQLException {
		
		CartModel cm = c.getCart(cartId);
		
		System.out.println(cm);
		
		pm.viewPaymentMethods(buyerId);
		
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
		}
		else if (op == 2){
			pm.addPaymentMethod(cm.getBidderID());
			makePaymentforCart(cartId, buyerId);
		}
		//show methods for user
		//do you want to add payment method
		//select a payment method
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


//void showPaymentOptions() {
//	//todo get userModel in arguments 
//	System.out.println("Payment options: ");
//	System.out.println("1. Cash of Delivery");
//	System.out.println("2. Credit Card");
//	System.out.println("3. Debit Card");
//	System.out.println("Enter a valid mode of payment: ");
//	int value = sc.nextInt();
//	if (value == 1) {
//		System.out.println("Please keep the cash in hand at the time of delivery. Thank you.");
//		emptyCart();
//	} else if (value == 2 || value == 3) {
//		System.out.println("Payment successful. Thank you.");
//		emptyCart();
//	} else {
//		System.out.println("Incorrect Option");
//	}
//}
//
//void emptyCart() {
//	//TODO cartView.emptyCart(userModel)
//}

// class PaymentsMain {
////	Payments payments = new Payments();
//
//	
//	PaymentModel paymentmodel = new PaymentModel();
//
//	Scanner sc = new Scanner(System.in);
//
//	void PaymentView() {
//		String paymentOption = null;
//
//		Map<Integer, String> paymentsMap = new HashMap<Integer, String>();
//		paymentsMap.put(1, "Cash of Delivery");
//		paymentsMap.put(2, "Credit Card");
//		paymentsMap.put(3, "Debit Card");
//
//		System.out.println("Please select the mode of payment");
//		System.out.println("1. Cash of Delivery");
//		System.out.println("2. Credit Card");
//		System.out.println("3. Debit Card");
//		int value = sc.nextInt();
//
//		paymentOption = paymentsMap.get(value);
//
//		if (paymentOption == "Cash of Delivery") {
//
//			System.out.println("Please keep the cash in hand at the time of delivery. Thank you.");
//		} else if (paymentOption == "Credit Card") {
//			// view existing
//			// add new card
//			addCreditCard();
//			// break;
//
//		} else if (paymentOption == "Debit Card") {
//
//			addDebitCard();
//			// break;
//		} else {
//			System.out.println("Please select the correct option");
//		}
//	}
//
//	void addCreditCard() {
//		verifyCard();
//		// void addCard();
//
//	}
//
//	void addDebitCard() {
//
//	}
//
//	void verifyCard() {
//		try {
//			System.out.println("Enter the card number");
//			String cardNumber = sc.nextLine();
//			if (cardNumber.length() <= 19) {
//				System.out.println("Valid  card entered");
//
//				paymentmodel.set
//				System.out.println("Confirm to add the above entered card (Y/N): " + cardNumber);
//				String confirmation = sc.nextLine();
//				boolean invalidMonth = false;
//				try {
//					int month, year;
//					do {
//						System.out.println("Enter the month for expiration");
//						month = sc.nextInt();
//						if (month >= 1 && month <= 12) {
//							invalidMonth = true;
//							System.out.println("Invalid number for month");
////							System.out.println("Enter the month between 1 to 12");
//						} else {
//							invalidMonth = false;
//						}
//
//					} while (invalidMonth == true);
//
//					System.out.println("Enter the year for expiration");
//					year = sc.nextInt();
//
//					Date date = new Date(year);
//
//					if (year < date.getYear()) {
//						System.out.println("The card has been expired");
//					}
//				} catch (Exception e) {
//					System.out.println("Please enter valid numbers: ");
//					verifyCard();
//				}
//			} else {
//				System.out.println("Invalid card");
////				System.out.println("Kindly enter the card number again: ");
//				verifyCard();
//			}
//		} catch (
//
//		StringIndexOutOfBoundsException e) {
//			System.out.println("String index out of bounds exception");
//		}
//	}
//}
