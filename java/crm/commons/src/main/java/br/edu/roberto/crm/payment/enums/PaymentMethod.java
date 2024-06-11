package br.edu.roberto.crm.payment.enums;

public enum PaymentMethod {
	CARD(0), BANK_TRANSFER(1), DIGITAL_WALLET(2);

	private final int value;

	PaymentMethod(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
