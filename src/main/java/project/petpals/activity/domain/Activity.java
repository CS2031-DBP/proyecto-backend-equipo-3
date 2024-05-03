package project.petpals.activity.domain;


import jakarta.persistence.*;
import lombok.Data;
import project.petpals.company.domain.Company;

import java.time.LocalDateTime;

@Data
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "activity_status")
    private ActivityStatus activityStatus;

    @Column(name = "activity_type")
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
