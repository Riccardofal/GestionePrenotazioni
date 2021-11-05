package it.riccardofalzea.gestioneprenotazioni.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.riccardofalzea.Postazione;
import it.riccardofalzea.Prenotazione;
import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.repository.PostazioneRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.PrenotazioneRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.UserRepository;

@Service
public class PrenotazioneService {
	@Value("${gestioneprenotazioni.giornianticipoprenotazione}")
    int giorniAnticipoPrenotazione;
	
	@Autowired
	PrenotazioneRepository preRep;
	
	@Autowired
	PostazioneRepository postazioneRepo;
	
	@Autowired
	UserRepository userRepo;

	public List<Optional<Postazione>> findByPostazione(Postazione postazione) {
		return preRep.findByPostazione(postazione);
	}
	public List<Optional<Postazione>> findByPostazioneUser ( User user, LocalDate data){
		return this.findByPostazioneUser(user, data);
	}
	
	public boolean savePrenotazione(LocalDate dataPrenotazione, Long idPostazione, Long idUser) {
		if(checkDataPrenotazione(dataPrenotazione)) {
			Prenotazione preno= new Prenotazione();
			preno.setDataPrenotata(dataPrenotazione);
			LocalDate atm=LocalDate.now();
			preno.setDataPrenotazione(atm);
			preno.setPostazione(this.postazioneRepo.getById(idPostazione));
			preno.setUser(this.userRepo.getById(idUser));
			this.preRep.save(preno);
			 return true;
		}else
			return false;
			
		}
//	private boolean controlloData(LocalDate dataPrenotazione) {
//		LocalDate atm= LocalDate.now();
//		int mese=atm.getMonthValue();
//		int giorno= atm.getDayOfYear();
//		int anno= atm.getYear();
//		dataPrenotazione.minusDays(giorno);
//		dataPrenotazione.minusMonths(mese);
//		dataPrenotazione.minusYears(anno);
//		int  daysPre= dataPrenotazione.getDayOfMonth();
//		if(daysPre>2)
//		return false;
//		else
//			return true;
//	}
	private boolean checkDataPrenotazione(LocalDate dataPrenotazione) {
        LocalDate now = LocalDate.now();
        return dataPrenotazione.minus(giorniAnticipoPrenotazione, ChronoUnit.DAYS).isAfter(now);
    }
	
	public Page<Prenotazione> myFindAllPrenotazionePageable(Pageable pageable) {
	       return preRep.findAll(pageable);
	}
	
	// Ordinamento
	   public List<Prenotazione> myFindAllPrenotazioni() {
	       return preRep.findByOrderByDataPrenotataDesc();
	   }
	
	
	}
	
	

