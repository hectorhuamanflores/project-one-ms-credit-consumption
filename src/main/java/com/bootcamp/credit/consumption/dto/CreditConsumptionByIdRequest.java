package com.bootcamp.credit.consumption.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditConsumptionByIdRequest {
	
   private String idCreditConsumption;
   private Integer currency;
}
