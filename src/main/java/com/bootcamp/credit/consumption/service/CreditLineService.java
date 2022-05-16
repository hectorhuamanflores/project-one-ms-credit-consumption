package com.bootcamp.credit.consumption.service;

import java.time.LocalDate;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.bootcamp.credit.consumption.dto.CreditLineUpdateRequest;
import com.bootcamp.credit.consumption.entity.CreditLine;
import com.bootcamp.credit.consumption.repository.CreditLineRepository;
import com.bootcamp.credit.consumption.service.impl.CreditLineServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditLineService implements CreditLineServiceImpl{
	
	private  final CreditLineRepository creditLineRepository;
	
	@Override
	public Mono<CreditLine> createCreditLine(CreditLine creditLine) {
		if(creditLine !=null) {
    		log.info("INICIO_CREACION_CREDIT");
    		log.info("documentNumber: "+creditLine.getIdCredit());
    		return creditLineRepository.save(creditLine);
    	}else {
    		log.error("Credit Line is null");
    		throw new RuntimeException("Credit is null");
    	}
	}

	@Override
	public Mono<CreditLine> updateCreditLine(CreditLineUpdateRequest creditLine) {
		LocalDate date = LocalDate.now(); 
        return creditLineRepository.findByIdCredit(creditLine.getIdCredit())
                .flatMap( object ->{
                	object.setAmountLine(creditLine.getAmountLine());
                	//object.setDateLastModification(date );
                	object.setStatus(creditLine.getStatus());
                    return creditLineRepository.save(object);
                 });
	}

	@Override
	public Mono<CreditLine> getCreditLineByIdCredit(String idCredit) {
		// TODO Auto-generated method stub
		return creditLineRepository.findByIdCredit(idCredit);
	}

}
