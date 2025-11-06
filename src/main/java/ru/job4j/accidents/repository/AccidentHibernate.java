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
        session.save(accident);
        return accident;
    }

    public boolean update(Accident accident) {
        var session = sf.getCurrentSession();
        session.update(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        var session = sf.getCurrentSession();
        Accident accident = session.get(Accident.class, id);
        return Optional.ofNullable(accident);
    }

    public List<Accident> findAll() {
        var session = sf.getCurrentSession();
        return session.createQuery("FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules", Accident.class).list();
    }
}