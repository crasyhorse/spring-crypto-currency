package de.brightslearning.currency.crypto;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.brightslearning.currency.crypto.model.Currency;
import de.brightslearning.currency.crypto.model.Interval;
import de.brightslearning.currency.crypto.model.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class Beans {

    @Value("classpath:/digital_currency_list.csv")
    Resource digitalCurrencyList;

    @Value("classpath:/physical_currency_list.csv")
    Resource physicalCurrencyList;

    private final CsvSchema schema;

    private final CsvMapper mapper;

    public Beans() {
        mapper = new CsvMapper();
        schema = CsvSchema.builder().setSkipFirstDataRow(true).setUseHeader(false).addColumn("currency code").addColumn("currency name").build();
    }

    @Bean
    public Settings settings() {
        return new Settings("BTC", "EUR", Interval.WEEKLY);
    }

    @Bean
    public List<Currency> cryptoCurrencies() {
        return createCurrencieList(digitalCurrencyList);
    }

    @Bean
    public List<Currency> physicalCurrencies() {
        return createCurrencieList(physicalCurrencyList);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private List<Currency> createCurrencieList(Resource resource) {
        List<Currency> currencies;

        try {
            InputStream stream = resource.getInputStream();
            MappingIterator<Currency> it = mapper.readerFor(Currency.class).with(schema).readValues(stream);
            currencies = it.readAll();

            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return currencies;
    }
}
