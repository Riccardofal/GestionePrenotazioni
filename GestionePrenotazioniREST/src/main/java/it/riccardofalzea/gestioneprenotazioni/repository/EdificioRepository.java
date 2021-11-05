package it.riccardofalzea.gestioneprenotazioni.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.riccardofalzea.Edificio;


public interface EdificioRepository extends JpaRepository<Edificio,Long> {
	List <Edificio> findByIndirizzo(String indirizzo);
	
	public Page<Edificio> findAll(Pageable pageable);
	
	public List<Edificio> findByOrderByNomeAsc();
}
