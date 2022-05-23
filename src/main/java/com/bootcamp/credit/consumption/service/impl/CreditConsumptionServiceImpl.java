package com.bootcamp.credit.consumption.service.impl;

import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.credit.consumption.dto.CreditLineByIdRequest;
import com.bootcamp.credit.consumption.dto.CreditLineByIdResponse;
import com.bootcamp.credit.consumption.dto.CreditUpdateForConsPayRequest;
import com.bootcamp.credit.consumption.dto.CreditUpdateForConsPayResponse;
import com.bootcamp.credit.consumption.entity.CreditConsumption;
import com.bootcamp.credit.consumption.repository.CreditConsumptionRepository;
import com.bootcamp.credit.consumption.service.CreditConsumptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditConsumptionServiceImpl implements CreditConsumptionService{
	private final CreditConsumptionRepository creditConsumptionRepository;
    
	private WebClient creditServiceClient = WebClient.builder()
		      .baseUrl("http://localhost:8095")
		      .build();
	
	private Function<CreditLineByIdRequest, Mono<CreditLineByIdResponse>> msCreditbyId = (objeto) -> creditServiceClient.post()
			.uri("/CreditLine/id/")
			.body(Mono.just(objeto), CreditLineByIdResponse.class)
			.retrieve()
			.bodyToMono(CreditLineByIdResponse.class);
	
	private Function<CreditUpdateForConsPayRequest, Mono<CreditUpdateForConsPayResponse>> msCreditForConsPay = (objeto) -> creditServiceClient.put()
			.uri("/CreditLine/updateCreditConsumptionPayment/")
			.body(Mono.just(objeto), CreditUpdateForConsPayResponse.class)
			.retrieve()
			.bodyToMono(CreditUpdateForConsPayResponse.class);
	
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
    	CreditLineByIdRequest creditLineById = CreditLineByIdRequest.builder()
    			.idCredit(credit.getIdCredit())
    			.build();
    	CreditUpdateForConsPayRequest creditUpdateForConsPay = CreditUpdateForConsPayRequest.builder()
    			.idCredit(credit.getIdCredit())
    			.type(credit.getTyTrxCredit())
    			.amount(credit.getAmountTrx())
    			.build();
    	//consultar linea de credito por usar
    	Mono<CreditLineByIdResponse> consultMsCredit = msCreditbyId.apply(creditLineById);

    	
    		 return consultMsCredit.flatMap(result -> {
    			 if(result.getAvailableCredit() < credit.getAmountTrx()) {
    			   log.error("Monto del consumo excesivo");	
    			   return null;
    			 }else {
    				 log.error("Entro al servicio");		 
    				 Mono<CreditUpdateForConsPayResponse> f = msCreditForConsPay.apply(creditUpdateForConsPay);
    			     log.error("Salio del servicio");	
    			     return f.flatMap(ra ->creditConsumptionRepository.save(credit));
    			 } 			  

    		 });  	
       
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
