package SSLWD.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm {
    private String id;
    private String name;
    private String email;
    private BigDecimal phone;
    private String subject;
    private String message;
    private String date_created;
}
