package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleHibernate ruleHibernate;

    @Transactional(readOnly = true)
    public List<Rule> findAll() {
        return ruleHibernate.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Rule> getById(int id) {
        return ruleHibernate.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Rule> findByIds(Set<Integer> ids) {
        return ruleHibernate.findByIds(ids);
    }
}