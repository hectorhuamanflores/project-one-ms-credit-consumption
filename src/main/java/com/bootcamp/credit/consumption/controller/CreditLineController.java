package com.bootcamp.credit.consumption.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.credit.consumption.dto.CreditConsumptionByIdCreditRequest;
import com.bootcamp.credit.consumption.dto.CreditLineUpdateRequest;
import com.bootcamp.credit.consumption.entity.CreditLine;
import com.bootcamp.credit.consumption.service.impl.CreditLineServiceImpl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/CreditLine")
public class CreditLineController {
	
	private final CreditLineServiceImpl creditLineServiceImpl;
	
	@PostMapping("/idCredit")
    public Mono<ResponseEntity<CreditLine>> getCreditLineByIdCredit(@RequestBody CreditConsumptionByIdCreditRequest creditConsumptionByIdCreditRequest){
    	
		 var creditLine =this.creditLineServiceImpl.getCreditLineByIdCredit(creditConsumptionByIdCreditRequest.getIdCredit());
		 return creditLine.map(ResponseEntity::ok)
	                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
	@PutMapping("/updateCreditLine")
    public Mono<ResponseEntity<CreditLine>> updateCreditLine(@RequestBody CreditLineUpdateRequest creditLineUpdateRequest){

        return this.creditLineServiceImpl.updateCreditLine(creditLineUpdateRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditLine> create(@RequestBody CreditLine creditLine){
		
        return this.creditLineServiceImpl.createCreditLine(creditLine);
        
    }
	
}
