package br.com.jgconsulting.springapirest.repository;

import br.com.jgconsulting.springapirest.model.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository {

    public List<Country> findAll();
    public Country findByName( String name );

}
