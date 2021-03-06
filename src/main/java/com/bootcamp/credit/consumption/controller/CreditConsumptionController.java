package com.bootcamp.credit.consumption.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.credit.consumption.dto.CreditConsumptionByIdCreditRequest;
import com.bootcamp.credit.consumption.dto.CreditConsumptionByIdRequest;
import com.bootcamp.credit.consumption.entity.CreditConsumption;
import com.bootcamp.credit.consumption.service.CreditConsumptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/CreditConsumption")
public class CreditConsumptionController {

	private final CreditConsumptionService creditConsumptionService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CreditConsumption>>>getAllCreditConsumption() {
        Flux<CreditConsumption> list=this.creditConsumptionService.getAllCreditConsumption();
        return  Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list))
        		.defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/idCreditConsumption")
    public Mono<ResponseEntity<CreditConsumption>> getCreditConsumptionById(@RequestBody CreditConsumptionByIdRequest creditConsumptionByIdRequest){
        var Credit=this.creditConsumptionService.getCreditConsumptionById(creditConsumptionByIdRequest.getIdCreditConsumption());
        return Credit.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/idCredit")
    public Mono<ResponseEntity<Flux<CreditConsumption>>> getCreditConsumptionByIdCredit(@RequestBody CreditConsumptionByIdCreditRequest creditConsumptionByIdCreditRequest){
    	
    	Flux<CreditConsumption> list=this.creditConsumptionService.getCreditConsumptionByIdCredit(creditConsumptionByIdCreditRequest.getIdCredit());
        return  Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditConsumption> create(@RequestBody CreditConsumption Credit){
        return this.creditConsumptionService.createCreditConsumption(Credit);
    }

    @PutMapping("/updateCreditConsumption")
    public Mono<ResponseEntity<CreditConsumption>> updateCredit(@RequestBody CreditConsumption Credit){

        return this.creditConsumptionService.updateCreditConsumption(Credit)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCreditById(@PathVariable String id){
        return this.creditConsumptionService.deleteCreditConsumption(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
   
}
