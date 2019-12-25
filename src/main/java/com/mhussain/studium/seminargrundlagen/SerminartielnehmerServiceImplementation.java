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
public class SerminartielnehmerServiceImplementation implements SeminarteilmehmerService {

    private final RowMapper<Seminarteilmehmer> rowMapper = (rs, i) -> new Seminarteilmehmer(rs.getLong("ID"),
            rs.getLong("MATRIKEL_NUMMER"),
            rs.getString("NAME"),
            rs.getString("EMAIL"),
            rs.getString("SEMINAR"));

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Seminarteilmehmer> findByMatrikelNummer(Long matrikelNummer) {
        String sql = "SELECT * FROM SEMINARTEILMEHMER WHERE MATRIKEL_NUMMER = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this.rowMapper, matrikelNummer));
    }

    @Override
    public Collection<Seminarteilmehmer> findAll() {
        return jdbcTemplate.query("SELECT * FROM SEMINARTEILMEHMER", rowMapper);
    }

    @Override
    public void save(Collection<Seminarteilmehmer> seminarteilmehmers) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        for (Seminarteilmehmer seminarteilmehmer : seminarteilmehmers) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO SEMINARTEILMEHMER (MATRIKEL_NUMMER, NAME, EMAIL, SEMINAR) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, seminarteilmehmer.getMatrikelNummer());
                ps.setString(2, seminarteilmehmer.getName());
                ps.setString(3, seminarteilmehmer.getEmail());
                ps.setString(4, seminarteilmehmer.getSeminar());
                return ps;
            }, keyHolder);
            long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
            seminarteilmehmer.setId(id);
        }
    }

}