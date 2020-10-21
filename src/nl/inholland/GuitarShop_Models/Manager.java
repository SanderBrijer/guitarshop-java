package nl.inholland.GuitarShop_Models;

public class Manager extends Gebruiker {

    public Manager(int id, String voornaam, String achternaam, String email, String gebruikersnaam, String wachtwoord) {
        super(id, voornaam, achternaam, email, Rol.Manager, gebruikersnaam, wachtwoord);
    }
}
