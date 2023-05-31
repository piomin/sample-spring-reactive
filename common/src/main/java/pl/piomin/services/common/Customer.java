package pl.piomin.services.common;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String pesel;
    private List<Account> accounts = new ArrayList<>();

    public Customer() {

    }

    public Customer(String id, String firstName, String lastName, String pesel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public Customer(String pesel, List<Account> accounts) {
        this.pesel = pesel;
        this.accounts.addAll(accounts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void addAccount(Account a) {
        accounts.add(a);
    }

    public void addAccounts(List<Account> a) {
        accounts.addAll(a);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, pesel=%s]", id, pesel);
    }

}
