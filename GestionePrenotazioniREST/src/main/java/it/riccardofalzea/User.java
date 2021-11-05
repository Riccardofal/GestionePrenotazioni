package it.riccardofalzea;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import it.riccardofalzea.gestioneprenotazioni.security.StringAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String nome;
	private String cognome;
	/* Una volta definito il converter, è possibile annotare le proprietà che si intende cifrare, 
    in modo che il motore di persistenza JPA possa effettuare le operazioni di conversione */
    @Convert(converter = StringAttributeConverter.class)
	private String email;
	private String password;
	private Boolean active = true;
	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))

	private Set<Role> roles = new HashSet<>();
	
	 public User(Long id, String username,@Email String email, String password, String nome, String cognome) {
         super();
         this.id = id;
         this.username = username;
         this.email = email;
         this.password = password;
         this.nome = nome;
         this.cognome = cognome;
     }
	
}
