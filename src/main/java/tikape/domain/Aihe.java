package tikape.domain;

import java.util.Objects;

public class Aihe {
    private String nimi;
    private String kurssi;

    public Aihe(String nimi, String kurssi) {
        this.nimi = nimi;
        this.kurssi = kurssi;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKurssi() {
        return kurssi;
    }

    public void setKurssi(String kurssi) {
        this.kurssi = kurssi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aihe other = (Aihe) obj;
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        if (!Objects.equals(this.kurssi, other.kurssi)) {
            return false;
        }
        return true;
    }
    
    
}
