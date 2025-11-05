package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentJdbcRepository accidentRepository;

    public void create(Accident accident) {
        accidentRepository.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    public Optional<Accident> getById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }
}