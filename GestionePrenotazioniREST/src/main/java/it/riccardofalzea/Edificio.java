package it.riccardofalzea;


import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import it.riccardofalzea.gestioneprenotazioni.security.StringAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="edificio")
public class Edificio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String indirizzo;
	 @Convert(converter = StringAttributeConverter.class)
	 @Size (min=8, max=8, message="password non corretta")
	   @Column(name="pass_edificio")
	private String passEdificio;
	@ManyToOne
	private Citta citta;
	
	
	public Edificio(String nome, String indirizzo, Citta citta) {
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.citta = citta;
	}
	
   


}
