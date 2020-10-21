package nl.inholland.GuitarShop_Models;

public class Verkoper extends Gebruiker{
    public Verkoper(int id, String voornaam, String achternaam, String email, String gebruikersnaam, String wachtwoord) {
        super(id, voornaam, achternaam, email, Rol.Verkoper, gebruikersnaam, wachtwoord);
    }
}
