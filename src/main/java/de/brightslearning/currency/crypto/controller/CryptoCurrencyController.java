package de.brightslearning.currency.crypto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.brightslearning.currency.crypto.model.Currency;
import de.brightslearning.currency.crypto.model.Settings;
import de.brightslearning.currency.crypto.model.json.Entity;
import de.brightslearning.currency.crypto.model.json.JsonSerializableMap;
import de.brightslearning.currency.crypto.service.AlphaVantageRESTConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CryptoCurrencyController {

    @GetMapping(value = "/")
    public String index(Model model) {
        if (entities != null) {
            try {
                String serializedEntities = entities.serialize();
                model.addAttribute("source", serializedEntities);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostMapping(value = "/refresh")
    public String refreshSettings(@ModelAttribute Settings settings) {
        entities = service.consumeRESTApi(settings);

        return "redirect:/";
    }
}
