package resourceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import resourceservice.model.auth.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByuserName (String username);
}
