package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public List<Rule> findAll() {
        return StreamSupport.stream(ruleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Rule> getById(int id) {
        return ruleRepository.findById(id);
    }

    public List<Rule> findByIds(Set<Integer> ids) {
        return ruleRepository.findByIds(ids);
    }
}