package it.riccardofalzea.gestioneprenotazioni.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.riccardofalzea.Citta;

public interface CittaPage extends PagingAndSortingRepository <Citta, Long> {
	public Page<Citta> findAll(Pageable pageable);

	/* Sort */
    // Formula: findBy + OrderBy + NomeColonna + Ordinamento(Asc/Desc)
	public List<Citta> findByOrderByNomeAsc();
}
