package olx.payments;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PaymentsMain {
//	Payments payments = new Payments();

	Scanner sc = new Scanner(System.in);

	void PaymentView() {
		String paymentOption = null;

		Map<Integer, String> paymentsMap = new HashMap<Integer, String>();
		paymentsMap.put(1, "Cash of Delivery");
		paymentsMap.put(2, "Credit Card");
		paymentsMap.put(3, "Debit Card");

		System.out.println("Please select the mode of payment");
		System.out.println("1. Cash of Delivery");
		System.out.println("2. Credit Card");
		System.out.println("3. Debit Card");
		int value = sc.nextInt();

		paymentOption = paymentsMap.get(value);

		if (paymentOption == "Cash of Delivery") {

			System.out.println("Please keep the cash in hand at the time of delivery. Thank you.");
		} else if (paymentOption == "Credit Card") {
			// view existing
			// add new card
			addCreditCard();
			// break;

		} else if (paymentOption == "Debit Card") {

			addDebitCard();
			// break;
		} else {
			System.out.println("Please select the correct option");
		}
	}

	void addCreditCard() {
		verifyCard();
		// void addCard();

	}

	void addDebitCard() {

	}

	void verifyCard() {
		try {
			System.out.println("Enter the card number");
			String cardNumber = sc.nextLine();
			if (cardNumber.length() <= 19) {
				System.out.println("Valid  card entered");

				System.out.println("Confirm to add the above entered card (Y/N): " + cardNumber);
				String confirmation = sc.nextLine();
				boolean invalidMonth = false;
				try {
					int month, year;
					do {
						System.out.println("Enter the month for expiration");
						month = sc.nextInt();
						if (month >= 1 && month <= 12) {
							invalidMonth = true;
							System.out.println("Invalid number for month");
//							System.out.println("Enter the month between 1 to 12");
						} else {
							invalidMonth = false;
						}

					} while (invalidMonth == true);

					System.out.println("Enter the year for expiration");
					year = sc.nextInt();

					Date date = new Date(year);

					if (year < date.getYear()) {
						System.out.println("The card has been expired");
					}
				} catch (Exception e) {
					System.out.println("Please enter valid numbers: ");
					verifyCard();
				}
			} else {
				System.out.println("Invalid card");
//				System.out.println("Kindly enter the card number again: ");
				verifyCard();
			}
		} catch (

		StringIndexOutOfBoundsException e) {
			System.out.println("String index out of bounds exception");
		}
	}
}
