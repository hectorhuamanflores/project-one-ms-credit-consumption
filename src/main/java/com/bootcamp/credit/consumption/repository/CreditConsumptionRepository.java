package com.bootcamp.credit.consumption.repository;

import com.bootcamp.credit.consumption.entity.CreditConsumption;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditConsumptionRepository  extends ReactiveCrudRepository<CreditConsumption,String> {
    /*
     * find(loQuetrae)By(loQueBusca)
     * findByNombreContainingOrApellidoContaining(String nombre,String apellido);
     * 
     */
	Flux<CreditConsumption> findByIdCredit(String idCredit);
	
}
