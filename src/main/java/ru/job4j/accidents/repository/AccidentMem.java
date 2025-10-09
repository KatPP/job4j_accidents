package ru.job4j.accidents.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        // Добавляем тестовые данные при старте приложения
        accidents.put(nextId.getAndIncrement(), new Accident(1, "ДТП на перекрёстке", "Столкновение двух автомобилей", "ул. Ленина, 10"));
        accidents.put(nextId.getAndIncrement(), new Accident(2, "ДТП на трассе", "Выезд в кювет", "пр. Мира, 25"));
        accidents.put(nextId.getAndIncrement(), new Accident(3, "Нарушение правил парковки", "Нахождение в зоне знака парковка запрещена", "ул. Советская, 5"));
    }

    public Iterable<Accident> findAll() {
        return accidents.values();
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(nextId.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }
}