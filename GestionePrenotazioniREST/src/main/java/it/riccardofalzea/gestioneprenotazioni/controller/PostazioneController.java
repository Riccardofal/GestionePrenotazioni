package it.riccardofalzea.gestioneprenotazioni.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.riccardofalzea.Postazione;
import it.riccardofalzea.TipoPostazione;
import it.riccardofalzea.gestioneprenotazioni.service.PostazioneService;

@RestController
@RequestMapping("/controllerpostazione")
public class PostazioneController {
	
	@Autowired
	PostazioneService postazioneService;
	
	@GetMapping ("/allpost")
	public List <Postazione> allPostazione(){
		return postazioneService.findAllPosta();
		
	}
	
	@GetMapping ("/findpostazionecitta")
	public List<Optional<Postazione>> findPostazioneCitta(@RequestParam TipoPostazione tipo, String nome){
		return postazioneService.findPostazioneCitta(tipo, nome);
	}
	
	@GetMapping ("/findbytipopostazione")
	public List <Optional<Postazione>> findByTipoPostazione (@RequestParam TipoPostazione tipo){
		return postazioneService.findPostazioneByTipo(tipo);
	}
	
	@PostMapping ("/savepostazione")
	public String insertPostazione (@RequestParam Postazione postazione){
		  postazioneService.insertPostazione(postazione);
		  return "questa postazione postazione è salvata";
		
	}
	
	// Paginazione
    @GetMapping(value = "/mygetallpostazionipage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Postazione>> myGetAllEdificioPage(Pageable pageable) {
        Page<Postazione> findAll = postazioneService.myFindAllPostazionePageable(pageable);
        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/mygetallpostazioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Postazione> myGetAllPostazioni() {
        return postazioneService.myFindAllPostazioni();
    }
}
