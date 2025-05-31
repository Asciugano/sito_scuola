package com.asciugano.sito_scuola.services.DTO;

public class CommentRequestDTO {
    private String content;
    private Long userId;
    private Long assignmentId;

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getAssignmentId() { return assignmentId; }

    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
}