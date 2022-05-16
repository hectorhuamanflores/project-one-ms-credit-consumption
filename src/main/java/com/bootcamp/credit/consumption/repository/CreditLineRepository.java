package com.bootcamp.credit.consumption.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.credit.consumption.entity.CreditConsumption;
import com.bootcamp.credit.consumption.entity.CreditLine;

import reactor.core.publisher.Mono;

@Repository
public interface CreditLineRepository  extends ReactiveCrudRepository<CreditLine,String>{
    
	Mono<CreditLine> findByIdCredit(String idCredit);
}
