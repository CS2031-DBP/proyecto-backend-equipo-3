package project.petpals.company.infrastructure;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import project.petpals.company.domain.Company;
import project.petpals.user.infrastructure.BaseUserRepository;

@Repository
//@Transactional
public interface CompanyRepository extends BaseUserRepository<Company> {
}
