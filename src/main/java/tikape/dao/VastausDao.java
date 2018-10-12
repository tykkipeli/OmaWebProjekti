package tikape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.Database;
import tikape.domain.Kysymys;
import tikape.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer>{
    
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet res = stmt.executeQuery();
        res.next();
        Vastaus vastaus = new Vastaus(res.getInt("id"),res.getInt("kysymysId"),res.getString("vastausteksti"),res.getBoolean("oikein"));
        stmt.close();
        res.close();
        conn.close();
        return vastaus;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        return null;
    }
    
    public List<Vastaus> findAll(int kysymysId) throws SQLException {
        List<Vastaus> vastaukset = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymysId = ?");
        stmt.setInt(1, kysymysId);
        ResultSet res = stmt.executeQuery();
        while(res.next()) {
            vastaukset.add(new Vastaus(res.getInt("id"),res.getInt("kysymysId"),res.getString("vastausteksti"),res.getBoolean("oikein")));
        }
        stmt.close();
        res.close();
        conn.close();
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus vastaus) throws SQLException {
        Connection conn = database.getConnection();   
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (kysymysId, vastausteksti, oikein) VALUES (?,?,?)");
        stmt.setInt(1, vastaus.getKysymysId());
        stmt.setString(2, vastaus.getVastausteksti());
        stmt.setBoolean(3, vastaus.getOikein());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();   
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close(); 
    }

    public void deleteAll(int kysymysId) throws SQLException{
        Connection conn = database.getConnection();   
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymysId = ?");
        stmt.setInt(1, kysymysId);
        stmt.executeUpdate();
        stmt.close();
        conn.close(); 
    }
    
}
