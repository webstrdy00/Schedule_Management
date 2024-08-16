package com.webstrdy00.schedule_management.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Assignee {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private Map<String, String> additionalInfo; // 추갸 정보를 저장할 map


}
