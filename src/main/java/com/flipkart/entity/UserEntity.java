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
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HP
 *
 */

@Entity
@Table(name = "user")
public class UserEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  @Getter @Setter Integer id;
	
	@Column(name = "username")
	private  @Getter @Setter String username;
	
	@Column(name = "password")
	private  @Getter @Setter String password;
	
	@Column(name = "name")
	private  @Getter @Setter String name;
	
	@Column(name = "contactNo")
	private  @Getter @Setter String contactNo;
	
	@Column(name = "balance")
	private  @Getter @Setter int balance;
	
	@Version
	@Column(name = "version")
	private @Getter @Setter int version;

}
