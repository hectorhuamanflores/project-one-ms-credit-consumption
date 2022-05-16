package com.bootcamp.credit.consumption.service.impl;

import com.bootcamp.credit.consumption.entity.CreditConsumption;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditConsumptionServiceImpl {

    public Flux<CreditConsumption> getAllCreditConsumption();
    public Mono<CreditConsumption> getCreditConsumptionById(String id);
    public Flux<CreditConsumption> getCreditConsumptionByIdCredit(String idCredit);
    public Mono<CreditConsumption> createCreditConsumption(CreditConsumption creditConsumption);
    public Mono<CreditConsumption> updateCreditConsumption(CreditConsumption creditConsumption);
    public Mono<CreditConsumption> deleteCreditConsumption(String id);

}
