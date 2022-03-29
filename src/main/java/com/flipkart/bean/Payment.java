package com.flipkart.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public @Data class Payment {
	private @Getter @Setter int payerId;
	private @Getter @Setter int payeeId;
	private @Getter @Setter int amount;
}
