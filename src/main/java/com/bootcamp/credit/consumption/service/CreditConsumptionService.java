package com.bootcamp.credit.consumption.service;

import org.springframework.stereotype.Service;

import com.bootcamp.credit.consumption.entity.CreditConsumption;
import com.bootcamp.credit.consumption.repository.CreditConsumptionRepository;
import com.bootcamp.credit.consumption.service.impl.CreditConsumptionServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditConsumptionService implements CreditConsumptionServiceImpl{
	private  final CreditConsumptionRepository creditConsumptionRepository;

    @Override
    public Flux<CreditConsumption> getAllCreditConsumption() {
        return creditConsumptionRepository.findAll();
    }

    @Override
    public Mono<CreditConsumption> getCreditConsumptionById(String id) {
        return creditConsumptionRepository.findById(id);
    }

    @Override
    public Mono<CreditConsumption> createCreditConsumption(CreditConsumption credit) {
    	if(credit !=null) {
    		log.error("INICIO_CREACION_CREDIT");
    		log.info("documentNumber: "+credit.getIdCredit());
    		return creditConsumptionRepository.save(credit);
    	}else {
    		log.error("Credit is null");
    		throw new RuntimeException("Credit is null");
    	}
       
    }

    @Override
    public Mono<CreditConsumption> updateCreditConsumption(CreditConsumption creditConsumption) {
    	
        
        return creditConsumptionRepository.findById(creditConsumption.getId())
                .flatMap( object ->{
                	object.setIdCredit(creditConsumption.getIdCredit());
                	object.setTyTrxCredit(creditConsumption.getTyTrxCredit());
                	object.setCurrency(creditConsumption.getCurrency());
                	object.setAmountTrx(creditConsumption.getAmountTrx());
                	object.setDateStar(creditConsumption.getDateStar());
                    return creditConsumptionRepository.save(object);
                 });
    }

    @Override
    public Mono<CreditConsumption> deleteCreditConsumption(String id) {
        return creditConsumptionRepository.findById(id)
                .flatMap(existscreditConsumptionRepository -> creditConsumptionRepository.delete(existscreditConsumptionRepository)
                        .then(Mono.just(existscreditConsumptionRepository)));
    }

	@Override
	public Flux<CreditConsumption> getCreditConsumptionByIdCredit(String idCredit) {
		log.error("INICIO_CREDIT_CONSUMTION");
		log.info("idCredit: "+idCredit);
		return creditConsumptionRepository.findByIdCredit(idCredit);
	}

	
}
