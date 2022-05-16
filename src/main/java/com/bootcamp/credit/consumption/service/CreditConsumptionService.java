package com.bootcamp.credit.consumption.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bootcamp.credit.consumption.entity.CreditConsumption;
import com.bootcamp.credit.consumption.entity.CreditLine;
import com.bootcamp.credit.consumption.property.Constantes;
import com.bootcamp.credit.consumption.repository.CreditConsumptionRepository;
import com.bootcamp.credit.consumption.repository.CreditLineRepository;
import com.bootcamp.credit.consumption.service.impl.CreditConsumptionServiceImpl;
import com.bootcamp.credit.consumption.service.impl.CreditLineServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditConsumptionService implements CreditConsumptionServiceImpl{
	private  final CreditConsumptionRepository creditConsumptionRepository;
	private final CreditLineService creditLineService;
    
	private RestTemplate restTemplate;
	
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
    		log.info(Constantes.SEPARADOR);
    		log.info(Constantes.INICIO + Constantes.ACT1_CREAR_CREDIT_CONSUMPTION);
    		log.info(Constantes.CREDIT_ID + credit.getIdCredit());
			log.info(Constantes.SEPARADOR);
			return creditLineService.getCreditLineByIdCredit(credit.getIdCredit())
				   .flatMap(object ->{
					       if(object.getAmountLine() >= credit.getAmountTrx()) {
					    	   return creditConsumptionRepository.save(credit);
					       }
						Mono<? extends CreditConsumption> a = null;
						return a;	   
				   });
    		/* return creditConsumptionRepository.save(credit); */
    	}else {
    		log.info(Constantes.SEPARADOR);
    		log.info(Constantes.ERROR);
    		log.info(Constantes.SEPARADOR);
    		throw new RuntimeException(Constantes.EXCEPTIONEMPTYNULL);
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
		log.info("INICIO_CREDIT_CONSUMTION");
		log.info("idCredit: "+idCredit);
		return creditConsumptionRepository.findByIdCredit(idCredit);
	}

	
}
