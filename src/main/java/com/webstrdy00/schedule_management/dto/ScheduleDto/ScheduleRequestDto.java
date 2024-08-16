package com.webstrdy00.schedule_management.dto.ScheduleDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ScheduleRequestDto {
    private String todo;
    private Long assigneeId;
    private String assignee;
    private String password;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ScheduleRequestDto(String todo, String assignee, String password){
        this.todo = todo;
        this.assignee = assignee;
        this.password= password;
    }
}
