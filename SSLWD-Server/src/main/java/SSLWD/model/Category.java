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
public class Category {
    
    private Integer id;
    private String categoryName;


    public static List<Category> populate(SqlRowSet rs) {
        List<Category> result = new ArrayList<Category>();
        while (rs.next()) {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setCategoryName(rs.getString("category_name"));

            result.add(c);
            }
            return result;
    }

}
