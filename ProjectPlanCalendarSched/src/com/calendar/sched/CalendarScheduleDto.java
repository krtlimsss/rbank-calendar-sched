package com.calendar.sched;

import java.time.LocalDate;
import java.util.List;

class CalendarScheduleDto {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> taskDependencies;
    private TaskStatus status;

    String getTaskName() {
        return taskName;
    }

    void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    LocalDate getEndDate() {
        return endDate;
    }

    void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    List<String> getTaskDependencies() {
        return taskDependencies;
    }

    void setTaskDependencies(List<String> taskDependencies) {
        this.taskDependencies = taskDependencies;
    }

    TaskStatus getStatus() {
        return status;
    }

    void setStatus(TaskStatus status) {
        this.status = status;
    }
}

