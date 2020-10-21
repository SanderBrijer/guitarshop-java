package nl.inholland.GuitarShop_Models;

public class Klant extends Persoon {

    private String adres;
    private String stad;
    private String telefoonnummer;

    public Klant(int id, String voornaam, String achternaam, String email, String adres, String stad, String telefoonnummer) {
        super(id, voornaam, achternaam, email);
        this.adres = adres;
        this.stad = stad;
        this.telefoonnummer = telefoonnummer;
    }


    public void zetAdres(String adres) {
        this.adres = adres;
    }

    public void zetStad(String stad) {
        this.stad = stad;
    }

    public void zetTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String verkrijgAdres() {
        return adres;
    }

    public String verkrijgStad() {
        return stad;
    }

    public String verkrijgTelefoonnummer() {
        return telefoonnummer;
    }
}
