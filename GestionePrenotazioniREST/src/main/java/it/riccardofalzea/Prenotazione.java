package it.riccardofalzea;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="prenotazione")
public class Prenotazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@NonNull
	private User user;
	@ManyToOne
	@NonNull
	private Postazione postazione;
	@NonNull
	@Column (name="dataprenotata")
	private LocalDate dataPrenotata;
	@Column (name="dataprenotazione")
	private LocalDate dataPrenotazione;
}
