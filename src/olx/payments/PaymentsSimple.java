package olx.payments;

import java.util.Scanner;

public class PaymentsSimple {
	Scanner sc = new Scanner(System.in);

	void showPaymentOptions() {
		//todo get userModel in arguments 
		System.out.println("Payment options: ");
		System.out.println("1. Cash of Delivery");
		System.out.println("2. Credit Card");
		System.out.println("3. Debit Card");
		System.out.println("Enter a valid mode of payment: ");
		int value = sc.nextInt();
		if (value == 1) {
			System.out.println("Please keep the cash in hand at the time of delivery. Thank you.");
			emptyCart();
		} else if (value == 2 || value == 3) {
			System.out.println("Payment successful. Thank you.");
			emptyCart();
		} else {
			System.out.println("Incorrect Option");
		}
	}
	
	void emptyCart() {
		//TODO cartView.emptyCart(userModel)
	}
}
