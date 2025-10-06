package com.betacom.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="prodotto")
public class Prodotto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name="descrizione_prodotto",
			length=100,
			nullable = false)
	private String descrizione;
	
	@Column (nullable = false)
	private Double prezzo;
	
	@ManyToOne
	@JoinColumn (name="id_artista")
	private Artist artista;
	
	@ManyToOne
	@JoinColumn (name="id_famiglia")
	private Famiglia famiglia;
	
	
}
