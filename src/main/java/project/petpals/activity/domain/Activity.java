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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "activity_status", nullable = false)
    private ActivityStatus activityStatus;

    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
