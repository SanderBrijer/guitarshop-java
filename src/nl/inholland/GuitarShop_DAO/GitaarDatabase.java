package nl.inholland.GuitarShop_DAO;

import nl.inholland.GuitarShop_Models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GitaarDatabase {

    private List<Klant> klanten = new ArrayList<>();

    public List<Klant> verkrijgKlanten() {
        return klanten;
    }


    private List<Artikel> artikelen = new ArrayList<>();

    public List<Artikel> verkrijgArtikelen() {
        return artikelen;
    }

    public Integer verkrijgVoorraadVanArtikel(Artikel artikel) {
        for(int i=0;i<artikelen.size();i++){

            if (artikelen.get(i).verkrijgId() == artikel.verkrijgId()) {
                return artikelen.get(i).verkrijgAantal();
            }
        }

        return 0;
    }


    private List<Manager> managers;

    public List<Manager> verkrijgManagers() {
        return managers;
    }


    private List<Verkoper> verkopers;

    public List<Verkoper> verkrijgVerkopers() {
        return verkopers;
    }

    public GitaarDatabase() {
        klanten = new ArrayList<>();
        managers = new ArrayList<>();
        verkopers = new ArrayList<>();

        inladen();
    }

    public void inladen() {
        leesKlanten();
        leesVerkopers();
        leesManagers();
        leesArtikelen();
    }

    // klanten
    private void leesKlanten() {
        try (Scanner klantenScanner = new Scanner(new File("src/Bestanden/klanten.csv"))) {
            while (true) {
                try {
                    String line = klantenScanner.nextLine();
                    String[] klantenArray = line.split(",");
                    Klant klant = new Klant(Integer.parseInt(klantenArray[0]), klantenArray[1], klantenArray[2], klantenArray[3], klantenArray[4], klantenArray[5], klantenArray[6]);
                    klanten.add(klant);
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // managers
    private void leesManagers() {
        try (Scanner managersScanner = new Scanner(new File("src/Bestanden/managers.csv"))) {
            while (true) {
                try {
                    String line = managersScanner.nextLine();
                    String[] managerArray = line.split(",");
                    Manager manager = new Manager(Integer.parseInt(managerArray[1]), managerArray[0], managerArray[2], managerArray[3], managerArray[4], managerArray[5]);
                    managers.add(manager);
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // verkopers
    private void leesVerkopers() {
        try (Scanner verkopersScanner = new Scanner(new File("src/Bestanden/verkopers.csv"))) {
            while (true) {
                try {
                    String line = verkopersScanner.nextLine();
                    String[] verkopersArray = line.split(",");
                    Verkoper verkoper = new Verkoper(Integer.parseInt(verkopersArray[1]), verkopersArray[0], verkopersArray[2], verkopersArray[3], verkopersArray[4], verkopersArray[5]);
                    verkopers.add(verkoper);
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // managers
    private void leesArtikelen() {
        try (Scanner artikelenScanner = new Scanner(new File("src/Bestanden/artikelen.csv"))) {
            while (true) {
                try {
                    String line = artikelenScanner.nextLine();
                    String[] artikelenArray = line.split(",");

                    Artikel artikel = new Artikel(Integer.parseInt(artikelenArray[1]), Integer.parseInt(artikelenArray[2]), artikelenArray[0], artikelenArray[3], Boolean.parseBoolean(artikelenArray[4]), TypeGitaar.valueOf(artikelenArray[5]), Double.parseDouble(artikelenArray[6]));
                    artikelen.add(artikel);
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Gebruiker checkInlog(String gebruikersnaam, String wachtwoord) {
        for (Gebruiker verkoper : verkopers) {
            if (verkoper.verkrijgGebruikersnaam().equals(gebruikersnaam) && verkoper.verkrijgWachtwoord().equals(wachtwoord)) {
                return verkoper;
            }
        }
        for (Manager manager : managers) {
            if (manager.verkrijgGebruikersnaam().equals(gebruikersnaam) && manager.verkrijgWachtwoord().equals(wachtwoord)) {
                return manager;
            }
        }
        return null;
    }

    public void verlaagVoorraadArtikel(Artikel artikel, Integer besteldAantal)
    {
        Integer huidigeVoorraadArtikel = artikel.verkrijgAantal();
        Integer nieuweVoorraadArtikel = huidigeVoorraadArtikel - besteldAantal;
        artikel.setAantal(nieuweVoorraadArtikel);
        for(int i=0;i<artikelen.size();i++){

            if (artikelen.get(i).verkrijgId() == artikel.verkrijgId()) {
                artikelen.get(i).setAantal(nieuweVoorraadArtikel);
            }
        }
    }

    public void verhoogVoorraadArtikel(Artikel artikel, Integer verhoging) {
        Integer huidigeVoorraadArtikel = artikel.verkrijgAantal();
        Integer nieuweVoorraadArtikel = huidigeVoorraadArtikel + verhoging;
        artikel.setAantal(nieuweVoorraadArtikel);
    }
}