package ru.job4j.accidents.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentTypeService;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AccidentTypeService accidentTypeService;

    public WebConfig(AccidentTypeService accidentTypeService) {
        this.accidentTypeService = accidentTypeService;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, AccidentType.class, id -> {
            if (id == null || id.isEmpty() || "null".equals(id)) {
                return null;
            }
            return accidentTypeService.getById(Integer.valueOf(id))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type id: " + id));
        });
    }
}