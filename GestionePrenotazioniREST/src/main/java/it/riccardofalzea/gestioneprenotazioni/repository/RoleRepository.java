package it.riccardofalzea.gestioneprenotazioni.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.riccardofalzea.Role;
import it.riccardofalzea.RoleType;


public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByRoleType(RoleType roletype);

}
