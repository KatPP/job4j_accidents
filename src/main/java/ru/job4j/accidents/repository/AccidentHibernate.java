package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccidentHibernate {

    private final SessionFactory sf;

    public Accident create(Accident accident) {
        var session = sf.getCurrentSession();
        session.doWork(connection -> {
            // Можно добавить логику, если нужно
        });
        session.save(accident);
        return accident;
    }

    public boolean update(Accident accident) {
        var session = sf.getCurrentSession();
        session.doWork(connection -> {
            // Можно добавить логику, если нужно
        });
        session.update(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        var session = sf.getCurrentSession();
        return Optional.ofNullable(session.createQuery(
                        "SELECT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :id",
                        Accident.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    public List<Accident> findAll() {
        var session = sf.getCurrentSession();
        return session.createQuery(
                        "SELECT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules",
                        Accident.class)
                .list();
    }
}