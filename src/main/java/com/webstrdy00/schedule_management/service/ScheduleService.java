package com.webstrdy00.schedule_management.service;

import com.webstrdy00.schedule_management.dto.ScheduleRequestDto;
import com.webstrdy00.schedule_management.dto.ScheduleResponseDto;
import com.webstrdy00.schedule_management.dto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.entity.Schedule;
import com.webstrdy00.schedule_management.exception.ScheduleNotFoundException;
import com.webstrdy00.schedule_management.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null){
            throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");
        }

        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    public List<ScheduleResponseDto> getScheduleList(ScheduleSearchRequestDto requestDto) {
        List<Schedule> schedules = scheduleRepository.findAllByConditions(requestDto);
        return schedules.stream()
                .map(ScheduleResponseDto::new).toList();
    }
}
