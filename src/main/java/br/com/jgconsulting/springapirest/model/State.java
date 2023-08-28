package br.com.jgconsulting.springapirest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class State {

    private Country country;
    private String name;
    private String uf;
    private int ddd;

}
