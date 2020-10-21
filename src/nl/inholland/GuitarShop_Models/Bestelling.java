package nl.inholland.GuitarShop_Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bestelling {

    private Klant klant;


    private List<BesteldeItem> besteldeItems;

    public Bestelling()
    {
        besteldeItems = new ArrayList<BesteldeItem>();
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

    public void verwijderVanBesteldeItems(BesteldeItem besteldeItem) {
        this.besteldeItems.remove(besteldeItem);
    }

    public void toevoegenAanBesteldeItems(BesteldeItem besteldeItem) {
        this.besteldeItems.add(besteldeItem);
    }


    public List<BesteldeItem> verkrijgBesteldeItems() {
        return besteldeItems;
    }

    public void setBesteldeItems(List<BesteldeItem> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

}
