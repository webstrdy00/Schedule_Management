package com.webstrdy00.schedule_management.service;

import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleRequestDto;
import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleResponseDto;
import com.webstrdy00.schedule_management.dto.ScheduleDto.ScheduleSearchRequestDto;
import com.webstrdy00.schedule_management.entity.Assignee;
import com.webstrdy00.schedule_management.entity.Schedule;
import com.webstrdy00.schedule_management.exception.PasswordMismatchException;
import com.webstrdy00.schedule_management.exception.ScheduleDeleteFailedException;
import com.webstrdy00.schedule_management.exception.ScheduleNotFoundException;
import com.webstrdy00.schedule_management.exception.ScheduleUpdateFailedException;
import com.webstrdy00.schedule_management.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private AssigneeService assigneeService;
    public ScheduleService(ScheduleRepository scheduleRepository, AssigneeService assigneeService){
        this.scheduleRepository = scheduleRepository;
        this.assigneeService = assigneeService;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Assignee assignee = assigneeService.getAssigneeById(requestDto.getAssigneeId());

        Schedule schedule = new Schedule();
        schedule.setTodo(requestDto.getTodo());
        schedule.setAssigneeId(requestDto.getAssigneeId());
        schedule.setPassword(requestDto.getPassword());
        schedule.setStartDate(requestDto.getStartDate());
        schedule.setEndDate(requestDto.getEndDate());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setModifiedAt(LocalDateTime.now());

        Schedule saveSchedule = scheduleRepository.save(schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null){
            throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");
        }

        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    public List<Schedule> getScheduleByAssigneeId(Long assigneeId){
        return scheduleRepository.findByAssigneeId(assigneeId);
    }

    public List<ScheduleResponseDto> getScheduleList(ScheduleSearchRequestDto requestDto) {
        List<Schedule> schedules = scheduleRepository.findAllByConditions(requestDto);
        return schedules.stream()
                .map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null)
            throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");

        if(!requestDto.getPassword().equals(schedule.getPassword()))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");

        LocalDateTime modifiedAt = LocalDateTime.now();
        String newTodo = requestDto.getTodo() != null ? requestDto.getTodo() : schedule.getTodo();
        int updatedRows = scheduleRepository.updateSchedule(id, newTodo, modifiedAt);

        if (updatedRows == 0)
            throw new ScheduleUpdateFailedException("수정에 실패했습니다.");

        Schedule updateSchedule = scheduleRepository.findById(id);

        return new ScheduleResponseDto(updateSchedule);
    }

    public Long deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null)
            throw new ScheduleNotFoundException("일정을 찾을 수 없습니다.");

        if (!requestDto.getPassword().equals(schedule.getPassword()))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");

        int deleteRows = scheduleRepository.deleteSchedule(id);
        if (deleteRows == 0)
            throw new ScheduleDeleteFailedException("일정 삭제에 실패했습니다.");

        return id;
    }
}
