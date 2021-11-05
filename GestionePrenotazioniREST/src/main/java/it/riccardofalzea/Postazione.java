package it.riccardofalzea;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="postazione")
public class Postazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codice;
	private String descrizione;
	@Column (name="numeromassimooccupati")
	private Integer numeroMassimoOccupati;
	@Column(name="tipopostazione")
	@Enumerated(EnumType.STRING)
	private TipoPostazione tipoPostazione;
	@ManyToOne
	private Edificio edificio;
	
	
}
