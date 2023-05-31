package pl.piomin.services.common;

public class Account {

    private String id;
    private String customerId;
    private String number;
    private int amount;

    public Account() {

    }

    public Account(String id, String customerId, String number, int amount) {
        this.id = id;
        this.customerId = customerId;
        this.number = number;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%s, customer=%s]", id, customerId);
    }

}
