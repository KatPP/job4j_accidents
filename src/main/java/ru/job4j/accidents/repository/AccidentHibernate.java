package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccidentHibernate {

    private final SessionFactory sf;

    public Accident create(Accident accident) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(accident);
            tx.commit();
            return accident;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public boolean update(Accident accident) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(accident);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Optional<Accident> findById(int id) {
        Session session = sf.openSession();
        try {
            return Optional.ofNullable(
                    session.createQuery(
                                    "SELECT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :id",
                                    Accident.class
                            )
                            .setParameter("id", id)
                            .uniqueResult()
            );
        } finally {
            session.close();
        }
    }

    public List<Accident> findAll() {
        Session session = sf.openSession();
        try {
            return session.createQuery(
                    "SELECT a FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules",
                    Accident.class
            ).list();
        } finally {
            session.close();
        }
    }
}