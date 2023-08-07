# Spring MVC - Crypto Currency exercise

This is a simple Spring Boot MVC software that consumes a stock API to make charts of daily, weekly or monthly historical time series for a digital currency

## How to start?

### Get ready for the exercise

Just clone this repo like this:

```
git clone https://github.com/java-2023-03/java-spring-crypto-currency.git java-spring-crypto-currency/exercise
```

This will give you the code base you need to start this exercise.

### Let's start

1. Add an additional dependency to your ```pom.xml```: ```com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.14.3```

2. Create a new Spring Boot Application (Class) named ```CryptoCurrencyChartsApplication```! It should have a ```main``` method that starts the application!

3. Create an Enumerator named ```de.brightslearning.currency.crypto.model.Interval```! It should

* have these constants:

```java
DAILY("Daily", "DIGITAL_CURRENCY_DAILY"), 
WEEKLY("Weekly", "DIGITAL_CURRENCY_WEEKLY"), 
MONTHLY("Monthly", "DIGITAL_CURRENCY_MONTHLY")
```

* a constructor that takes the arguments ```String displayValue, String function```
* getter methods for ```displayValue``` and ```function```

If you have never created an enum before 

* you may want to read this: https://www.geeksforgeeks.org/enum-in-java/
* or you may want to just copy the code of ```de.brightslearning.currency.crypto.model.Interval```

<details>
    <summary>de.brightslearning.currency.crypto.model.Interval</summary>

    package de.brightslearning.currency.crypto.model;

    public enum Interval {
        
        DAILY("Daily", "DIGITAL_CURRENCY_DAILY"), 
        WEEKLY("Weekly", "DIGITAL_CURRENCY_WEEKLY"), 
        MONTHLY("Monthly", "DIGITAL_CURRENCY_MONTHLY");

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
</details>

4. Create a class named ```de.brightslearning.currency.crypto.model.Settings```! It should

* have four private attributes
    * ```String cryptoCurrency```
    * ```String physicalCurrency```
    * ```Interval interval```
    * ```String apiKey```
* have all the necessary constructors
* getters and setters for all of its attributes
* a default ```toString``` method

5. Create a class named ```de.brightslearning.currency.crypto.model.Currency```! It should

* have two private attributes
    * ```String currencyCode```
    * ```String currencyName```
* have all the necessary constructors
* getters and setters for all of its attributes
* a default ```toString``` method
* implement the interface ```Comparable``` like this: ```Comparable<Currency>```
* have a ```compareTo``` method that compares two ```Currency``` objects

If you have never wrote a ```compareTo``` method 

* you may want to read this:
* or you may want to just copy the code

<details>
    <summary>compareTo</summary>

    @Override
    public int compareTo(Currency currency) {
        return this.getCurrencyCode().compareTo(currency.getCurrencyCode());
    }
</details>

6. Amend the two properties ```currencyCode``` and ```CurrencyName``` with the ```@JsonProperty``` annotation (```com.fasterxml.jackson.annotation.JsonProperty```) like this:

```java
    @JsonProperty("currency code")
    private String currencyCode;

    @JsonProperty("currency name")
    private String currencyName;
```
7. The ```Currency``` class is used as record when the two files ```src/main/resources/digital_currency_list.csv``` and ```src/main/resources/physical_currency_list.csv``` are read. Can you imagine what the ```@JsonPropery``` annotation in the ```Currency``` class does?

Hint: Have a look at one of these two files!

8. Create a class named ```de.brightslearning.currency.crypto.model.json.Entity```! It should

* have four private attributes
    * ```Double open```
    * ```Double high```
    * ```Double low```
    * ```Double close```
* have all the necessary constructors
* getters and setters for all of its attributes

9. Open the ```de.brightslearning.currency.crypto.service.AlphaVantageRESTConsumerService``` service bean! Add a private property named ```url``` of type String and annotate it with the ```@Value``` annotation (```org.springframework.beans.factory.annotation.Value```) like this:

```java
    @Value("${alphavantage.rest.api.url}")
```

10. Have a look a the file ```src/main/resources/application.properties```! Can you figure out what the ```@Value("${alphavantage.rest.api.url}")``` annotation does?

11. Open the ```de.brightslearning.currency.crypto.Beans``` class! This class should serve as an additional configuration for your Spring Boot Application.

<details>
    <summary>Don't know what to do? Here is the hint!</summary>

    package de.brightslearning.currency.crypto;

    ...

    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class Beans {
</details>

12. Add two private properties to the ```de.brightslearning.currency.crypto.Beans``` class:

* ```Resource digitalCurrencyList```
* ```Resource physicalCurrencyList```

(```org.springframework.core.io.Resource```)

13. Annotate the ```digitalCurrencyList``` property with the ```@Value``` annotation (```org.springframework.beans.factory.annotation.Value```) like this:

```java
    @Value("classpath:/digital_currency_list.csv")
```

Can you figure out what the ```@Value("classpath:/digital_currency_list.csv")``` annotation does?

14. Annotate the ```physicalCurrencyList``` property with the ```@Value``` annotation (```org.springframework.beans.factory.annotation.Value```) like this:

```java
    @Value("classpath:/physical_currency_list.csv")
```

15. In the ```de.brightslearning.currency.crypto.Beans``` class create  the ```public Settings settings()``` method! It should return the ```de.brightslearning.currency.crypto.model.Settings``` class as a bean! Use the empty construtor of ```de.brightslearning.currency.crypto.model.Settings``` to initialize it!

16. In the ```de.brightslearning.currency.crypto.Beans``` class create  the ```cryptoCurrencies``` method! It should

* be ```public```
* return a ```List``` of ```Currency``` objects
* use the method ```createCurrencieList``` together with the ```digitalCurrencyList``` property to create the list and return it

<details>
    <summary>Don't know what to do? Here is the hint!</summary>

    @Bean
    public List<Currency> cryptoCurrencies() {
        return createCurrencieList(digitalCurrencyList);
    }
</details>

17. In the ```de.brightslearning.currency.crypto.Beans``` class create  the ```physicalCurrencies``` method! It should

* be ```public```
* return a ```List``` of ```Currency``` objects
* use the method ```createCurrencieList``` together with the ```physicalCurrencyList``` property to create the list and return it

<details>
    <summary>Don't know what to do? Here is the hint!</summary>

    @Bean
    public List<Currency> physicalCurrencies() {
        return createCurrencieList(physicalCurrencyList);
    }
</details>

18. Open the controller class ```de.brightslearning.currency.crypto.controller.CryptoCurrencyController```! Add the following private properties to the class:

* ```Settings settings```
* ```List<Currency> cryptoCurrencies```
* ```List<Currency> physicalCurrencies```
* ```AlphaVantageRESTConsumerService service```

19. The four properties ```settings```, ```cryptoCurrencies```, ```physicalCurrencies``` and ```service``` should be injected via a constructor!

20. The ```index``` method should

* respond to a GET request to ```/```
* take the ```@ModelAttribute``` ```entities``` of type ```JsonSerializableMap<String, Entity>``` from the request 
* add the properties ```settings```, ```cryptoCurrencies``` and ```physicalCurrencies``` to the ```Model``` (do this before the ```if (entities != null)``` condition!)
* return the template ```/index```

21. Create the ```refreshSettings``` method! It should

* respond to a POST request to ```/refresh```
* take the ```@ModelAttribute``` ```settings``` of type ```Settings``` from the request
* take ```redirectAttributes``` as an argument of type ```RedirectAttributes```
* consume the AlphaVantage REST-API like this:

```java
entities = service.consumeRESTApi(settings);
```
* add the entities as flash attribute (```addFlashAttribute()```) to the ```redirectAttributes``` (name: ```entities```)
* redirect to ```/```

END 

## How to get the solution?

### Clone the solution
You are done with this exercise? All you have to do is to clone the "solution" branch of this repo.

```
git clone --branch solution https://github.com/java-2023-03/java-spring-crypto-currency.git java-spring-crypto-currency/solution

```

### Switch to the solution

If you just want to switch from your local files to the solution you should

1. Stash your local changes

```
git stash
```

2. Switch to the "solution" branch

```
git switch solution
```

If you want to get your local changes back you should

1. Switch back to the "master" branch

```
git switch master
```

2. Unstash your local changes

```
git stash pop
```
