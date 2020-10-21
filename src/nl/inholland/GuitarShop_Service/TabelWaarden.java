package nl.inholland.GuitarShop_Service;

import javafx.collections.ObservableList;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Artikel;
import nl.inholland.GuitarShop_Models.Klant;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelWaarden {

    static Klant klant;

    static final ArrayList<Artikel> artikelenLijst = new ArrayList<Artikel>();


    public TabelWaarden() {
    }

    public ArrayList<Artikel> verkrijgArtikelenLijst() {
        return artikelenLijst;
    }

    public void verwijderArtikelUitLijst(Artikel artikel) {
        artikelenLijst.remove(artikel);
    }


    public void zetArtikelenLijst(ArrayList<Artikel> artikelenLijst) {
        artikelenLijst = artikelenLijst;
    }
    public void voegToeAanArtikelenLijst(Artikel artikel) {
        artikelenLijst.add(artikel);
    }

    public Klant verkrijgKlant() {
        return klant;
    }
    public void zetKlant(Klant klant) {
        this.klant = klant;
    }



}
