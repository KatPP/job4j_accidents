package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RuleJdbcRepository {

    private final JdbcTemplate jdbc;

    public List<Rule> findAll() {
        return jdbc.query(
                "SELECT id, name FROM rules ORDER BY id",
                (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name"))
        );
    }

    public Optional<Rule> findById(int id) {
        return jdbc.query(
                "SELECT id, name FROM rules WHERE id = ?",
                (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name")),
                id
        ).stream().findFirst();
    }

    public List<Rule> findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        String placeholders = ids.stream().map(id -> "?").collect(Collectors.joining(","));
        String sql = "SELECT id, name FROM rules WHERE id IN (" + placeholders + ")";
        return jdbc.query(sql, (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name")),
                ids.toArray());
    }
}