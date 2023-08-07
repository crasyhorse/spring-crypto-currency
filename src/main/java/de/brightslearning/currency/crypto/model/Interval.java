package de.brightslearning.currency.crypto.model;

public enum Interval {
    DAILY("Daily", "DIGITAL_CURRENCY_DAILY"), WEEKLY("Weekly", "DIGITAL_CURRENCY_WEEKLY"), MONTHLY("Monthly", "DIGITAL_CURRENCY_MONTHLY");

    private final String displayValue;

    private final String function;

    Interval(String displayValue, String function) {
        this.displayValue = displayValue;
        this.function = function;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getFunction() {
        return function;
    }
}
