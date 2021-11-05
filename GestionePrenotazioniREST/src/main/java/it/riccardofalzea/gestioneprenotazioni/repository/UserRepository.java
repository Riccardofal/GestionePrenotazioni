package it.riccardofalzea.gestioneprenotazioni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.riccardofalzea.User;


public interface UserRepository extends JpaRepository<User,Long> {
	Optional <User> findByUsername(String username);
	
	public Page<User> findAll(Pageable pageable);

	public List<User> findByOrderByUsernameDesc();
	
	Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
	}

