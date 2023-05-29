package SSLWD.model;



import java.math.BigDecimal;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {


    private String firstName;
    private String lastName;
    private String email;
    private String address1;
    private String address2;
    private BigDecimal phone;
    private String state;
    private String country;
    private String city;
    private String zip;
    private String service;
    private String orderTrackingNumber;
    private String dateCreated;


    public static OrderDetails populate(SqlRowSet rs) {
        OrderDetails c = new OrderDetails();
        while (rs.next()) {
            c.setFirstName(rs.getString("first_name"));
            c.setLastName(rs.getString("last_name"));
            c.setEmail(rs.getString("email"));
            c.setAddress1(rs.getString("address1"));
            c.setAddress2(rs.getString("address2"));
            c.setPhone(rs.getBigDecimal("phone"));
            c.setState(rs.getString("state"));
            c.setCountry(rs.getString("country"));
            c.setCity(rs.getString("city"));
            c.setZip(rs.getString("zip"));
            c.setService(rs.getString("service"));
            c.setOrderTrackingNumber(rs.getString("order_number"));
            try{
                c.setDateCreated((rs.getObject("date_created")).toString());
            }catch(NullPointerException e){
                // System.out.println("---->>>"+e);
                c.setDateCreated(rs.getString("date_created"));
            }
            
            }
            return c;
    }

}
