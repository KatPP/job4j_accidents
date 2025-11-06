package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentHibernate accidentHibernate;

    @Transactional
    public void create(Accident accident) {
        accidentHibernate.create(accident);
    }

    @Transactional
    public boolean update(Accident accident) {
        return accidentHibernate.update(accident);
    }

    @Transactional(readOnly = true)
    public Optional<Accident> getById(int id) {
        return accidentHibernate.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Accident> findAll() {
        return accidentHibernate.findAll();
    }
}