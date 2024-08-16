package com.webstrdy00.schedule_management.controller;

import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleRequestDto;
import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleResponseDto;
import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.entity.Schedule;
import com.webstrdy00.schedule_management.service.AssigneeService;
import com.webstrdy00.schedule_management.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;
    private AssigneeService assigneeService;

    public ScheduleController(ScheduleService scheduleService, AssigneeService assigneeService){
        this.scheduleService = scheduleService;
        this.assigneeService = assigneeService;
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id){
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/assignees/{assigneeId}")
    public List<ScheduleResponseDto> getScheduleByAssigneeId(@PathVariable Long assigneeId){
        List<Schedule> schedules = scheduleService.getScheduleByAssigneeId(assigneeId);
        List<ScheduleResponseDto> responseDtos = schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
        return responseDtos;
    }

    @GetMapping
    public List<ScheduleResponseDto> getScheduleList(@RequestBody ScheduleSearchRequestDto requestDto){
        List<ScheduleResponseDto> schedules = scheduleService.getScheduleList(requestDto);
        return schedules;
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto){
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);
        return responseDto;
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto){
        String password = requestDto.getPassword();
        if (password == null)
            throw new IllegalArgumentException("비밀번호는 필수로 입력해야 합니다.");

        return scheduleService.deleteSchedule(id, requestDto);
    }
}
