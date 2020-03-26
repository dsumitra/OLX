package olx.payments;

import java.util.Scanner;

public class PaymentsUI {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Payment options: ");
			System.out.println("1. Cash of Delivery");
			System.out.println("2. Credit Card");
			System.out.println("3. Debit Card");
			System.out.println("Enter a valid mode of payment: "); 
			int value = sc.nextInt();
			if (value == 1) {
				cod();
				break;
			} else if (value == 2) {
				creditCard();
				break;

			} else if (value == 3) {
				DebitCard db = new DebitCard();
				db.addDebitCard();
				break;
			} else {
				System.out.println("Incorrect Option");
			}
		}
	}

	static void cod() {
		System.out.println("User has opted for Cash on Delivery!");
	}

	static void creditCard() {
		CreditCard cc = new CreditCard();
		cc.addCreditCard();
	}

	static void DebitCard() {
		DebitCard dc = new DebitCard();
		dc.addDebitCard();
	}
}
