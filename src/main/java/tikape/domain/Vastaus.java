package tikape.domain;

public class Vastaus {
    private String vastausteksti;
    private Boolean oikein;
    private int id;
    private int kysymysId;

    public Vastaus(int id, int kysymysId, String vastausteksti, Boolean oikein) {
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
        this.id = id;
        this.kysymysId = kysymysId;
    }

    public int getKysymysId() {
        return kysymysId;
    }

    public void setKysymysId(int kysymysId) {
        this.kysymysId = kysymysId;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getVastausteksti() {
        return vastausteksti;
    }

    public void setVastausteksti(String vastausteksti) {
        this.vastausteksti = vastausteksti;
    }

    public Boolean getOikein() {
        return oikein;
    }

    public void setOikein(Boolean oikein) {
        this.oikein = oikein;
    }
    
    
}
