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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@RequestBody CompanySelfUpdateDto company) {
        companyService.updateCompany(company);
        return ResponseEntity.ok().build();
    }
}
