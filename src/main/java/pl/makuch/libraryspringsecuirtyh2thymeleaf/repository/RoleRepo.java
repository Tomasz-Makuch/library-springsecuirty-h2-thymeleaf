package pl.makuch.libraryspringsecuirtyh2thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Role findByRolename(String rolename);
}
