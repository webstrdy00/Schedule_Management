package com.webstrdy00.schedule_management.repository;

import com.webstrdy00.schedule_management.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedules (todo, assignee, password, start_date, end_date, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTodo());
            ps.setString(2, schedule.getAssignee());
            ps.setString(3, schedule.getPassword());
            ps.setObject(4, schedule.getStartDate());
            ps.setObject(5, schedule.getEndDate());
            ps.setObject(6, schedule.getCreatedAt());
            ps.setObject(7, schedule.getModifiedAt());
            return ps;
        }, keyHolder);

        schedule.setId(keyHolder.getKey().longValue());
        return schedule;
    }
}
