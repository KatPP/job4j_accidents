package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccidentTypeHibernate {

    private final SessionFactory sf;

    public List<AccidentType> findAll() {
        Session session = sf.openSession();
        try {
            return session.createQuery("FROM AccidentType", AccidentType.class).list();
        } finally {
            session.close();
        }
    }

    public Optional<AccidentType> findById(int id) {
        Session session = sf.openSession();
        try {
            return Optional.ofNullable(session.get(AccidentType.class, id));
        } finally {
            session.close();
        }
    }
}