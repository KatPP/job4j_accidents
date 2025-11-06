package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeHibernate accidentTypeHibernate;

    @Transactional(readOnly = true)
    public List<AccidentType> findAll() {
        return accidentTypeHibernate.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AccidentType> getById(int id) {
        return accidentTypeHibernate.findById(id);
    }
}