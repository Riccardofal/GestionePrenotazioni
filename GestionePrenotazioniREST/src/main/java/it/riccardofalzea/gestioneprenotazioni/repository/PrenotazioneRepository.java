package it.riccardofalzea.gestioneprenotazioni.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.riccardofalzea.Postazione;
import it.riccardofalzea.Prenotazione;
import it.riccardofalzea.User;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {
	@Query("SELECT c.postazione FROM Prenotazione c WHERE c.postazione =:postazione" )
	List<Optional<Postazione>> findByPostazione(Postazione postazione);

	@Query("SELECT c.postazione FROM Prenotazione c WHERE c.user =:user AND c.dataPrenotata =:data" )
	List<Optional<Postazione>> findByPostazioneUser(User user, LocalDate data);

	public Page<Prenotazione> findAll(Pageable pageable);

	public List<Prenotazione> findByOrderByDataPrenotataDesc();
	
}
