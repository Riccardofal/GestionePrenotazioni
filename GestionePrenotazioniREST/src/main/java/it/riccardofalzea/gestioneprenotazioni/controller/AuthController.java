package it.riccardofalzea.gestioneprenotazioni.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Email;


import it.riccardofalzea.Role;
import it.riccardofalzea.RoleType;
import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.repository.UserRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.RoleRepository;

import it.riccardofalzea.gestioneprenotazioni.request.LoginRequest;
import it.riccardofalzea.gestioneprenotazioni.request.SignupRequest;
import it.riccardofalzea.gestioneprenotazioni.response.LoginResponse;
import it.riccardofalzea.gestioneprenotazioni.response.SignupResponse;
import it.riccardofalzea.gestioneprenotazioni.security.JwtUtils;
import it.riccardofalzea.gestioneprenotazioni.security.UserDetailsImpl;


@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    
   String stringa;

    @PostMapping(value="/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Usa l'AuthenticationManager per autenticare i parametri della request
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Ottiene i privilegi dell'utente
        authentication.getAuthorities();
        
        // Ottiene il SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Genera il token
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        // getPrincipal(), ottiene i dati dell'utente
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        
        // Restituisce la response
        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getExpirationTime()));
        
    }
    
    @PostMapping("/login2")
    public ResponseEntity<?> authenticateUser1(@RequestParam String username, String password) {
        // Usa l'AuthenticationManager per autenticare i parametri della request
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        // Ottiene i privilegi dell'utente
        authentication.getAuthorities();
        
        // Ottiene il SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Genera il token
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        // getPrincipal(), ottiene i dati dell'utente
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        
        // Restituisce la response
        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getExpirationTime()));
        
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Verifica l'esistenza di Username e Email già registrate
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
        }
        // Crea un nuovo user codificando la password
        User user = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(),    encoder.encode(signUpRequest.getPassword()), signUpRequest.getNome(), signUpRequest.getCognome());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
     // Verifica l'esistenza dei Role
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
    }
    @PostMapping("/signup1")
    public ResponseEntity<?> registerUser1(@RequestParam String username, @Email String email, String password,
            String nome, String cognome) {
//        SignupRequest signUpRequest = new SignupRequest();
//        signUpRequest.setRole(null);
        // Verifica l'esistenza di Username e Email già registrate
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
        }
        // Crea un nuovo user codificando la password
        User user = new User(null, username, email, encoder.encode(password), nome, cognome);
        Set<String> strRoles = new HashSet<>();
        Set<Role> roles = new HashSet<>();
     // Verifica l'esistenza dei Role
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
    }
}
