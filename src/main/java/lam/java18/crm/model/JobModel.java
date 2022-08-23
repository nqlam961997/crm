package lam.java18.crm.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class JobModel {
    private int id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public JobModel() {
        this.startDate = LocalDateTime.now();
    }
}
