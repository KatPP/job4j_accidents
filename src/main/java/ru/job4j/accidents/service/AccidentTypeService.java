package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;;

@Service
public class AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    public AccidentTypeService(AccidentTypeRepository accidentTypeRepository) {
        this.accidentTypeRepository = accidentTypeRepository;
    }

    public List<AccidentType> findAll() {
        return (List<AccidentType>) accidentTypeRepository.findAll();
    }

    public Optional<AccidentType> getById(int id) {
        return accidentTypeRepository.findById(id);
    }
}