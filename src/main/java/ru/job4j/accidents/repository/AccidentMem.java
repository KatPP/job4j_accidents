package ru.job4j.accidents.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    // Справочник типов: ключ — Integer, значение — AccidentType с Integer id
    private static final Map<Integer, AccidentType> TYPES = Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    );

    @PostConstruct
    public void init() {
        accidents.put(nextId.getAndIncrement(),
                new Accident(1, "ДТП на перекрёстке", "Столкновение", "ул. Ленина, 10", TYPES.get(1)));
        accidents.put(nextId.getAndIncrement(),
                new Accident(2, "Наезд на пешехода", "Пешеход в больнице", "пр. Мира, 25", TYPES.get(2)));
        accidents.put(nextId.getAndIncrement(),
                new Accident(3, "Велосипедист упал", "Без пострадавших", "ул. Советская, 5", TYPES.get(3)));
    }

    public Iterable<Accident> findAll() {
        return accidents.values();
    }

    public void save(Accident accident) {
        restoreFullType(accident);
        if (accident.getId() == 0) {
            accident.setId(nextId.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    public boolean update(Accident accident) {
        restoreFullType(accident);
        var updated = accidents.computeIfPresent(accident.getId(), (k, v) -> accident);
        return updated != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    private void restoreFullType(Accident accident) {
        if (accident.getType() != null && accident.getType().getId() != null) {
            AccidentType fullType = TYPES.get(accident.getType().getId());
            if (fullType != null) {
                accident.setType(fullType);
            }
        }
    }
}