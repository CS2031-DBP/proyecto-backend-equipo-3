package project.petpals.activity.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import project.petpals.activity.dtos.ActivityResponseDto;
import project.petpals.activity.dtos.NewActivityDto;
import project.petpals.activity.infrastructure.ActivityRepository;
import project.petpals.auth.AuthUtils;
import project.petpals.company.domain.Company;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.location.infrastructure.LocationRepository;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private LocationRepository locationRepository;

    private ActivityResponseDto convertToDTO(Activity activity) {
        ActivityResponseDto res = modelMapper.map(activity, ActivityResponseDto.class);
        res.setCompanyDto(modelMapper.map(activity.getCompany(), CompanyDto.class));
        return res;
    }

    public Page<ActivityResponseDto> getActivityByType(ActivityType type, int page, int size) {
        Page<Activity> activities = activityRepository.findAllByActivityType(type, PageRequest.of(page, size));
        return activities.map(this::convertToDTO);
    }

    public Page<ActivityResponseDto> getActivitiesByDate(LocalDate date, int page, int size) {
        Page<Activity> activities = activityRepository.findAllByStartDateGreaterThan(date, PageRequest.of(page, size));
        return activities.map(this::convertToDTO);
    }

    public Page<ActivityResponseDto> getActivitiesByCompany(Long companyId, int page, int size) {
        Page<Activity> activities = activityRepository.findAllByCompanyId(companyId, PageRequest.of(page, size));
        return activities.map(this::convertToDTO);
    }

    public Page<ActivityResponseDto> getActivitiesByStatus(ActivityStatus status, int page, int size) {
        Page<Activity> activities = activityRepository.findAllByActivityStatus(status, PageRequest.of(page, size));
        return activities.map(this::convertToDTO);
    }

    public ActivityResponseDto getActivity(Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Activity not found"));
        return convertToDTO(activity);
    }

    public void saveActivity(NewActivityDto newActivityDto) {
        // find Company from security context
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company not found"));

        Activity activity = modelMapper.map(newActivityDto, Activity.class);

        // Added
        if (newActivityDto.getLocation() != null) {
            locationRepository.save(newActivityDto.getLocation());
            activity.setLocations(List.of(newActivityDto.getLocation()));
        }

        if (newActivityDto.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        if (newActivityDto.getStartDate().isBefore(LocalDate.now())) {
            activity.setActivityStatus(ActivityStatus.IN_PROGRESS);
        }
        else {
            activity.setActivityStatus(ActivityStatus.NOT_STARTED);
        }
        activity.setCompany(company);
        activityRepository.save(activity);

        // ADD  LOCATION TO ACTIVITY DTO??
    }

    public void deleteActivity(Long id) throws AccessDeniedException {

        Activity activity = activityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Activity not found"));

        if (!authUtils.isResourceOwner(activity.getCompany().getId())) {
            throw new AccessDeniedException("You are not allowed to delete this activity");
        }

        activityRepository.deleteById(activity.getId());
    }
}
