package it.riccardofalzea.gestioneprenotazioni.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.riccardofalzea.Citta;
import it.riccardofalzea.gestioneprenotazioni.service.CittaService;

@RestController
@RequestMapping ("/controllercitta")
public class CittaController {
	
	@Autowired
	CittaService cittaService;
	
	@GetMapping("/savecitta")
	public String saveCitta(@RequestParam String nome) {
		Citta citta= new Citta();
		citta.setNome(nome);
		cittaService.saveCitta(citta);
		return "è stata inserita una citta";
	}
	
	@GetMapping ("/findallcitta")
	public List <Citta> allCitta(){
		return cittaService.findAll();
	}
	
	@GetMapping ("/findcittabyname")
	public List <Citta> findByName (@RequestParam String nome){
		return cittaService.findByNome(nome);
	}
	
	// Paginazione
    @GetMapping(value = "/mygetallcittapage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Citta>> myGetAllCittaPage(Pageable pageable) {
        Page<Citta> findAll = cittaService.myFindAllCittaPageable(pageable);
        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/mygetalluserspagesize", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> myGetAllCittaPageSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Page<Citta> users = cittaService.myFindAllCittaPageSize(page, size);
        Map<String, Object> myResponse = new HashMap<>();
        myResponse.put("users", users);
//        myResponse.put("currentPage", users.getNumber());
//        myResponse.put("totalItems", users.getTotalElements());
//        myResponse.put("totalPages", users.getTotalPages());
        return myResponse;
    }
    
    @GetMapping(value = "/mygetallcittapagesizesort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Citta>> myGetAllCittaPageSizeSort(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size, @RequestParam(defaultValue = "id") String sort) {
      List<Citta> list = cittaService.myFindAllCittaPageSizeSort(page, size, sort);
      return new ResponseEntity<List<Citta>>(list, new HttpHeaders(), HttpStatus.OK); 
    }
    
    @GetMapping(value = "/mygetallcittasortbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Citta> myGetAllCittaSortByName() {
        return cittaService.myFindAllCittaSorted();
    }

}
