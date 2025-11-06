package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RuleHibernate {

    private final SessionFactory sf;

    public List<Rule> findAll() {
        var session = sf.getCurrentSession();
        return session.createQuery("FROM Rule", Rule.class).list();
    }

    public Optional<Rule> findById(int id) {
        var session = sf.getCurrentSession();
        Rule rule = session.get(Rule.class, id);
        return Optional.ofNullable(rule);
    }

    public List<Rule> findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        var session = sf.getCurrentSession();
        return session.createQuery("FROM Rule WHERE id IN :ids", Rule.class)
                .setParameter("ids", ids)
                .list();
    }
}