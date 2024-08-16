package com.webstrdy00.schedule_management.repository;

import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.entity.Assignee;
import com.webstrdy00.schedule_management.entity.Schedule;
import com.webstrdy00.schedule_management.exception.AssigneeNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AssigneeRepository assigneeRepository;

    public ScheduleRepository(JdbcTemplate jdbcTemplate, AssigneeRepository assigneeRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.assigneeRepository = assigneeRepository;
    }

    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedules (todo, assignee_id, password, start_date, end_date, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTodo());
            ps.setLong(2, schedule.getAssigneeId());
            ps.setString(3, schedule.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(schedule.getStartDate()));
            ps.setTimestamp(5, Timestamp.valueOf(schedule.getEndDate()));
            ps.setTimestamp(6, Timestamp.valueOf(schedule.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(schedule.getModifiedAt()));
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
                schedule.setAssigneeId(resultSet.getLong("assigneeId"));
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

    public Assignee getAssigneeById(Long assigneeId){
        Assignee assignee = assigneeRepository.findById(assigneeId);
        if (assignee == null)
            throw new AssigneeNotFoundException("사용자를 찾을 수 없습니다.");

        return assignee;
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

    public int updateSchedule(Long id, String todo, LocalDateTime modifiedAt) {
        String sql = "UPDATE schedules SET todo = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, todo, modifiedAt, id);
    }

    public int deleteSchedule(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Schedule> findByAssigneeId(Long assigneeId) {
        String sql = "SELECT * FROM schedules WHERE assignee_id = ?";
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), assigneeId);
    }

    private static class ScheduleRowMapper implements RowMapper<Schedule> {
        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTodo(rs.getString("todo"));
            schedule.setAssigneeId(rs.getLong("assignee_id"));
            schedule.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
            schedule.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
            schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return schedule;
        }
    }
}
