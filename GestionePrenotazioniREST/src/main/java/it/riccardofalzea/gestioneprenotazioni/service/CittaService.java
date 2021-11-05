package it.riccardofalzea.gestioneprenotazioni.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import it.riccardofalzea.Citta;
import it.riccardofalzea.gestioneprenotazioni.repository.CittaPage;
import it.riccardofalzea.gestioneprenotazioni.repository.CittaRepository;

@Service
public class CittaService {
	
	@Autowired
	CittaRepository cittaRepository;
	@Autowired
	CittaPage cittaPage;
	
	public List<Citta> findAll (){
		return  cittaRepository.findAll();
	}
	
	public List<Citta> findByNome(String nome){
		return cittaRepository.findByNome(nome);
	}
	
	public void saveCitta (Citta citta) {
		cittaRepository.save(citta);
	}
	
	// Paginazione
   public Page<Citta> myFindAllCittaPageable(Pageable pageable) {
       return cittaPage.findAll(pageable);
   }
   
   public Page<Citta> myFindAllCittaPageSize(Integer page, Integer size) {
       Pageable paging = PageRequest.of(page, size);
       Page<Citta> pagedResult = cittaPage.findAll(paging);
       if(pagedResult.hasContent()) {
     return pagedResult;
     } else {
         return null;
     }
   }
   
// Paginazione e Ordinamento
   public List<Citta> myFindAllCittaPageSizeSort(Integer page, Integer size, String sort) {
       Pageable paging = PageRequest.of(page, size, Sort.by(sort));
       Page<Citta> pagedResult = cittaPage.findAll(paging);
       if (pagedResult.hasContent()) {
           return pagedResult.getContent();
       } else {
           return new ArrayList<Citta>();
       }
   }
   
// Ordinamento
   public List<Citta> myFindAllCittaSorted() {
       return cittaPage.findByOrderByNomeAsc();
   }
}
