package com.asciugano.sito_scuola.services.DTO;

public class SubmissionRequestDTO {
    private Long assignmentId;
    private String content;

    public Long getAssignmentId() { return assignmentId; }

    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
