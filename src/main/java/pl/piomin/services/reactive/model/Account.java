package pl.piomin.services.reactive.model;

public class Account {

	private Integer id;
	private Integer customerId;
	private String number;
	private int balance;

	public Account() {

	}

	public Account(Integer id, Integer customerId, String number, int balance) {
		this.id = id;
		this.customerId = customerId;
		this.number = number;
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return String.format("Account[id=%d]", id);
	}

}
