package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    public List<AccidentType> findAll() {
        return StreamSupport.stream(accidentTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<AccidentType> getById(int id) {
        return accidentTypeRepository.findById(id);
    }
}