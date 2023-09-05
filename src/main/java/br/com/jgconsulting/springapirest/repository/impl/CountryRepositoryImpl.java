package br.com.jgconsulting.springapirest.repository.impl;

import br.com.jgconsulting.springapirest.model.Country;
import br.com.jgconsulting.springapirest.repository.CountryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CountryRepositoryImpl implements CountryRepository {

    // TODO: Refactor to JPA...
    private static List<Country> all = new ArrayList(0);
    static {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final String json = IOUtils.toString( CountryRepositoryImpl.class.getResourceAsStream("/json/country.json"), StandardCharsets.UTF_8 );
            List<Country> countries = mapper.readValue(json, new TypeReference<List<Country>>() {});
            all.addAll( countries );
        } catch (IOException e) {
            log.error("Error when trying to load countries list from repository: {}", e.getMessage());
        }
    }

    @Override
    public List<Country> findAll() {
        return all;
    }

    @Override
    public Country findByName(String name) {
        return findAll()
                .stream()
                .filter( country -> country.getName().equalsIgnoreCase(name) )
                .findFirst()
                .get();
    }
}
