package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeRepository {

    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public AccidentTypeRepository() {
        init();
    }

    private void init() {
        types.put(nextId.getAndIncrement(), new AccidentType(1, "Две машины"));
        types.put(nextId.getAndIncrement(), new AccidentType(2, "Машина и человек"));
        types.put(nextId.getAndIncrement(), new AccidentType(3, "Машина и велосипед"));
    }

    public Iterable<AccidentType> findAll() {
        return types.values();
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }
}