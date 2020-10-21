package nl.inholland.GuitarShop_Models;

public class BesteldeItem {
    private Integer aantalBesteld;
    private Artikel artikel;

        public BesteldeItem(Integer aantalBesteld, Artikel artikel) {
            this.aantalBesteld = aantalBesteld;
            this.artikel = artikel;
    }



    public Integer verkrijgAantalBesteld() {
        return aantalBesteld;
    }

    public Artikel verkrijgArtikel() {
        return artikel;
    }

    public void zetAantalBesteld(Integer aantalBesteld) {
        this.aantalBesteld = aantalBesteld;
    }

    public void zetArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

}
