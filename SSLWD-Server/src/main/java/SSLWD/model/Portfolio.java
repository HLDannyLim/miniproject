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
public class Portfolio {

    private String name;
    private String description;
    private String imageUrl;
    private String siteUrl;
    private String dateCreated;
    private Integer categoryId;

    public static Portfolio populate(SqlRowSet rs) {
        Portfolio p = new Portfolio();
        while (rs.next()) {
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setImageUrl(rs.getString("image_url"));
            p.setSiteUrl(rs.getString("site_url"));
            try{
                p.setDateCreated((rs.getObject("date_created")).toString());
            }catch(NullPointerException e){
                // System.out.println("---->>>"+e);
                p.setDateCreated(rs.getString("date_created"));
            }
            p.setCategoryId(rs.getInt("category_id"));
            
            }
            return p;
    }

    public static List<Portfolio> populateList(SqlRowSet rs) {
        List<Portfolio> result = new ArrayList<>();
        while (rs.next()) {
            Portfolio p = new Portfolio();
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setImageUrl(rs.getString("image_url"));
            p.setSiteUrl(rs.getString("site_url"));
            try{
                p.setDateCreated((rs.getObject("date_created")).toString());
            }catch(NullPointerException e){
                // System.out.println("---->>>"+e);
                p.setDateCreated(rs.getString("date_created"));
            }
            p.setCategoryId(rs.getInt("category_id"));
            
            result.add(p);
            }
            return result;
    }

}
