package com.webstrdy00.schedule_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ScheduleRequestDto {
    private String todo;
    private String assignee;
    private String password;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
