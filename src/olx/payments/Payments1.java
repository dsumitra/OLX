package olx.payments;

import java.util.Scanner;

public class Payments1 {

	public interface Payment {
		void verifyCard();
	}

	// Visa card starts with 4 and has a length between 13 to 19
	class Visa implements Payment {
		public void verifyCard() {
			try {
//				LuhnCheck lc = new LuhnCheck();
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the card details");
				String cardNumber = sc.nextLine();
//				if (cardNumber.length() <= 19)) {
					// card is valid for 4 and has a length between 13 to 19
//					System.out.println("correct start value and length");
					// Needs to validate for luhn's algorithm
//					if (lc.checkLuhn(cardNumber)) {
						System.out.println("Valid visa card entered");
						CardDetails cd = new CardDetails();
						cd.card();
					} else {
						System.out.println("Invalid visa card");
					}
				} else {
					System.out.println("card not accepted");
					verifyCard();
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("String index out of bounds exception");
			}
		}
	}

	// American Express starts with 34 or 37 and has a length of 15 digits
	class AmericanExpress implements Payment {
		public void verifyCard() {
			try {
				LuhnCheck lc = new LuhnCheck();
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the card details");
				String cardNumber = sc.nextLine();
				if (cardNumber.substring(0, 2).equals("34")
						|| cardNumber.substring(0, 2).equals("37") && (cardNumber.length() <= 15)) {
					// American Express starts with 34 or 37 and has a length of 15 digits
					System.out.println("correct checksum and length");
					// Needs to validate for luhn's algorithm
					if (lc.checkLuhn(cardNumber)) {
						System.out.println("Valid AmericanExpress card entered");
						CardDetails cd = new CardDetails();
						cd.card();
					} else {
						System.out.println("Invalid AmericanExpress card");
					}
				} else {
					System.out.println("card not accepted");
					verifyCard();
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("String index out of bounds exception");
			}
		}
	}

	// Master card starts with 51, 52, 53, 54, 55 and has a length of 16 digits
	class Master implements Payment {
		public void verifyCard() {
			try {
				LuhnCheck lc = new LuhnCheck();
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter the card details");
				String cardNumber = sc.nextLine();
				if ((cardNumber.substring(0, 2).equals("51") || cardNumber.substring(0, 2).equals("52")
						|| cardNumber.substring(0, 2).equals("53") || cardNumber.substring(0, 2).equals("54")
						|| cardNumber.substring(0, 2).equals("55")) && (cardNumber.length() == 16)) {
					// Master card starts with 51, 52, 53, 54, 55 and has a length of 16 digits
					System.out.println("correct checksum and length");
					// Needs to validate for luhn's algorithm
					if (lc.checkLuhn(cardNumber)) {
						System.out.println("Valid Master card entered");
						CardDetails cd = new CardDetails();
						cd.card();
					} else {
						System.out.println("Invalid Master card");
					}
				} else {
					System.out.println("Mastercard not accepted");
					verifyCard();
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("String index out of bounds exception");
			}
		}
	}

	class CardDetails {
		Scanner sc = new Scanner(System.in);
		int month, year;

		// Month and Year of expiration date is required
		void card() {
			try {
				while (true) {
					System.out.println("Enter the month for expiration");
					month = sc.nextInt();
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
				year = sc.nextInt();
				// enter the year into the database
				System.out.println("Year has been updated");
			} catch (Exception e) {
				System.out.println("exception caught");
			}
			// Card Verification Value(CVV)
			// System.out.println("Enter the CVV for authentication");
			// int cvv=sc.nextInt();
		}
	}


	class CreditCard {
		Scanner sc = new Scanner(System.in);

		void addCreditCard() {
			// Need to verify the credit card number
			System.out.println("Please select one of the credit card as your payment option");
			System.out.println("1. Visa card");
			System.out.println("2. American Express card");
			System.out.println("3. Master card");
			int card_option = sc.nextInt();
			if (card_option == 1) {
				Visa v = new Visa();
				v.verifyCard();
			} else if (card_option == 2) {
				AmericanExpress ae = new AmericanExpress();
				ae.verifyCard();
			} else if (card_option == 3) {
				Master m = new Master();
				m.verifyCard();
			} else {
				System.out.println("Invalid option provided");
				System.out.println("Please select from the below options");
				addCreditCard();
			}

		}

		void updateCreditCard() {
			// check the credit card in the DB
		}
	}

	class DebitCard {
		Scanner sc = new Scanner(System.in);
		// Month and Year of expiration date is required

		void addDebitCard() {
			try {
				System.out.println("Please select one of the debit card as your payment option");
				System.out.println("1. Visa card");
				System.out.println("2. American Express card");
				System.out.println("3. Master card");
				int card_option = sc.nextInt();
				if (card_option == 1) {
					Visa v = new Visa();
					v.verifyCard();
				} else if (card_option == 2) {
					AmericanExpress ae = new AmericanExpress();
					ae.verifyCard();
				} else if (card_option == 3) {
					Master m = new Master();
					m.verifyCard();
				} else {
					System.out.println("Invalid option provided");
					System.out.println("Please select from the below options");
					addDebitCard();
				}
			} catch (Exception e) {
				System.out.println("exception caught");
			}
		}

		void updateDebitCard() {}
			//check the credit card in the DB

}