package com.webstrdy00.schedule_management.controller;

import com.webstrdy00.schedule_management.dto.ScheduleRequestDto;
import com.webstrdy00.schedule_management.dto.ScheduleResponseDto;
import com.webstrdy00.schedule_management.dto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScheduleList(@RequestBody ScheduleSearchRequestDto requestDto){
        List<ScheduleResponseDto> schedules = scheduleService.getScheduleList(requestDto);
        return schedules;
    }
}
