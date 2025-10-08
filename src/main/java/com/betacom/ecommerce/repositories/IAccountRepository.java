package com.betacom.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ecommerce.models.Account;

public interface IAccountRepository extends JpaRepository<Account, Integer>{
	Optional<Account> findByUserName(String user);

}
