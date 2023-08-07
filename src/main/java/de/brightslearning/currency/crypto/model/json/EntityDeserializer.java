package de.brightslearning.currency.crypto.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.brightslearning.currency.crypto.model.Settings;

import java.io.IOException;

public class EntityDeserializer extends StdDeserializer<Entity> {

    private static Settings settings;

    public EntityDeserializer(Class<?> vc) {
        super(vc);
    }

    public EntityDeserializer(Class<?> vc, Settings settings) {
        super(vc);
        EntityDeserializer.settings = settings;
    }

    @Override
    public Entity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Double open = node.get(String.format("1a. open (%s)", settings.getPhysicalCurrency())).asDouble();
        Double high = node.get(String.format("2a. high (%s)", settings.getPhysicalCurrency())).asDouble();
        Double low = node.get(String.format("3a. low (%s)", settings.getPhysicalCurrency())).asDouble();
        Double close = node.get(String.format("4a. close (%s)", settings.getPhysicalCurrency())).asDouble();

        return new Entity(open, high, low, close);
    }
}
