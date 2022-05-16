package com.bootcamp.credit.consumption.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(value = "CREDIT_LINE")
public class CreditLine {
	
	@Id
    private String  id;                     // Identificador Linea de Credito
    private String  idCredit;               // Identificador del credito
    private Double  amountLine;             // monto de la linea de credito
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateStar;             // Fecha de creacion
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateLastModification; // Fecha de la ultima modificacion
    private String status;                  // activo - inactivo - cerrado

}
