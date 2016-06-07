package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblcustomercard")
public class CustomerCard implements Serializable{
	public CustomerCard(){}
	public CustomerCard(String customercode, String cardnumber, String cardname, String cardexpiration){
		this.customerCode = customercode;
		this.cardNumber = cardnumber;
		this.cardName = cardname;
		this.cardExpiration = cardexpiration;
		this.active = "YES";
	}
	private static final long serialVersionUID = -2300784801800062989L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
	@Column(name = "CardNumber")
	private String cardNumber;
	
	@Column(name = "CardName")
	private String cardName;
	
	@Column(name = "CardExpiration")
	private String cardExpiration;
	
	@Column(name = "Active")
	private String active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardExpiration() {
		return cardExpiration;
	}

	public void setCardExpiration(String cardExpiration) {
		this.cardExpiration = cardExpiration;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
