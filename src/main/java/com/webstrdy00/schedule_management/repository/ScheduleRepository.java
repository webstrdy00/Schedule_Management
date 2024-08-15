package com.webstrdy00.schedule_management.repository;

import ch.qos.logback.core.util.StringUtil;
import com.webstrdy00.schedule_management.dto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("id"));
                schedule.setTodo(resultSet.getString("todo"));
                schedule.setAssignee(resultSet.getString("assignee"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
                schedule.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
                schedule.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                schedule.setModifiedAt(resultSet.getTimestamp("modified_at").toLocalDateTime());
                return schedule;
            }else {
                return null;
            }
        }, id);
    }

    public List<Schedule> findAllByConditions(ScheduleSearchRequestDto requestDto) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (requestDto.getModifiedDate() != null){
            sql.append(" AND DATE(modified_at) = ?");
            params.add(requestDto.getModifiedDate());
        }

        if (requestDto.getAssignee() != null && !requestDto.getAssignee().isEmpty()){
            sql.append(" AND assignee = ?");
            params.add(requestDto.getAssignee());
        }

        sql.append(" ORDER BY modified_at DESC");

        return jdbcTemplate.query(sql.toString(), new ScheduleRowMapper(), params.toArray());
    }

    public int updateSchedule(Long id, String todo, String assignee, LocalDateTime modifiedAt) {
        String sql = "UPDATE schedules SET todo = ?, assignee = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, todo, assignee, modifiedAt, id);
    }

    public int deleteSchedule(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class ScheduleRowMapper implements RowMapper<Schedule> {
        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTodo(rs.getString("todo"));
            schedule.setAssignee(rs.getString("assignee"));
            schedule.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
            schedule.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
            schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return schedule;
        }
    }
}
