package com.webstrdy00.schedule_management.dto;

import com.webstrdy00.schedule_management.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String assignee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.assignee = schedule.getAssignee();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.createAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
