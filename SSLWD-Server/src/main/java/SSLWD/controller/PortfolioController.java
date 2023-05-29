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

import SSLWD.model.Category;
import SSLWD.model.Portfolio;
import SSLWD.repo.SslwdRepo;

@Controller
@RequestMapping(path ="/api")
@CrossOrigin("http://sslwd.s3-website-us-east-1.amazonaws.com")
public class PortfolioController {
    @Autowired
    SslwdRepo sslwdRepo;

    @GetMapping("/portfoliobyname")
    public ResponseEntity<Portfolio> findOrdersById(@RequestParam("name") String name) {
        Portfolio portfolio = new Portfolio();

        portfolio = sslwdRepo.findPortfolioByName(name);

        if (portfolio.getName() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        }
    }

    @GetMapping("/portfoliobycategory")
    public ResponseEntity <List<Portfolio>> findOrdersByCategory(@RequestParam("categoryId") Integer categoryId) {
        List<Portfolio> portfolios= new ArrayList<>();

        portfolios = sslwdRepo.findPortfolioByCategory(categoryId);

        if (portfolios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(portfolios, HttpStatus.OK);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> findCategory() {
        List<Category> categorys= new ArrayList<>();

        categorys = sslwdRepo.findCategory();

        if (categorys.isEmpty() == true) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categorys, HttpStatus.OK);
        }
    }
}
