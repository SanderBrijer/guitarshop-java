package nl.inholland.GuitarShop_Models;

public class Gebruiker extends Persoon {

    private Rol rol;
    private String wachtwoord;
    private String gebruikersnaam;

    public Gebruiker(int id, String voornaam, String achternaam, String email, Rol rol, String gebruikersnaam, String wachtwoord) {
        super(id, voornaam, achternaam, email);

        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.rol = rol;
    }


    public String verkrijgWachtwoord() {
        return wachtwoord;
    }

    public String verkrijgGebruikersnaam() {
        return gebruikersnaam;
    }
    public Rol verkrijgRol() {
        return rol;
    }
}
