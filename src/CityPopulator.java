//import com.google.gson.Gson;
//import dataaccess.db.DBConnection;
/*
import logic.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.json.*;*/




//Bertram
//public class CityPopulator {
    //This code should only be executed once.

/*    public void fetchAndStoreCities() throws Exception {
        DBConnection dbcon = new DBConnection(Constants.DB_DRIVER, Constants.DB_USER, Constants.DB_PASSWORD);
        List<String[]> cities = new LinkedList<>();
        JSONArray results = new JSONArray(readUrl("https://dawa.aws.dk/postnumre"));
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        try {
            for (int i = 0; i < results.length(); i++) {
                String sql = "INSERT INTO privatboligen.zipcodes VALUES (?, ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(results.getJSONObject(i).getString("nr")));
                pstmt.setString(2, results.getJSONObject(i).getString("navn"));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}*/
