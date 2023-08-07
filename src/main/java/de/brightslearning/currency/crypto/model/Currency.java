package de.brightslearning.currency.crypto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency implements Comparable<Currency> {

    @JsonProperty("currency code")
    private String currencyCode;

    @JsonProperty("currency name")
    private String currencyName;

    public Currency() {
    }

    public Currency(String currencyCode, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public String toString() {
        return "Currency{" + "shortName='" + currencyCode + '\'' + ", longName='" + currencyName + '\'' + '}';
    }

    @Override
    public int compareTo(Currency currency) {
        return this.getCurrencyCode().compareTo(currency.getCurrencyCode());
    }
}
