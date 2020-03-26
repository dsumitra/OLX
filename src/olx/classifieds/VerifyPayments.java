package olx.classifieds;

import java.util.Scanner;

public class VerifyPayments {

	Scanner sc= new Scanner(System.in);
	

	
	boolean checkLuhn(String cardNo) {
		int nDigits = cardNo.length();
		int nSum = 0;
		boolean isSecond = false;
		for (int i = nDigits - 1; i >= 0; i--) {
			int d = cardNo.charAt(i) - '0';
			if (isSecond == true)
				d = d * 2;
			// We add two digits to handle
			// cases that make two digits
			// after doubling
			nSum += d / 10;
			nSum += d % 10;
			isSecond = !isSecond;
		}
		return (nSum % 10 == 0);
	}

	void verifyCard(cardnUMBER) {
		try {
			Scanner sc = new Scanner(System.in);
			String cardNumber = sc.nextLine();
			if (cardNumber.substring(0, 1).equals("4")
					&& (cardNumber.length() >= 13 && cardNumber.length() <= 19)) {
				// card is valid for 4 and has a length between 13 to 19
				System.out.println("correct start value and length");
				// Needs to validate for luhn's algorithm
				if (checkLuhn(cardNumber)) {
					System.out.println("Valid visa card entered");
					CardDetails cd = new CardDetails();
					cd.card();
				} else {
					System.out.println("Invalid visa card");
				}
			} else {
				System.out.println("card not accepted");
				verifyVisaCard();
			}
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("String index out of bounds exception");
		}
	}


	void verifiyMasterCard() {

	}

	void verifiyAmericanExpressCard() {

	}
	
	void cardDetails() {
		try {
			while (true) {
				System.out.println("Enter the month for expiration");
				 int month = sc.nextInt();
				if (month >= 1 && month <= 12) {
					System.out.println("Month has been validated");
					// enter the month into the database
					break;
				} else {
					System.out.println("Invalid number for month");
					System.out.println("Enter the month between 1 to 12");
				}
			}
			System.out.println("Enter the year for expiration");
			int year = sc.nextInt();
			// enter the year into the database
			System.out.println("Year has been updated");
		} catch (Exception e) {
			System.out.println("exception caught");
		}

}
