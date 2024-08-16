package com.webstrdy00.schedule_management.service;

import com.webstrdy00.schedule_management.dto.AssigneeDto.AssigneeRequestDto;
import com.webstrdy00.schedule_management.dto.AssigneeDto.AssigneeResponseDto;
import com.webstrdy00.schedule_management.entity.Assignee;
import com.webstrdy00.schedule_management.exception.AssigneeNotFoundException;
import com.webstrdy00.schedule_management.exception.AssigneeUpdateFailedException;
import com.webstrdy00.schedule_management.repository.AssigneeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssigneeService {
    private final AssigneeRepository assigneeRepository;

    public AssigneeService(AssigneeRepository assigneeRepository){
        this.assigneeRepository = assigneeRepository;
    }
    public AssigneeResponseDto createAssignee(AssigneeRequestDto requestDto) {
        Assignee assignee = new Assignee();
        assignee.setName(requestDto.getName());
        assignee.setEmail(requestDto.getEmail());
        assignee.setCreatedAt(LocalDateTime.now());
        assignee.setModifiedAt(LocalDateTime.now());

        Assignee saveAssignee = assigneeRepository.save(assignee);

        return new AssigneeResponseDto(saveAssignee);
    }

    public Assignee getAssigneeById(Long id) {
        Assignee assignee = assigneeRepository.findById(id);
        if (assignee == null)
            throw new AssigneeNotFoundException("관리자를 찾을 수 없습니다.");

        return assignee;
    }

    public AssigneeResponseDto getAssigneeByEmail(String email) {
        Assignee assignee = assigneeRepository.findByEmail(email);
        if (assignee == null)
            throw new AssigneeNotFoundException("관리자를 찾을 수 없습니다.");

        return new AssigneeResponseDto(assignee);
    }

    public List<AssigneeResponseDto> getAllAssignee() {
        List<Assignee> assignees = assigneeRepository.findAll();
        return assignees.stream()
                .map(AssigneeResponseDto::new)
                .collect(Collectors.toList());
    }

    public AssigneeResponseDto updateAssignee(Long id, AssigneeRequestDto requestDto) {
        Assignee assignee = assigneeRepository.findById(id);
        if (assignee == null)
            throw new AssigneeNotFoundException("관리자를 찾을 수 없습니다.");

        String newName = requestDto.getName() != null ? requestDto.getName() : assignee.getName();
        String newEmail = requestDto.getEmail() != null ? requestDto.getEmail() : assignee.getEmail();

        LocalDateTime modifiedAt = LocalDateTime.now();

        int updateRow = assigneeRepository.update(id, newName, newEmail, modifiedAt);

        if (updateRow == 0)
            throw new AssigneeUpdateFailedException("수정에 실패했습니다.");

        Assignee updateAssignee = assigneeRepository.findById(id);

        return new AssigneeResponseDto(updateAssignee);
    }

    public Long deleteAssignee(Long id) {
        if (assigneeRepository.findById(id) == null)
            throw new AssigneeNotFoundException("관리자를 찾을 수 없습니다.");

        return assigneeRepository.delete(id);
    }
}
