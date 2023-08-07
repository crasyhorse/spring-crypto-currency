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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CryptoCurrencyController {

    private final Settings settings;

    private final List<Currency> cryptoCurrencies;

    private final List<Currency> physicalCurrencies;

    private final AlphaVantageRESTConsumerService service;

    @Autowired
    public CryptoCurrencyController(Settings settings, List<Currency> cryptoCurrencies, List<Currency> physicalCurrencies, AlphaVantageRESTConsumerService service) {
        this.settings = settings;
        this.cryptoCurrencies = cryptoCurrencies;
        this.physicalCurrencies = physicalCurrencies;
        this.service = service;
    }

    @GetMapping(value = "/")
    public String index(@ModelAttribute("entities") JsonSerializableMap<String, Entity> entities, Model model) {
        model.addAttribute("settings", settings);
        model.addAttribute("cryptoCurrencies", cryptoCurrencies);
        model.addAttribute("physicalCurrencies", physicalCurrencies);

        if (entities != null) {
            try {
                String serializedEntities = entities.serialize();
                model.addAttribute("source", serializedEntities);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return "/index";
    }

    @PostMapping(value = "/refresh")
    public String refreshSettings(@ModelAttribute Settings settings, RedirectAttributes redirectAttributes) {
        JsonSerializableMap<String, Entity> entities = service.consumeRESTApi(settings);

        redirectAttributes.addFlashAttribute("entities", entities);
        return "redirect:/";
    }
}
