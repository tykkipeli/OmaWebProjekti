package tikape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.KysymysDao;
import tikape.dao.VastausDao;
import tikape.database.Database;
import tikape.domain.Aihe;
import tikape.domain.Kurssi;
import tikape.domain.Kysymys;
import tikape.domain.Vastaus;

public class Main {

    public static void main(String[] args) throws Exception{
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        Database database = new Database("jdbc:sqlite:kysymykset.db");
        KysymysDao kysymykset = new KysymysDao(database);
        VastausDao vastaukset = new VastausDao(database);
        
        Spark.get("/sivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("teksti", "No mutta nythän pomppelehtii!");
            List<Kysymys> fragen = kysymykset.findAll();
            List<Kurssi> kurssit = kysymykset.findKurssit(fragen);
            List<Aihe> aiheet = kysymykset.findAiheet(fragen);
            map.put("kysymykset", fragen);
            map.put("kurssit", kurssit);
            map.put("aiheet", aiheet);
            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/vastaus/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            Kysymys kysymys = kysymykset.findOne(id);
            List<Vastaus> answers = vastaukset.findAll(id);
            map.put("teksti", kysymys.getKysymysteksti());
            map.put("kurssi", kysymys.getKurssi());
            map.put("aihe", kysymys.getAihe());
            ArrayList<Kysymys> frage = new ArrayList<>();
            frage.add(kysymys);
            map.put("kysymykset", frage);
            map.put("vastaukset", answers);
            return new ModelAndView(map, "vastaus");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/sivu", (req, res) -> {
            Kysymys kysymys = new Kysymys(-1, req.queryParams("kurssi"),req.queryParams("aihe"),req.queryParams("kysymysteksti"));
            kysymykset.saveOrUpdate(kysymys);
            res.redirect("/sivu");
            return "";
        });
        
        Spark.post("/vastaus/:id", (req, res) -> {
            boolean totuus = true;
            if (req.queryParams("totta") == null) totuus = false;
            Vastaus vastaus = new Vastaus(-1, Integer.parseInt(req.params(":id")),req.queryParams("vastausteksti"),totuus);
            vastaukset.saveOrUpdate(vastaus);
            res.redirect("/vastaus/"+req.params(":id"));
            return "";
        });
        
        Spark.post("/poistakysymys/:id", (req, res) -> {
            vastaukset.deleteAll(Integer.parseInt(req.params(":id")));
            kysymykset.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/sivu");
            return "";
        });
        
        Spark.post("/poistavastaus/:id", (req, res) -> {
            Vastaus vastaus = vastaukset.findOne(Integer.parseInt(req.params(":id")));
            vastaukset.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/vastaus/"+vastaus.getKysymysId());
            return "";
        });
        
    }

    
}
