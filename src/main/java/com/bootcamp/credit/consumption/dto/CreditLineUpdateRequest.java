package com.bootcamp.credit.consumption.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditLineUpdateRequest {
	
	private String idCredit;
	private Double amountLine;
	private String status;

}
