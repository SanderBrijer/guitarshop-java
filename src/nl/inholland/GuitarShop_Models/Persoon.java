package nl.inholland.GuitarShop_Models;

public class Persoon {
    private static int Id;
    private String voornaam;
    private String achternaam;

    private String email;
    private int id;

    public Persoon(int id, String voornaam, String achternaam, String email) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        Id = Id++;
        this.id = id;
    }

    public String verkrijgVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String verkrijgAchternaam() {
        return achternaam;
    }

    public String verkrijgVolledigeNaam() {
        return voornaam + " " + achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public int verkrijgId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String verkrijgEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
