package de.brightslearning.currency.crypto.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonSerializableMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    public JsonSerializableMap() {
        super();
    }

    public String serialize() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
