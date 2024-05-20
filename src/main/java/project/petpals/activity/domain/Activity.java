package project.petpals.activity.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.petpals.company.domain.Company;
import project.petpals.location.domain.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "activity_status", nullable = false)
    private ActivityStatus activityStatus;

    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;


    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Location> locations = new ArrayList<>();
}
