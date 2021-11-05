package it.riccardofalzea.gestioneprenotazioni.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.service.UserService;

@RestController
	@RequestMapping("/usercontroller")
	public class UserController {
	    @Autowired
	    UserService userService;
	    @GetMapping("/saveuser")
	    public String saveUser(@RequestParam String username, String nome, String email, String password) {
	        User user = new User();
	        user.setUsername(username);
	        user.setNome(nome);
	        user.setEmail(email);
	        user.setPassword(password);
	        this.userService.saveUser(user);
	        return "Utente salvato";
	    }
	    @GetMapping("/findallusers")
	    public List<User> allUser() {
	        return this.userService.findAllUsers();
	    }
	    @GetMapping("/findbyusername")
	    public Optional<User> findUserByUsername(@RequestParam String username) {
	        return this.userService.findUserByUsername(username);
	    }
	    
	 // Paginazione
	    @GetMapping(value = "/mygetalluserpage", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Page<User>> myGetAllUserPage(Pageable pageable) {
	        Page<User> findAll = userService.myFindAllUserPageable(pageable);
	        if (findAll.hasContent()) {
	            return new ResponseEntity<>(findAll, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	    }

	    @GetMapping(value = "/mygetalluser", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<User> myGetAllUser() {
	        return userService.myFindAllUser();
	    }
	}