package it.riccardofalzea.gestioneprenotazioni.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.riccardofalzea.Edificio;
import it.riccardofalzea.gestioneprenotazioni.repository.EdificioRepository;

@Service
public class EdificioService {

	@Autowired
	EdificioRepository edificioRepository;
	
	public List <Edificio> findAll(){
		return edificioRepository.findAll();
	}
	
	public void saveEdificio (Edificio edificio) {
		edificioRepository.save(edificio);
	}
	
	public List <Edificio> findByIndirizzo(String indirizzo){
		return edificioRepository.findByIndirizzo(indirizzo);
	}
	
	public Page<Edificio> myFindAllEdificioPageable(Pageable pageable) {
	       return edificioRepository.findAll(pageable);
	}
	
	// Ordinamento
	   public List<Edificio> myFindAllEdifici() {
	       return edificioRepository.findByOrderByNomeAsc();
	   }
}
