package it.riccardofalzea.gestioneprenotazioni.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.riccardofalzea.LinguaPrenotazioni;
import it.riccardofalzea.Postazione;
import it.riccardofalzea.Prenotazione;
import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.service.PrenotazioneService;





@RestController
@RequestMapping("/controllerprenotazione")
public class PrenotazioneController {
	@Autowired
	LinguaPrenotazioni rules;
	
	@Autowired
	PrenotazioneService prenotazioneService;
	
	@PostMapping(value= "/findbypostazione", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String findByPostazione(@RequestBody Postazione postazione){
		if (this.prenotazioneService.findByPostazione(postazione).isEmpty())
		return "la postazione è libera";
		else
			return "la postazione non è libera";
	}
	
	@PostMapping(value="/prenotazioneuser", produces=MediaType.APPLICATION_JSON_VALUE )
		public String findByPostazioneUser (@RequestBody User user, @RequestParam LocalDate local) {
			if( this.prenotazioneService.findByPostazioneUser(user, local).size()>1) {
				return "hai gia prenotato la postazione nel giorno " + local + " e non è possibile prenotarla"; 
			}else
				return "non ci sono prenotazioni in data:" + local + "ed è possibile prenotarla";
	}
	@GetMapping(value="/prenotazionedata")
    public String prenota(@RequestParam Long idUser, @RequestParam Long idPostazione,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataPrenotazione) {
        boolean res=this.prenotazioneService.savePrenotazione(dataPrenotazione, idPostazione, idUser);
        if(res) {
            return "prenotazione eseguita";
        }else
            return "prenotazione non eseguita";
    }
	@GetMapping(value="/rulesprenot",produces=MediaType.APPLICATION_JSON_VALUE)
	public LinguaPrenotazioni modLang() {
		return rules;
	}
	
	@GetMapping(value="/choselang")
	public String choseLang(@RequestParam String lang) {
		switch(lang) {
		case "ita": 
			return " Questa è la prenotazione in lingua italiana" + this.rules.getRulesIta();
		case "eng": 
			return "Questo è il test prenotazione in inglese";
		default:
			return "lingua errata";
		}
	}
	
	@GetMapping ("/prenotIta")
	private String langIta() {
		return "Questa è la prenotazione in lingua italiana" + this.rules.getRulesIta();
	}
	
	@GetMapping ("/prenotEng")
	private String langEng() {
		return "Questo è il test prenotazione in inglese" + this.rules.getRulesEng();
	}
	@GetMapping("/prenoterr")
	private String langError() {
		return "lingua errata";
	}
	
	@PostMapping(value = "/changerulesita", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LinguaPrenotazioni changeRulesIta(@RequestBody LinguaPrenotazioni reque) {
		return modLange (reque);

	}
	
	private LinguaPrenotazioni modLange (LinguaPrenotazioni reque) {
		if(reque.getRulesEng()!=null) {
			this.rules.setRulesEng(reque.getRulesEng());
		return modLang();
	} else
		if(reque.getRulesIta()!=null) {
			this.rules.setRulesIta(reque.getRulesIta());
			return modLang();
	}
	return modLang();
	}
	
	@PostMapping(value = "/changeruleseng", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public LinguaPrenotazioni changeRulesEng(@RequestBody LinguaPrenotazioni reque) {
		return modLange(reque);
		
	}
	
	// Paginazione
    @GetMapping(value = "/mygetallprenotazionipage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Prenotazione>> myGetAllEdificioPage(Pageable pageable) {
        Page<Prenotazione> findAll = prenotazioneService.myFindAllPrenotazionePageable(pageable);
        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Ordinamento
    @GetMapping(value = "/mygetallprenotazioni", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prenotazione> myGetAllPrenotazione() {
        return prenotazioneService.myFindAllPrenotazioni();
    }
}


