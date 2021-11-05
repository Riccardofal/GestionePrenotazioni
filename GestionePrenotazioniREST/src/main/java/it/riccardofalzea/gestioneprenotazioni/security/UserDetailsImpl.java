package it.riccardofalzea.gestioneprenotazioni.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.riccardofalzea.User;
import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = -7130684040991684077L;
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    
    //Proprieta dell'utente: abilitato, bloccato, scaduto, password scaduta
    //Locked: account sospeso automaticamente a causa di tentativi di accesso non validi
    //Expired: account disattivato amministritivamente in base a un timing (es. abbonamento scaduto)
    //CredentialsNonExpired: scadenza delle credenziali (password)
    private boolean accountNonLocked = true;    
    private boolean accountNonExpired = false;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Date expirationTime;
    /* GrantedAuthority, rappresenta un'autorizzazione concessa a un oggetto di autenticazione (il token) */
    private Collection<? extends GrantedAuthority> authorities;
    
    //Costruttore
    public UserDetailsImpl(Long id, String username, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;        
        this.accountNonLocked = enabled;
        this.accountNonExpired = enabled;
        this.credentialsNonExpired = enabled;
        this.enabled = enabled;
        this.authorities = authorities;
    }
    
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),    user.getActive(), authorities);
    }
}
