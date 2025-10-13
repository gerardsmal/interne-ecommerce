package com.betacom.ecommerce.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ecommerce.dto.AccountDTO;
import com.betacom.ecommerce.dto.CarelloDTO;
import com.betacom.ecommerce.dto.CarelloRigaDTO;
import com.betacom.ecommerce.dto.SigninDTO;
import com.betacom.ecommerce.enums.Role;
import com.betacom.ecommerce.models.Account;
import com.betacom.ecommerce.models.Prezzo;
import com.betacom.ecommerce.models.RigaCarello;
import com.betacom.ecommerce.repositories.IAccountRepository;
import com.betacom.ecommerce.requests.AccountReq;
import com.betacom.ecommerce.requests.SigninReq;
import com.betacom.ecommerce.services.interfaces.IAccountServices;
import com.betacom.ecommerce.services.interfaces.IValidationServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountImpl implements IAccountServices{

	private IAccountRepository accR;
	private IValidationServices  validS;
	private PasswordEncoder    encoder;

	private static String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	private static String capRegex = "^[0-9]{5}$";
	private static String telefonoRegex = "^(\\+39)?\\s?(3\\d{2}|0\\d{1,3})\\s?\\d{5,10}$";
	
	public AccountImpl(IAccountRepository accR, 
			IValidationServices validS,
			PasswordEncoder  encoder) {
		this.accR = accR;
		this.validS = validS;
		this.encoder = encoder;
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void create(AccountReq req) throws Exception {
		log.debug("create:" + req);
		

		validS.checkNotNull(req.getNome(), "account_no_nome");
		validS.checkNotNull(req.getCognome(), "account_no_cognome");
		
		validS.validateWithRegex(req.getEmail(), emailRegex, "account_email_ko");
		
		validS.checkNotNull(req.getCommune(), "account_no_comune");
		validS.checkNotNull(req.getVia(), "account_no_via");

		validS.validateWithRegex(req.getCap(), capRegex, "account_cap_ko");
		validS.validateWithRegex(req.getTelefono(), telefonoRegex, "account_telefono_ko");

		validS.checkNotNull(req.getUserName(), "account_no_username");
		validS.validatePassword(req.getPwd());

		Role role = null;
		try {
			role = Role.valueOf((req.getRole() == null) ? "error" : req.getRole());
		} catch (IllegalArgumentException e) {
			throw new Exception(validS.getMessaggio("account_ruolo_ko"));	
		}
		if (req.getSesso() == null) req.setSesso(true);
		
		Optional<Account> a = accR.findByUserName(req.getUserName());
		if (a.isPresent())
			throw new Exception(validS.getMessaggio("account_username_ko"));	
		
		
		Account acc = new Account();
		acc.setNome(req.getNome());
		acc.setCognome(req.getCognome());
		acc.setEmail(req.getEmail());
		acc.setCommune(req.getCommune());
		acc.setVia(req.getVia());
		acc.setCap(req.getCap());
		acc.setTelefono(req.getTelefono());
		acc.setUserName(req.getUserName());
		acc.setPwd(encoder.encode(req.getPwd()));  // encode password
		acc.setRole(role);
		acc.setDataCreazione(LocalDate.now());
		
		acc.setSesso(req.getSesso());
		acc.setStatus(true);
		accR.save(acc);
		
	}

	@Override
	public void update(AccountReq req) throws Exception {
		log.debug("update:" + req);
		Account acc = accR.findById(req.getId())
				.orElseThrow(() -> new Exception(validS.getMessaggio("account_ntfnd")));

		if (req.getNome() != null) acc.setNome(req.getNome());
		if (req.getCognome() != null)   acc.setCognome(req.getCognome());
		if (req.getEmail() != null) {
			validS.validateWithRegex(req.getEmail(), emailRegex, "account_email_ko");
			acc.setEmail(req.getEmail());					
		}
		
		if (req.getCommune() != null) acc.setCommune(req.getCommune());
		if (req.getVia() != null) acc.setVia(req.getVia());
		if (req.getCap() != null) {
			validS.validateWithRegex(req.getCap(), capRegex, "account_cap_ko");
			acc.setCap(req.getCap());
		}
		if (req.getTelefono() != null) {
			validS.validateWithRegex(req.getTelefono(), telefonoRegex, "account_telefono_ko");
			acc.setTelefono(req.getTelefono());
		}
		if (req.getUserName() != null) {
			if (!req.getUserName().equalsIgnoreCase(acc.getUserName())) {
				Optional<Account> a = accR.findByUserName(req.getUserName());
				if (a.isPresent())
					throw new Exception(validS.getMessaggio("account_username_ko"));	
				acc.setUserName(req.getUserName());				
			}
			
		}
		if (req.getPwd() != null) {
			validS.validatePassword(req.getPwd());		
			acc.setPwd(encoder.encode(req.getPwd()));  // encode password
		}
		
		if (req.getRole() != null) {
			Role role = null;
			try {
				role = Role.valueOf((req.getRole() == null) ? "error" : req.getRole());
			} catch (IllegalArgumentException e) {
				throw new Exception(validS.getMessaggio("account_ruolo_ko"));	
			}
			acc.setRole(role);
		}
		if (req.getSesso() != null) acc.setSesso(req.getSesso());
		
		if (req.getStatus() != null) acc.setStatus(req.getStatus());
		
		accR.save(acc);
		

		
	}


	@Override
	public SigninDTO login(SigninReq req) throws Exception {
		log.debug("login:" + req);
		Account user = accR.findByUserName(req.getUserName())
				.orElseThrow(() -> new Exception(validS.getMessaggio("account_invalid")));
		
		if (!encoder.matches(req.getPassword(), user.getPwd()))
			throw new Exception(validS.getMessaggio("account_invalid"));
		
		
		return SigninDTO.builder()
				.userID(user.getId())
				.role(user.getRole().toString())
				.build();
	}

	@Override
	public List<AccountDTO> list(
			Integer id,
			String nome,
			String cognome,
			String commune,
			String status,
			String role
			) throws Exception {
		log.debug("list" + id + "/" + nome + "/" + cognome + "/" + role);
		Boolean sta = null;
		Role ro = null;
		
		if (status != null) sta = (status.equalsIgnoreCase("Attivo")) ? true : false;
		
		if (role != null)  ro = Role.valueOf(role.trim().toUpperCase());
		
		List<Account> lA = accR.searchByFilter(id, nome, cognome, commune, sta, ro);
		
		return lA.stream()
				.map(a -> AccountDTO.builder()
						.id(a.getId())
						.nome(a.getNome())
						.cognome(a.getCognome())
						.sesso(a.getSesso() ? "M" :"F" )
						.telefono(a.getTelefono())
						.via(a.getVia())
						.commune(a.getCommune())
						.cap(a.getCap())
						.email(a.getEmail())
						.status(a.getStatus() ? "Attivo" : "Inattivo")
						.dataCreazione(a.getDataCreazione())
						.userName(a.getUserName())
						.role(a.getRole().toString())
						.carello(buildCarelloDTO(a))
						.build())
				.toList();
					
	}
	
	private CarelloDTO buildCarelloDTO(Account account){
		if (account.getCarello() == null)
			return null;
		try {
			List<CarelloRigaDTO> rigaCar = new ArrayList<CarelloRigaDTO>();
			double total = 0;
			for (RigaCarello riga : account.getCarello().getRigaCarello()) {
				Prezzo prezzo = validS.searchSupporto(riga.getProdotto().getPrezzo(), riga.getSupporto());
				total = total + (prezzo.getPrezzo() * riga.getQuantita());
				rigaCar.add(
						CarelloRigaDTO.builder()
							.dataCreazione(riga.getDataCreazione())
							.artist(riga.getProdotto().getArtista().getNome())
							.productName(riga.getProdotto().getDescrizione())
							.genere(riga.getProdotto().getFamiglia().getDescrizione())
							.prezzo(prezzo.getPrezzo())
							.supporto(prezzo.getSupporto().toString())
							.quantita(riga.getQuantita())
							.build()
						);
			}
			return CarelloDTO.builder()
					.prezzoTotale(total)
					.riga(rigaCar)
					.build();
		} catch (Exception e) {
			log.error("Error nella ricerca del prezzo;" + e.getMessage());
			return null;
		}	
	}


}
