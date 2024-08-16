package com.webstrdy00.schedule_management.repository;

import com.webstrdy00.schedule_management.dto.AssigneeDto.AssigneeResponseDto;
import com.webstrdy00.schedule_management.entity.Assignee;
import com.webstrdy00.schedule_management.exception.AssigneeNotFoundException;
import com.webstrdy00.schedule_management.exception.AssigneeUpdateFailedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AssigneeRepository {
    private final JdbcTemplate jdbcTemplate;

    public AssigneeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Assignee save(Assignee assignee){
        String sql = "INSERT INTO assignees (name, email, created_at, modified_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, assignee.getName());
            ps.setString(2, assignee.getEmail());
            ps.setTimestamp(3, Timestamp.valueOf(assignee.getCreatedAt()));
            ps.setTimestamp(4, Timestamp.valueOf(assignee.getModifiedAt()));
            return ps;
        }, keyHolder);

        assignee.setId(keyHolder.getKey().longValue());
        return assignee;
    }

    public Assignee findById(Long id){
        String sql = "SELECT * FROM assignees WHERE id = ?";
        Assignee assignee = jdbcTemplate.queryForObject(sql, new AssigneeRowMapper(), id);

        return assignee;
    }

    public Assignee findByEmail(String email){
        String sql = "SELECT * FROM assignees WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new AssigneeRowMapper(), email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Assignee> findAll() {
        String sql = "SELECT * FROM assignees";
        return jdbcTemplate.query(sql, new AssigneeRowMapper());
    }

    public int update(Long id, String newName, String newEmail, LocalDateTime modifiedAt) {
        String sql = "UPDATE assignees SET name = ?, email = ?, modified_at = ? WHERE id = ?";

        return jdbcTemplate.update(sql, newName, newEmail, modifiedAt, id);
    }

    public Long delete(Long id) {
        String sql = "DELETE FROM assignees WHERE id = ?";

        int deletedRows = jdbcTemplate.update(sql, id);
        if (deletedRows == 0)
            throw new AssigneeUpdateFailedException("삭제에 실패했습니다.");

        return id;
    }

    private class AssigneeRowMapper implements RowMapper<Assignee> {
        @Override
        public Assignee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Assignee assignee = new Assignee();
            assignee.setId(rs.getLong("id"));
            assignee.setName(rs.getString("name"));
            assignee.setEmail(rs.getString("email"));
            assignee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            assignee.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return assignee;
        }
    }
}
