package com.webstrdy00.schedule_management.dto.AssigneeDto;

import com.webstrdy00.schedule_management.entity.Assignee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AssigneeResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AssigneeResponseDto(Assignee assignee) {
        this.id = assignee.getId();
        this.name = assignee.getName();
        this.email = assignee.getEmail();
        this.createdAt = assignee.getCreatedAt();
        this.modifiedAt = assignee.getModifiedAt();
    }
}
