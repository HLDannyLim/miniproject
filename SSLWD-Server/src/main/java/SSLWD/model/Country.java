package SSLWD.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    private Integer id;
    private String code;
    private String name;
    private String code2;

    public static List<Country> populateList(SqlRowSet rs) {
        List<Country> result = new ArrayList<>();
        while (rs.next()) {
            Country c = new Country();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCode(rs.getString("iso3"));
            c.setCode2(rs.getString("iso2"));
        
            result.add(c);
            }
            return result;
    }

}
