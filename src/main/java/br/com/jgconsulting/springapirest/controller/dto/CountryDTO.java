package br.com.jgconsulting.springapirest.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

    private String name;
    private int ddi;

}
