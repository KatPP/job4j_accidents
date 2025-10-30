package ru.job4j.accidents.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    private static final Map<Integer, AccidentType> TYPES = Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    );

    private static final Map<Integer, Rule> RULES = Map.of(
            1, new Rule(1, "Статья. 1"),
            2, new Rule(2, "Статья. 2"),
            3, new Rule(3, "Статья. 3")
    );

    @PostConstruct
    public void init() {
        // Тестовые данные с типами и статьями
        Set<Rule> rules1 = Set.of(RULES.get(1), RULES.get(2));
        Set<Rule> rules2 = Set.of(RULES.get(2));
        Set<Rule> rules3 = Set.of(RULES.get(3));

        accidents.put(nextId.getAndIncrement(),
                new Accident(1, "ДТП на перекрёстке", "Столкновение", "ул. Ленина, 10", TYPES.get(1), rules1));
        accidents.put(nextId.getAndIncrement(),
                new Accident(2, "Наезд на пешехода", "Пешеход в больнице", "пр. Мира, 25", TYPES.get(2), rules2));
        accidents.put(nextId.getAndIncrement(),
                new Accident(3, "Велосипедист упал", "Без пострадавших", "ул. Советская, 5", TYPES.get(3), rules3));
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
            AccidentType fullType = TYPES.get(accident.getType().getId());
            if (fullType != null) {
                accident.setType(fullType);
            }
        }
    }

    private void restoreFullRules(Accident accident) {
        if (accident.getRules() != null) {
            Set<Rule> fullRules = accident.getRules().stream()
                    .filter(rule -> rule.getId() != 0)
                    .map(rule -> RULES.get(rule.getId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            accident.setRules(fullRules);
        }
    }
}