package nl.inholland.GuitarShop_Service;


import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Artikel;

import java.util.List;

public class Artikelen_Service {
    private GitaarDatabase gitaarDatabase;

    public Artikelen_Service()
    {

    }

    public List<Artikel> verkrijgAlleArtikelen()
    {
        return gitaarDatabase.verkrijgArtikelen();
    }

    public Integer verkrijgVoorraadVanArtikel(Artikel artikel)
    {
        return gitaarDatabase.verkrijgVoorraadVanArtikel(artikel);
    }

}

