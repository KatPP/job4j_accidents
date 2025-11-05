package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AccidentJdbcRepository {

    private final JdbcTemplate jdbc;
    private final AccidentTypeJdbcRepository typeRepository;
    private final RuleJdbcRepository ruleRepository;

    public Accident save(Accident accident) {
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO accidents (name, text, address, type_id) VALUES (?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType() != null ? accident.getType().getId() : 0);
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        accident.setId(id);

        // Сохраняем связи со статьями
        if (accident.getRules() != null && !accident.getRules().isEmpty()) {
            String sql = "INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)";
            for (Rule rule : accident.getRules()) {
                jdbc.update(sql, id, rule.getId());
            }
        }

        return accident;
    }

    public boolean update(Accident accident) {
        // Удаляем старые связи
        jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", accident.getId());

        int updated = jdbc.update(
                "UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType() != null ? accident.getType().getId() : 0,
                accident.getId()
        );

        if (accident.getRules() != null && !accident.getRules().isEmpty()) {
            String sql = "INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)";
            for (Rule rule : accident.getRules()) {
                jdbc.update(sql, accident.getId(), rule.getId());
            }
        }

        return updated > 0;
    }

    public Optional<Accident> findById(int id) {
        List<Accident> result = jdbc.query(
                "SELECT id, name, text, address, type_id FROM accidents WHERE id = ?",
                (rs, rowNum) -> {
                    Accident a = new Accident();
                    a.setId(rs.getInt("id"));
                    a.setName(rs.getString("name"));
                    a.setText(rs.getString("text"));
                    a.setAddress(rs.getString("address"));

                    int typeId = rs.getInt("type_id");
                    if (typeId != 0) {
                        a.setType(typeRepository.findById(typeId).orElse(null));
                    }

                    List<Rule> rules = jdbc.query(
                            "SELECT r.id, r.name FROM accident_rule ar "
                                    + "JOIN rules r ON ar.rule_id = r.id "
                                    + "WHERE ar.accident_id = ?",
                            (rs2, rowNum2) -> new Rule(rs2.getInt("id"), rs2.getString("name")),
                            a.getId()
                    );
                    a.setRules(new HashSet<>(rules));

                    return a;
                },
                id
        );
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Accident> findAll() {
        return jdbc.query(
                "SELECT id, name, text, address, type_id FROM accidents ORDER BY id",
                (rs, rowNum) -> {
                    Accident a = new Accident();
                    a.setId(rs.getInt("id"));
                    a.setName(rs.getString("name"));
                    a.setText(rs.getString("text"));
                    a.setAddress(rs.getString("address"));

                    int typeId = rs.getInt("type_id");
                    if (typeId != 0) {
                        a.setType(typeRepository.findById(typeId).orElse(null));
                    }

                    List<Rule> rules = jdbc.query(
                            "SELECT r.id, r.name FROM accident_rule ar "
                                    + "JOIN rules r ON ar.rule_id = r.id "
                                    + "WHERE ar.accident_id = ?",
                            (rs2, rowNum2) -> new Rule(rs2.getInt("id"), rs2.getString("name")),
                            a.getId()
                    );
                    a.setRules(new HashSet<>(rules));

                    return a;
                }
        );
    }
}