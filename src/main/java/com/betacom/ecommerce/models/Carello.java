package com.betacom.ecommerce.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table (name="carello")
public class Carello {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="data_creazione")
	private LocalDate dataCreazione;
	
	@OneToOne
	@JoinColumn(
			name="account_id",
			referencedColumnName = "id"
			)
	
	private Account account;
}
