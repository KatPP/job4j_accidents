package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccidentTypeJdbcRepository {

    private final JdbcTemplate jdbc;

    public List<AccidentType> findAll() {
        return jdbc.query(
                "SELECT id, name FROM accident_types ORDER BY id",
                (rs, rowNum) -> new AccidentType(rs.getInt("id"), rs.getString("name"))
        );
    }

    public Optional<AccidentType> findById(int id) {
        return jdbc.query(
                "SELECT id, name FROM accident_types WHERE id = ?",
                (rs, rowNum) -> new AccidentType(rs.getInt("id"), rs.getString("name")),
                id
        ).stream().findFirst();
    }
}