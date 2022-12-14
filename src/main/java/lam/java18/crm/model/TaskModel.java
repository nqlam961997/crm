package lam.java18.crm.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskModel {
    private int id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int userId;
    private int jobId;
    private int statusId;
    private String userEmail;
    private String jobName;
    private String statusName;

    public TaskModel() {
        this.startDate = LocalDateTime.now();
    }
}
