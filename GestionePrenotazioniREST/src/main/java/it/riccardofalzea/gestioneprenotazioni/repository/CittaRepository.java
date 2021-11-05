package it.riccardofalzea.gestioneprenotazioni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.riccardofalzea.Citta;

public interface CittaRepository extends JpaRepository<Citta,Long>  {
	List<Citta> findByNome(String nome);
	
//	public Page<Citta> findAll(Pageable pageable);

}
