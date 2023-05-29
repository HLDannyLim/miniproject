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
public class State {

    private Integer id;
    private Integer countryId;
    private String name;

    public static List<State> populateList(SqlRowSet rs) {
        List<State> result = new ArrayList<>();
        while (rs.next()) {
            State c = new State();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCountryId(rs.getInt("country_id"));
        
            result.add(c);
            }
            return result;
    }
}
