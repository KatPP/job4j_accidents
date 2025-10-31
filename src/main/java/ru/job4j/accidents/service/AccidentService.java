// src/main/java/ru/job4j/accidents/service/AccidentService.java

package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentJdbcTemplate accidentJdbc;

    public void create(Accident accident) {
        accidentJdbc.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentJdbc.update(accident);
    }

    public Optional<Accident> getById(int id) {
        return accidentJdbc.findById(id);
    }

    public List<Accident> findAll() {
        return accidentJdbc.findAll();
    }
}