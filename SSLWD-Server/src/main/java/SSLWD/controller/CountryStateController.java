package SSLWD.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import SSLWD.model.Country;
import SSLWD.model.State;
import SSLWD.repo.SslwdRepo;

@Controller
@RequestMapping(path ="/api")
@CrossOrigin("http://sslwd.s3-website-us-east-1.amazonaws.com")
public class CountryStateController {
    @Autowired
    SslwdRepo sslwdRepo;

    @GetMapping("/country")
    public ResponseEntity <List<Country>> findCountry() {
        List<Country> countries= new ArrayList<>();

        countries = sslwdRepo.findCountry();

        if (countries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(countries, HttpStatus.OK);
        }
    }

    @GetMapping("/state")
    public ResponseEntity <List<State>> findState(@RequestParam("countryid") Integer countryId) {
        List<State> states= new ArrayList<>();

        states = sslwdRepo.findState(countryId);

        if (states.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(states, HttpStatus.OK);
        }
    }
}
