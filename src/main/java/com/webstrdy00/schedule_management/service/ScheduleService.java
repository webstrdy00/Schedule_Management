package com.webstrdy00.schedule_management.service;

import com.webstrdy00.schedule_management.dto.ScheduleRequestDto;
import com.webstrdy00.schedule_management.dto.ScheduleResponseDto;
import com.webstrdy00.schedule_management.entity.Schedule;
import com.webstrdy00.schedule_management.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(requestDto, now);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }
}
