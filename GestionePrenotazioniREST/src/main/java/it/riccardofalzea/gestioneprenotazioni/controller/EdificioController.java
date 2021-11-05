package it.riccardofalzea.gestioneprenotazioni.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.riccardofalzea.Edificio;
import it.riccardofalzea.gestioneprenotazioni.security.StringAttributeConverter;
import it.riccardofalzea.gestioneprenotazioni.service.EdificioService;

@RestController
@RequestMapping ("/controlleredificio")
public class EdificioController {
	
	@Autowired
	EdificioService edificioService;
	@Autowired
	StringAttributeConverter converter;
	
	@PostMapping(value="/saveedificio",produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String saveEdificio(@Valid @RequestBody Edificio edificio) {
		edificioService.saveEdificio(edificio);
		return "é stato aggiunto un edificio";
		
}

	
	@GetMapping ("/findalledificio")
	public List <Edificio> findAllEdificio(){
		return edificioService.findAll();
	}
	
	@GetMapping ("/findedificiobyindirizzo")
	public List <Edificio> findByIndirizzo(@RequestParam String indirizzo) {
		return edificioService.findByIndirizzo(indirizzo);
	}
	
	// Paginazione
    @GetMapping(value = "/mygetalledificipage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Edificio>> myGetAllEdificioPage(Pageable pageable) {
        Page<Edificio> findAll = edificioService.myFindAllEdificioPageable(pageable);
        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/mygetalledificisortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Edificio> myGetAllEdificiSortByName() {
        return edificioService.myFindAllEdifici();
    }

}
