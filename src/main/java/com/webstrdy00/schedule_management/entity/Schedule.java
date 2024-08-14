package com.webstrdy00.schedule_management.entity;

import com.webstrdy00.schedule_management.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String todo;
    private String assignee;
    private String password;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public Schedule(ScheduleRequestDto requestDto, LocalDateTime now) {
        this.todo = requestDto.getTodo();
        this.assignee = requestDto.getAssignee();
        this.password = requestDto.getPassword();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.createdAt = now;
        this.modifiedAt = now;
    }
}
