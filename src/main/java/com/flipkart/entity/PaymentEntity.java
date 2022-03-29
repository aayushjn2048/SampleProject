/**
 * 
 */
package com.flipkart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HP
 *
 */
@Entity
@Table(name = "transactions")
public class PaymentEntity {
	@Id
	@Column(name = "transactionId")
	private @Getter @Setter int paymentId; 
	
	@Column(name = "payerId")
	private @Getter @Setter int payerId;
	
	@Column(name = "payeeId")
	private @Getter @Setter int payeeId;
	
	@Column(name = "amount")
	private @Getter @Setter int amount;
}
