package pl.krzystooof.sklepallegro.data;

public class Price {
    private double amount;
    private String currency;

    public Price(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price() {
        amount = 0;
        currency = "";
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
