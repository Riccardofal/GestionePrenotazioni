package it.riccardofalzea.gestioneprenotazioni.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.riccardofalzea.Postazione;
import it.riccardofalzea.TipoPostazione;
import it.riccardofalzea.gestioneprenotazioni.repository.PostazioneRepository;

@Service
public class PostazioneService {
	@Autowired
	PostazioneRepository postRepo;
	
	public List <Postazione> findAllPosta(){
		return postRepo.findAll();
	}
	
	public List<Optional<Postazione>> findPostazioneCitta(TipoPostazione tipo, String nome){
		return postRepo.findByTipoPostazione2(tipo, nome);
	}
	
	public void insertPostazione (Postazione pos) {
		postRepo.save(pos);
	}
	
	public List<Optional<Postazione>> findPostazioneByTipo(TipoPostazione tipo){
		return postRepo.findByTipoPostazione(tipo);
	}
	
	public Page<Postazione> myFindAllPostazionePageable(Pageable pageable) {
	       return postRepo.findAll(pageable);
	}
	
	// Ordinamento
	   public List<Postazione> myFindAllPostazioni() {
	       return postRepo.findByOrderByCodiceAsc();
	   }
}
