/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.database.Database;
import tikape.domain.Aihe;
import tikape.domain.Kurssi;
import tikape.domain.Kysymys;

/**
 *
 * @author Olli Matilainen
 */
public class KysymysDao implements Dao<Kysymys, Integer>{
    
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet res = stmt.executeQuery();
        res.next();
        Kysymys kysymys = new Kysymys(res.getInt("id"),res.getString("kurssi"),res.getString("aihe"),res.getString("kysymysteksti"));
        stmt.close();
        res.close();
        conn.close();
        return kysymys;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        List<Kysymys> kysymykset = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
        ResultSet res = stmt.executeQuery();
        while(res.next()) {
            kysymykset.add(new Kysymys(res.getInt("id"),res.getString("kurssi"),res.getString("aihe"),res.getString("kysymysteksti")));
        }
        stmt.close();
        res.close();
        conn.close();
        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException {
        Connection conn = database.getConnection();   
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES (?,?,?)");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();   
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();      
    }

    public List<Kurssi> findKurssit(List<Kysymys> kysymykset) {
        List<Kurssi> kurssit = new ArrayList<>();
        for (Kysymys kysymys: kysymykset) {
            if (!kurssit.contains(new Kurssi(kysymys.getKurssi()))) {
                kurssit.add(new Kurssi(kysymys.getKurssi()));
            }
        }
        return kurssit;
    }

    public List<Aihe> findAiheet(List<Kysymys> kysymykset) {
        List<Aihe> aiheet = new ArrayList<>();
        for (Kysymys kysymys: kysymykset) {
            if (!aiheet.contains(new Aihe(kysymys.getAihe(),kysymys.getKurssi()))) {
                aiheet.add(new Aihe(kysymys.getAihe(),kysymys.getKurssi()));
            }
        }
        return aiheet;
    }
    
}
