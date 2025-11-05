package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeJdbcRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeJdbcRepository accidentTypeRepository;

    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    public Optional<AccidentType> getById(int id) {
        return accidentTypeRepository.findById(id);
    }
}