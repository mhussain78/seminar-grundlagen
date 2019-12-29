package com.mhussain.studium.seminargrundlagen;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class SeminarteilnehmerService {

    private final RowMapper<Seminarteilnehmer> rowMapper = (rs, i) -> new Seminarteilnehmer(rs.getLong("ID"),
            rs.getLong("MATRIKEL_NUMMER"),
            rs.getString("NAME"),
            rs.getString("EMAIL"),
            rs.getString("SEMINAR"));

    private final JdbcTemplate jdbcTemplate;

    public Optional<Seminarteilnehmer> findByMatrikelNummer(Long matrikelNummer) {
        String sql = "SELECT * FROM SEMINARTEILNEHMER WHERE MATRIKEL_NUMMER = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this.rowMapper, matrikelNummer));
    }

    public Collection<Seminarteilnehmer> findAll() {
        return jdbcTemplate.query("SELECT * FROM SEMINARTEILNEHMER", rowMapper);
    }

    public void save(Collection<Seminarteilnehmer> seminarteilnehmers) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        for (Seminarteilnehmer seminarteilnehmer : seminarteilnehmers) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO SEMINARTEILNEHMER (MATRIKEL_NUMMER, NAME, EMAIL, SEMINAR) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, seminarteilnehmer.getMatrikelNummer());
                ps.setString(2, seminarteilnehmer.getName());
                ps.setString(3, seminarteilnehmer.getEmail());
                ps.setString(4, seminarteilnehmer.getSeminar());
                return ps;
            }, keyHolder);
            long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            seminarteilnehmer.setId(id);
        }
    }

}