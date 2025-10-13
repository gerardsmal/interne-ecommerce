package com.betacom.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseBase> create(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			artS.create(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseBase> update(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			artS.update(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}		
		return ResponseEntity.status(status).body(r);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseBase> delete(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			artS.remove(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	
	
	@DeleteMapping("/removeFamiglia")
	public ResponseEntity<ResponseBase> removeFamiglia(@RequestBody (required = true) ArtistReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			artS.removeFamigliaArtist(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}	
		return ResponseEntity.status(status).body(r);
	}

	
	@PutMapping("/changeFamily")
	public ResponseEntity<ResponseBase> changeFamily(@RequestBody (required = true) ChangeFamilyReq req) {
		ResponseBase r = new ResponseBase();
		HttpStatus status = HttpStatus.OK;
		try {
			artS.changeFamily(req);
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/listArtistbyId")
	public ResponseEntity<ResponseObject<ArtistaDTO>> listArtistbyId(@RequestParam (required = true) Integer id){
		ResponseObject<ArtistaDTO> r = new ResponseObject<ArtistaDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(artS.listByArtista(id));
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@GetMapping("/list")
	public ResponseEntity<ResponseList<ArtistaDTO>> list(){
		ResponseList<ArtistaDTO> r = new ResponseList<ArtistaDTO>();
		HttpStatus status = HttpStatus.OK;
		try {
			r.setDati(artS.list());
			r.setRc(true);
		} catch (Exception e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
