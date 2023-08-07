package de.brightslearning.currency.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.brightslearning.currency.crypto.model.Settings;
import de.brightslearning.currency.crypto.model.json.Entity;
import de.brightslearning.currency.crypto.model.json.EntityDeserializer;
import de.brightslearning.currency.crypto.model.json.JsonSerializableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Map;

@Service
public class AlphaVantageRESTConsumerService {

    private final RestTemplate restTemplate;

    @Value("${alphavantage.rest.api.url}")
    private String url;

    @Autowired
    public AlphaVantageRESTConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JsonSerializableMap<String, Entity> consumeRESTApi(Settings settings) {
        String json = restTemplate.getForEntity(this.buildUrl(settings), String.class).getBody();
        JsonSerializableMap<String, Entity> entities = new JsonSerializableMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Entity.class, new EntityDeserializer(Entity.class, settings));
            mapper.registerModule(module);

            JsonNode timeSeries = mapper.readTree(json).path("Time Series (Digital Currency Weekly)");

            for (Iterator<Map.Entry<String, JsonNode>> it = timeSeries.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> entityNode = it.next();

                entities.put(entityNode.getKey(), mapper.treeToValue(entityNode.getValue(), Entity.class));
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }

    private String buildUrl(Settings settings) {
        return String.format("%s?function=%s&symbol=%s&market=%s&apikey=%s", this.url, settings.getInterval().getFunction(), settings.getCryptoCurrency(), settings.getPhysicalCurrency(), settings.getApiKey());
    }
}
