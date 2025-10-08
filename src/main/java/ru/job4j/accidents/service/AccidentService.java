package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccidentService {

    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public List<Accident> findAll() {
        return new ArrayList<>((Collection<Accident>) accidentMem.findAll());
    }
}