package br.com.jgconsulting.springapirest.service;

import br.com.jgconsulting.springapirest.controller.dto.CountryDTO;
import br.com.jgconsulting.springapirest.controller.dto.mapper.MapStructMapper;
import br.com.jgconsulting.springapirest.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository repository;

    private final  MapStructMapper mapper;

    public CountryService(CountryRepository repository, MapStructMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CountryDTO> getAllCountries() {
        return repository
                .findAll()
                .stream()
                .map( country -> mapper.toCountryDTO(country) )
                .collect( Collectors.toList() );
    }

}
