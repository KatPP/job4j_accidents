package ru.job4j.accidents.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    public AccidentMem(AccidentTypeService accidentTypeService, RuleService ruleService) {
        this.accidentTypeService = accidentTypeService;
        this.ruleService = ruleService;
    }

    @PostConstruct
    public void init() {
        // Получаем типы и статьи через сервисы
        var type1 = accidentTypeService.getById(1).orElseThrow();
        var type2 = accidentTypeService.getById(2).orElseThrow();
        var type3 = accidentTypeService.getById(3).orElseThrow();

        var rule1 = ruleService.getById(1).orElseThrow();
        var rule2 = ruleService.getById(2).orElseThrow();
        var rule3 = ruleService.getById(3).orElseThrow();

        Set<Rule> rules1 = Set.of(rule1, rule2);
        Set<Rule> rules2 = Set.of(rule2);
        Set<Rule> rules3 = Set.of(rule3);

        accidents.put(nextId.getAndIncrement(),
                new Accident(1, "ДТП на перекрёстке", "Столкновение", "ул. Ленина, 10", type1, rules1));
        accidents.put(nextId.getAndIncrement(),
                new Accident(2, "Наезд на пешехода", "Пешеход в больнице", "пр. Мира, 25", type2, rules2));
        accidents.put(nextId.getAndIncrement(),
                new Accident(3, "Велосипедист упал", "Без пострадавших", "ул. Советская, 5", type3, rules3));
    }

    public Iterable<Accident> findAll() {
        return accidents.values();
    }

    public void save(Accident accident) {
        restoreFullType(accident);
        restoreFullRules(accident);
        if (accident.getId() == 0) {
            accident.setId(nextId.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    public boolean update(Accident accident) {
        restoreFullType(accident);
        restoreFullRules(accident);
        var updated = accidents.computeIfPresent(accident.getId(), (k, v) -> accident);
        return updated != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    private void restoreFullType(Accident accident) {
        if (accident.getType() != null && accident.getType().getId() != null) {
            accidentTypeService.getById(accident.getType().getId())
                    .ifPresent(accident::setType);
        }
    }

    private void restoreFullRules(Accident accident) {
        if (accident.getRules() != null) {
            Set<Rule> fullRules = accident.getRules().stream()
                    .filter(rule -> rule.getId() != 0)
                    .map(rule -> ruleService.getById(rule.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            accident.setRules(fullRules);
        }
    }
}