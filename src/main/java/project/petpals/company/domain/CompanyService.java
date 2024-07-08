package project.petpals.company.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.petpals.auth.AuthUtils;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.company.dtos.CompanySelfResponseDto;
import project.petpals.company.dtos.CompanySelfUpdateDto;
import project.petpals.company.infrastructure.CompanyRepository;
import project.petpals.exceptions.NotFoundException;
import project.petpals.location.domain.Location;
import project.petpals.location.domain.LocationService;
import project.petpals.location.infrastructure.LocationRepository;
import project.petpals.user.dtos.ProfilePhoto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AuthUtils authUtils;

    public CompanySelfResponseDto getSelfCompany() {
        // get current user email
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company with email " + email + " not found"));

        return modelMapper.map(company, CompanySelfResponseDto.class);
    }

    public CompanyDto getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with id " + companyId + " not found"));

        return modelMapper.map(company, CompanyDto.class);
    }

    public Page<CompanyDto> getAllCompanies(int page, int size) {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, size));
        return companies.map(company -> modelMapper.map(company, CompanyDto.class));
    }

    public void updateCompany(CompanySelfUpdateDto companySelfUpdateDto) {
        // get current user email
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company with email " + email + " not found"));

        company.setName(companySelfUpdateDto.getName());
        company.setLastUpdated(LocalDateTime.now());
        companyRepository.save(company);
    }

    public void addCompanyLocation(Location location) {
        // get current user email
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company with email " + email + " not found"));

        locationService.saveLocation(location);

        company.getLocations().add(location);
        company.setLastUpdated(LocalDateTime.now());

        companyRepository.save(company);
    }

    public void updateProfilePhoto(ProfilePhoto profilePhoto) {
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company with email " + email + " not found"));

        company.setProfileImage(profilePhoto.getProfileImage());
        company.setLastUpdated(LocalDateTime.now());

        companyRepository.save(company);
    }

    public void updateBannerPhoto(ProfilePhoto bannerPhoto) {
        String email = authUtils.getCurrentUserEmail();

        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Company with email " + email + " not found"));

        company.setBannerImage(bannerPhoto.getProfileImage());
        company.setLastUpdated(LocalDateTime.now());

        companyRepository.save(company);
    }
}
