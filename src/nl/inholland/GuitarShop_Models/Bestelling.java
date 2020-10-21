package nl.inholland.GuitarShop_Models;

import java.time.LocalDate;
import java.util.List;

public class Bestelling {

    private Klant klant;

    private LocalDate datum;

    private List<BesteldeItem> besteldeItems;

    public Bestelling()
    {

    }

    public Klant verkrijgKlant() {
        return klant;
    }

    public void zetKlant(Klant klant) {
        this.klant = klant;
    }

    public void zetBesteldeItems(List<BesteldeItem> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

    public void toevoegenAanBesteldeItems(BesteldeItem besteldeItem) {
        this.besteldeItems.add(besteldeItem);
    }

    public LocalDate verkrijgDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<BesteldeItem> verkrijgBesteldeItems() {
        return besteldeItems;
    }

    public void setBesteldeItems(List<BesteldeItem> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

}
