package br.com.jgconsulting.springapirest.controller.dto.mapper;

import br.com.jgconsulting.springapirest.controller.dto.CountryDTO;
import br.com.jgconsulting.springapirest.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CountryDTO toCountryDTO(Country country);

}
