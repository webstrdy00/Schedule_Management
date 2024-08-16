package com.webstrdy00.schedule_management.entity;

import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {
    private Long id;
    private Long assigneeId;  // Assignee의 id를 참조
    private String todo;
    private String password;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule() {

    }
}
