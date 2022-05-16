package com.bootcamp.credit.consumption.service.impl;

import com.bootcamp.credit.consumption.dto.CreditLineUpdateRequest;
import com.bootcamp.credit.consumption.entity.CreditLine;

import reactor.core.publisher.Mono;

public interface CreditLineServiceImpl {
 
	
	public Mono<CreditLine> createCreditLine(CreditLine creditLine);
    public Mono<CreditLine> updateCreditLine(CreditLineUpdateRequest creditLine);
    public Mono<CreditLine> getCreditLineByIdCredit(String idCredit);
    
}
