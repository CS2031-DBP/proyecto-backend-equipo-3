package project.petpals.company.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.petpals.company.domain.Company;
import project.petpals.company.domain.CompanyService;
import project.petpals.company.dtos.CompanyDto;
import project.petpals.company.dtos.CompanySelfResponseDto;
import project.petpals.company.dtos.CompanySelfUpdateDto;
import project.petpals.location.domain.Location;
import project.petpals.user.dtos.ProfilePhoto;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasRole('ROLE_COMPANY') or hasRole('ROLE_PERSON')")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @GetMapping("/me")
    public ResponseEntity<CompanySelfResponseDto> GetSelfCompany() {
        return ResponseEntity.ok(companyService.getSelfCompany());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_COMPANY') or hasRole('ROLE_PERSON')")
    public ResponseEntity<Page<CompanyDto>> getAllCompanies(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(companyService.getAllCompanies(page, size));
    }

//    @GetMapping("/{name}")
//    public ResponseEntity<Company> getCompanyByName(@PathVariable String name) {
//        return ResponseEntity.ok(companyService.getCompanyByName(name));
//    }

    @PatchMapping()
    public ResponseEntity<Void> updateCompany(@RequestBody CompanySelfUpdateDto company) {
        companyService.updateCompany(company);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/location")
    public ResponseEntity<Void> addCompanyLocation(@RequestBody Location location) {
        companyService.addCompanyLocation(location);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/photo")
    public ResponseEntity<Void> updateProfilePhoto(@RequestBody ProfilePhoto profilePhoto) {
        companyService.updateProfilePhoto(profilePhoto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/banner")
    public ResponseEntity<Void> updateBannerPhoto(@RequestBody ProfilePhoto bannerPhoto) {
        companyService.updateBannerPhoto(bannerPhoto);
        return ResponseEntity.ok().build();
    }
}
