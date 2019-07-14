package com.fhbonfante.reactive.core.model;

public class Balance {
    private Double amount;
    private Double withdrawLimit;

    public Balance() {
    }

    public Balance(Double amount, Double withdrawLimit) {
        this.amount = amount;
        this.withdrawLimit = withdrawLimit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(Double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "amount=" + amount +
                ", withdrawLimit=" + withdrawLimit +
                '}';
    }
}
