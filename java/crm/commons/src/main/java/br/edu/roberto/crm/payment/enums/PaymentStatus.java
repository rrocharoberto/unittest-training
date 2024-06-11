package br.edu.roberto.crm.payment.enums;

public enum PaymentStatus {
	PENDING(0), PAID(1), CANCELED(2);

	private final int value;

	PaymentStatus(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
