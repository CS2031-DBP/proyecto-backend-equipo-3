package project.petpals.user.infrastructure;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import project.petpals.user.domain.User;

public interface UserRepository<T extends User>  extends JpaRepository<T, Long> {
//    Page<? extends User> findByEmail();
}
