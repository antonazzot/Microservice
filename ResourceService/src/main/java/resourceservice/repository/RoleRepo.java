package resourceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import resourceservice.model.auth.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByroleName(String roleName);
}
