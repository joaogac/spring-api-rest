package br.com.jgconsulting.springapirest.controller;

import br.com.jgconsulting.springapirest.controller.dto.CountryDTO;
import br.com.jgconsulting.springapirest.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping()
    public List<CountryDTO> getAll() {
        return service.getAllCountries();
    }

    @GetMapping("/search")
    public CountryDTO getByName(@RequestParam String name) {
        CountryDTO ret = service.getCountryByName(name);
        if ( "Brazil".equalsIgnoreCase( name ) ) {
            ret.setName("Brasil");
        }
        else if ( "USA".equalsIgnoreCase( name ) ) {
            ret.setName("Estados Unidos");
        }
        else if ( "Canada".equalsIgnoreCase( name ) ) {
            ret.setName("Canadá");
        } else {
            // does nothing
        }
        return ret;
    }
}
