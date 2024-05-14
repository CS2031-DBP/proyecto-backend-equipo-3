package project.petpals.company.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import project.petpals.company.domain.Company;

public class CompanyController {

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String name) {
        return ResponseEntity.ok(companyService.getCompanyByName(name));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        companyService.updateCompany(id, company);
        return ResponseEntity.ok().build();
    }
}
