package com.calendar.sched;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class CalendarSchedule {

    private static Scanner console = new Scanner(System.in);
    private List<CalendarScheduleDto> taskList = new ArrayList<>();

    void run() {
        System.out.println("*********");
        System.out.println("PROJECT X");
        System.out.println("*********");

        while (true) {
            System.out.println("Choose from the following options: ");
            System.out.println("(1)Create New Task (2)Add Task Dependency (3)Print Task List (4)Start Task (5)Exit");
            System.out.println();

            System.out.print("Choice: ");
            String choice = console.next();
            System.out.println();

            if (choice.matches("\\d+")) {
                int finalChoice = Integer.parseInt(choice);
                switch (finalChoice) {
                    case 1: createNewTask();
                        break;
                    case 2: addTaskDependency();
                        break;
                    case 3: printTaskList();
                        break;
                    case 4: startTask();
                        break;
                    case 5: System.exit(0);
                        break;
                    default: System.out.println("Please choose between number 1 to 4 only.\n");
                }
            } else {
                System.out.println("Error: Please input a whole number.\n");
            }
        }
    }

    private void startTask() {
        System.out.print("Enter Task Name: ");
        String taskName = console.next();

        for (CalendarScheduleDto dto : taskList) {
            if (dto.getTaskName().equals(taskName)) {
                if (dto.getTaskDependencies() != null) {
                    validateCompletedTasks(dto);
                } else {
                    System.out.println("Succesfully completed task!");
                    dto.setStatus(TaskStatus.COMPLETED);
                }
            }
        }
    }

    private void validateCompletedTasks(CalendarScheduleDto dto) {
        List<String> tasks = new ArrayList<>();
        for (String task : dto.getTaskDependencies()) {
            for (CalendarScheduleDto sched : taskList) {
                if (sched.getTaskName().equals(task)) {
                    if (sched.getStatus().equals(TaskStatus.COMPLETED)) {
                        tasks.add(task);
                    }
                }
            }
        }

        if (tasks.size() == dto.getTaskDependencies().size()) {
            System.out.println("Successfully completed task!\n");
            dto.setStatus(TaskStatus.COMPLETED);
        } else {
            System.out.println("Please complete task dependencies first.\n");
        }
    }

    private void printTaskList() {
        System.out.println("TASK NAME, START DATE, END DATE, TASK DEPENDENCIES, STATUS");
        for (CalendarScheduleDto dto : taskList) {
            System.out.println(dto.getTaskName().concat(", ").concat(dto.getStartDate().toString()).concat(", ")
                .concat(dto.getEndDate().toString()).concat(", (").concat(dto.getTaskDependencies() != null ?
                    dto.getTaskDependencies().toString() : "").concat("), ").concat(dto.getStatus().name()));
        }
        System.out.println("\n");
    }

    private void addTaskDependency() {
        List<String> taskDependency = new ArrayList<>();
        System.out.print("Enter Task Name: ");
        String taskName = console.next();

        System.out.print("Enter Task Name Dependency: ");
        String taskNameDependency = console.next();

        boolean taskNameValid = taskList.stream().anyMatch(calendarScheduleDto ->
            Objects.equals(calendarScheduleDto.getTaskName(), taskNameDependency)
            && !taskNameDependency.equals(taskName));

        boolean isCompleted = false;
        for (CalendarScheduleDto dto : taskList) {
            if (dto.getTaskName().equals(taskNameDependency)) {
                if (dto.getStatus().equals(TaskStatus.COMPLETED)) {
                    isCompleted = true;
                }
            }
        }

        if (taskNameValid) {
            for (CalendarScheduleDto dto : taskList) {
                if (dto.getTaskName().equals(taskName)) {
                    if (!isCompleted) {
                        if (dto.getTaskDependencies() != null) {
                            dto.getTaskDependencies().add(taskNameDependency);
                        } else {
                            taskDependency.add(taskNameDependency);
                            dto.setTaskDependencies(taskDependency);
                        }
                        System.out.println("Task Dependency was added!\n");
                    } else {
                        System.out.println("Task cannot be added since it has been already completed.\n");
                    }
                }
            }
        }
    }

    private void createNewTask() {
        System.out.print("Enter Task Name: ");
        String taskName = console.next();

        String error = "Error";
        while (!error.isEmpty()) {
            try {
                error = "";
                System.out.print("Enter Start Date (MM/dd/yyyy): ");
                String startDate = console.next();
                LocalDate validatedStartDate = validateDateFormat(startDate);

                System.out.print("Enter End Date (MM/dd/yyyy): ");
                String endDate = console.next();
                LocalDate validatedEndDate = validateDateFormat(endDate);

                CalendarScheduleDto dto = new CalendarScheduleDto();
                dto.setTaskName(taskName);
                dto.setStartDate(validatedStartDate);
                dto.setEndDate(validatedEndDate);
                dto.setStatus(TaskStatus.NOT_STARTED);
                taskList.add(dto);

                System.out.println("Task has been created!\n");

            } catch (Exception e) {
                error = "Error: Invalid date input. Use MM/dd/yyyy format.";
                System.out.println(error + "\n");
            }
        }
    }

    private LocalDate validateDateFormat(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
}

