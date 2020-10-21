package nl.inholland.GuitarShop_Models;

import java.util.List;

public class Bestelling {

    private Klant klant;

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

    public List<BesteldeItem> verkrijgBesteldeItems() {
        return besteldeItems;
    }

    public void zetBesteldeItems(List<BesteldeItem> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

    public void toevoegenAanBesteldeItems(BesteldeItem besteldeItem) {
        this.besteldeItems.add(besteldeItem);
    }
}
