package com.webstrdy00.schedule_management.controller;

import com.webstrdy00.schedule_management.dto.AssigneeDto.AssigneeRequestDto;
import com.webstrdy00.schedule_management.dto.AssigneeDto.AssigneeResponseDto;
import com.webstrdy00.schedule_management.entity.Assignee;
import com.webstrdy00.schedule_management.service.AssigneeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignees")
public class AssigneeController {
    private final AssigneeService assigneeService;

    public AssigneeController(AssigneeService assigneeService){
        this.assigneeService = assigneeService;
    }

    @PostMapping
    public AssigneeResponseDto createAssignee(@RequestBody AssigneeRequestDto requestDto){
        return assigneeService.createAssignee(requestDto);
    }

    @GetMapping("/{id}")
    public AssigneeResponseDto getAssigneeById(@PathVariable Long id){
        Assignee assignee = assigneeService.getAssigneeById(id);
        return new AssigneeResponseDto(assignee);
    }

    @GetMapping("/email/{email}")
    public AssigneeResponseDto getAssigneeByEmail(@PathVariable String email){
        return assigneeService.getAssigneeByEmail(email);
    }

    @GetMapping
    public List<AssigneeResponseDto> getAllAssignee(){
        List<AssigneeResponseDto> assigneeResponse = assigneeService.getAllAssignee();
        return assigneeResponse;
    }

    @PutMapping("/{id}")
    public AssigneeResponseDto updateAssignee(@PathVariable Long id, @RequestBody AssigneeRequestDto requestDto){
        return assigneeService.updateAssignee(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteAssignee(@PathVariable Long id){
        return assigneeService.deleteAssignee(id);
    }
}
