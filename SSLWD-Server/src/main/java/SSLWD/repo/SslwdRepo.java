package SSLWD.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import SSLWD.model.Category;
import SSLWD.model.ContactForm;
import SSLWD.model.Country;
import SSLWD.model.OrderDetails;
import SSLWD.model.Portfolio;
import SSLWD.model.PurchaseResponse;
import SSLWD.model.State;

@Repository
public class SslwdRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Portfolio findPortfolioByName(String name){

        String findByName = "select * from portfolio where name = ?";
        Portfolio result = new Portfolio();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findByName, name);
        result = Portfolio.populate(rs);
        return result;
        
    }

    public List<Portfolio> findPortfolioByCategory(Integer categoryId) {
        if(categoryId ==0){
            String findByByCategory = "select * from portfolio";
            List<Portfolio> result = new ArrayList<>();
    
            final SqlRowSet rs = jdbcTemplate.queryForRowSet(findByByCategory);
            result = Portfolio.populateList(rs);
            return result;
        }else{
            String findByByCategory = "select * from portfolio where category_id = ?";
            List<Portfolio> result = new ArrayList<>();
    
            final SqlRowSet rs = jdbcTemplate.queryForRowSet(findByByCategory, categoryId);
            result = Portfolio.populateList(rs);
            return result;
        }
    }

    public List<Category> findCategory() {

        String findCategory = "select * from portfolio_category";
        List<Category> result = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findCategory);
        result = Category.populate(rs);
        return result;
    }

    public List<Country> findCountry() {
        String findCountry = "select * from countries";
        List<Country> result = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findCountry);
        result = Country.populateList(rs);
        return result;
    }

    public List<State> findState(Integer countryId) {
        String findState = "select * from states where country_id = ?";
        List<State> result = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findState, countryId);
        result = State.populateList(rs);
        return result;
    }

    
    public boolean insertOrderDetails(OrderDetails o) {
        String insertSQL="""
            INSERT INTO `order_details` (`first_name`, `last_name`, `email`,
             `address1`, `address2`, `phone`, `state`, `country`, 
             `city`, `zip`, `service`, 
             `order_number`) 

            VALUES (?, ?, ?, ?, ?, ?, 
            ?, ?, ?, ?, ?, ?)
            """;
        Integer result = jdbcTemplate.update(insertSQL,o.getFirstName(),o.getLastName(),o.getEmail(),o.getAddress1(),o.getAddress2(),o.getPhone(),o.getState(),o.getCountry(),o.getCity()
        ,o.getZip(),o.getService(),o.getOrderTrackingNumber());

        return result>0?true:false;

    }


    public OrderDetails findCustomerDetails(String invoiceNumber) {
        String findByInvoiceNumber = "select * from customer_details where invoice_number = ?";
        OrderDetails result = new OrderDetails();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findByInvoiceNumber, invoiceNumber);
        result = OrderDetails.populate(rs);
        return result;
    }

    public PurchaseResponse insertForm(ContactForm cf) {
        String insertSQL="""
            INSERT INTO `contact_form` (`id`, `name`, `email`,
             `phone`, `subject`, `message`) 

            VALUES (?, ?, ?, ?, ?, ?)
            """;

        String QueryNumber = generateQueryTrackingNumber();
        Integer result = jdbcTemplate.update(insertSQL,QueryNumber,cf.getName(),cf.getEmail(),cf.getPhone(),cf.getSubject(),cf.getMessage());

        if (result > 0){
            return new PurchaseResponse(QueryNumber);
        }else {
            return new PurchaseResponse("");
        }


    }

    private String generateQueryTrackingNumber() {
        // generate a random UUID number (UUID version-4)

        return UUID.randomUUID().toString();
    }


}
