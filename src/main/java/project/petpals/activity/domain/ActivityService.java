package project.petpals.activity.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import project.petpals.activity.dtos.NewActivityDto;
import project.petpals.activity.infrastructure.ActivityRepository;
import project.petpals.company.domain.Company;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepository companyRepository;

    public Page<Activity> getActivityByType(ActivityType type, int page, int size) {
        return activityRepository.findAllByActivityType(type, PageRequest.of(page, size));
    }

    public Page<Activity> getActivitiesByDate(LocalDateTime date, int page, int size) {
        return activityRepository.findAllByStartDateGreaterThan(date, PageRequest.of(page, size));
    }

    public Page<Activity> getActivitiesByCompany(Long companyId, int page, int size) {
        return activityRepository.findAllByCompanyId(companyId, PageRequest.of(page, size));
    }

    public Page<Activity> getActivitiesByStatus(ActivityStatus status, int page, int size) {
        return activityRepository.findAllByActivityStatus(status, PageRequest.of(page, size));
    }

    public void saveActivity(NewActivityDto newActivityDto) {
        // find Company from security context
        String email = "email";

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company not found"));

        Activity activity = modelMapper.map(newActivityDto, Activity.class);
        if (newActivityDto.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        if (newActivityDto.getStartDate().isBefore(LocalDateTime.now())) {
            activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        }
        else {
            activity.setActivityStatus(ActivityStatus.NOT_STARTED);
        }
        activity.setCompany(company);

        // ADD  LOCATION TO ACTIVITY DTO??
    }

    public void deleteActivity(Long id) throws AccessDeniedException {
        // find Company from security context
        String email = "email";

        Activity activity = activityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Activity not found"));

        if (!activity.getCompany().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to delete this activity");
        }

        activityRepository.deleteById(activity.getId());
    }
}
