package com.webstrdy00.schedule_management.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleSearchRequestDto {
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime modifiedDate;
    private String assignee;
}
