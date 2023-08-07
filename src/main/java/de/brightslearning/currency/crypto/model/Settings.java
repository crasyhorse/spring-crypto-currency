package de.brightslearning.currency.crypto.model;

public class Settings {
    private String cryptoCurrency;

    private String physicalCurrency;

    private Interval interval;
    private String apiKey;

    public Settings() {
    }

    public Settings(String cryptoCurrency, String physicalCurrency, Interval interval) {
        this.cryptoCurrency = cryptoCurrency;
        this.physicalCurrency = physicalCurrency;
        this.interval = interval;
    }

    public Settings(String cryptoCurrency, String physicalCurrency, Interval interval, String apiKey) {
        this.cryptoCurrency = cryptoCurrency;
        this.physicalCurrency = physicalCurrency;
        this.interval = interval;
        this.apiKey = apiKey;
    }

    public String getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public String getPhysicalCurrency() {
        return physicalCurrency;
    }

    public void setPhysicalCurrency(String physicalCurrency) {
        this.physicalCurrency = physicalCurrency;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "Settings{" + "cryptoCurrency='" + cryptoCurrency + '\'' + ", physicalCurrency='" + physicalCurrency + '\'' + ", interval=" + interval + ", apiKey='" + apiKey + '\'' + '}';
    }
}
