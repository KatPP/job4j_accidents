package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class RuleRepository {

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public RuleRepository() {
        init();
    }

    private void init() {
        rules.put(nextId.getAndIncrement(), new Rule(1, "Статья. 1"));
        rules.put(nextId.getAndIncrement(), new Rule(2, "Статья. 2"));
        rules.put(nextId.getAndIncrement(), new Rule(3, "Статья. 3"));
    }

    public Iterable<Rule> findAll() {
        return rules.values();
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public List<Rule> findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return ids.stream()
                .map(rules::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}