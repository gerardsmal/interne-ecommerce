package com.betacom.ecommerce.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.ecommerce.dto.ArtistaDTO;
import com.betacom.ecommerce.requests.ArtistReq;
import com.betacom.ecommerce.requests.ChangeFamilyReq;
import com.betacom.ecommerce.response.ResponseBase;
import com.betacom.ecommerce.response.ResponseList;
import com.betacom.ecommerce.response.ResponseObject;
import com.betacom.ecommerce.services.interfaces.IArtistServices;

@RestController
@RequestMapping("rest/artist")
public class ArtistController {

	private IArtistServices artS;

	public ArtistController(IArtistServices artS) {
		this.artS = artS;
	}

	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		try {
			artS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@PutMapping("/update")
	public ResponseBase update(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		try {
			artS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}

	@DeleteMapping("/delete")
	public ResponseBase delete(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		try {
			artS.remove(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}

	
	
	@DeleteMapping("/removeFamiglia")
	public ResponseBase removeFamiglia(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		try {
			artS.removeFamigliaArtist(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}

	
	@PutMapping("/changeFamily")
	public ResponseBase changeFamily(@RequestBody (required = true) ChangeFamilyReq req) {
		ResponseBase r = new ResponseBase();
		try {
			artS.changeFamily(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		
		return r;
	}
	
	@GetMapping("/listArtistbyId")
	public ResponseObject<ArtistaDTO> listArtistbyId(@RequestParam (required = true) Integer id){
		ResponseObject<ArtistaDTO> r = new ResponseObject<ArtistaDTO>();
		try {
			r.setDati(artS.listByArtista(id));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	@GetMapping("/list")
	public ResponseList<ArtistaDTO> list(){
		ResponseList<ArtistaDTO> r = new ResponseList<ArtistaDTO>();
		try {
			r.setDati(artS.list());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

}
