package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
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
        Session session = sf.openSession();
        try {
            return session.createQuery("FROM Rule", Rule.class).list();
        } finally {
            session.close();
        }
    }

    public Optional<Rule> findById(int id) {
        Session session = sf.openSession();
        try {
            return Optional.ofNullable(session.get(Rule.class, id));
        } finally {
            session.close();
        }
    }

    public List<Rule> findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        Session session = sf.openSession();
        try {
            return session.createQuery("FROM Rule WHERE id IN :ids", Rule.class)
                    .setParameter("ids", ids)
                    .list();
        } finally {
            session.close();
        }
    }
}