package it.riccardofalzea.gestioneprenotazioni.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	
	   public List<User> findAllUsers() {
	        return this.userRepo.findAll();
	    }
	    public Optional<User> findUserByUsername(String username) {
	        return this.userRepo.findByUsername(username);
	    }
	    public void saveUser(User user) {
	        this.userRepo.save(user);
	    }
	    
	    public Page<User> myFindAllUserPageable(Pageable pageable) {
		       return userRepo.findAll(pageable);
		}
		
		// Ordinamento
		   public List<User> myFindAllUser() {
		       return userRepo.findByOrderByUsernameDesc();
		   }
	}


