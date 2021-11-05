package it.riccardofalzea.gestioneprenotazioni.CRUD;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.riccardofalzea.Citta;
import it.riccardofalzea.Edificio;
import it.riccardofalzea.Postazione;
import it.riccardofalzea.Prenotazione;
import it.riccardofalzea.Role;
import it.riccardofalzea.RoleType;
import it.riccardofalzea.TipoPostazione;
import it.riccardofalzea.User;
import it.riccardofalzea.gestioneprenotazioni.repository.CittaRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.EdificioRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.PostazioneRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.PrenotazioneRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.RoleRepository;
import it.riccardofalzea.gestioneprenotazioni.repository.UserRepository;

@Component
public class MyCrud implements CommandLineRunner {
	
	@Autowired
	CittaRepository cittaRepository;
	@Autowired
	EdificioRepository edificioRepository;
	@Autowired
	PostazioneRepository postazioneRepository;
	@Autowired
	PrenotazioneRepository prenotazioneRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void run(String... args) throws Exception {
		Citta cit= new Citta();
		cit.setNome("Ancona");
		cittaRepository.save(cit);
		
		Edificio edf= new Edificio();
		
		edf.setIndirizzo("viale corto");
		edf.setCitta(cit);
		edf.setNome("edificio2");
		edf.setPassEdificio("riccardo");
		edificioRepository.save(edf);
		
		Postazione posta= new Postazione();
		posta.setCodice("codice3");
		posta.setDescrizione("descrizione5");
		posta.setEdificio(edf);
		posta.setTipoPostazione(TipoPostazione.SALA_RIUNIONI);
		posta.setNumeroMassimoOccupati(100);
		postazioneRepository.save(posta);
		
		
		User user= new User();
		user.setActive(true);
		user.setEmail("email13@gmail.it");
		user.setNome("nome3");
		String hashedPassword= encoder.encode("password5");
		user.setPassword(hashedPassword);
		user.setUsername("username8");
		userRepository.save(user);
		
		User user1= new User();
		user1.setActive(true);
		user1.setEmail("email12@gmail.it");
		user1.setNome("nome2");
		String hashedPassword2= encoder.encode("password18");
		user1.setPassword(hashedPassword2);
		user1.setUsername("username25");
		userRepository.save(user1);
		
		Prenotazione pre= new Prenotazione();
		pre.setPostazione(posta);
		pre.setDataPrenotata(LocalDate.now());
		pre.setDataPrenotazione(LocalDate.of(2021, 8, 5));
		pre.setUser(user);
		prenotazioneRepository.save(pre);
		
		Role rol= new Role();
		rol.setRoleType(RoleType.ROLE_ADMIN);
		roleRepository.save(rol);
		
		Role rol1= new Role();
		rol1.setRoleType(RoleType.ROLE_USER);
		roleRepository.save(rol1);
		
		List<Citta> cit2= (List<Citta>) cittaRepository.findAll();
		cit2.forEach(a->System.out.println("citta nel db" +a.toString()));
	
		System.out.println(cittaRepository.findById((long) 1).toString());
	}

}
