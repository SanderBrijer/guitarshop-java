package nl.inholland.GuitarShop_Service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Gebruiker;
import nl.inholland.GuitarShop_Models.Rol;
import nl.inholland.GuitarShop_UI.BestellingLijst;
import nl.inholland.GuitarShop_UI.BestellingMaken;
import nl.inholland.GuitarShop_UI.Dashboard;
import nl.inholland.GuitarShop_UI.Voorraadonderhoud;

import javax.swing.*;

public class MenuBarMaker extends MenuBar {

    private MenuBar menu;
    private Stage window;
    private Gebruiker ingelogdeGebruiker;
    private GitaarDatabase gitaarDatabase;
    private MenuItem verkopenBestellingMenuItem;
    private MenuItem verkopenBestellingenlijstMenuItem;
    private MenuItem voorraadBestellingMenuItem;
    private MenuItem voorraadBeheerMenuItem;
    MenuItem dashboardMenuItem;

    public MenuBarMaker() {
    }


    public MenuBar verkrijgMenu() {
        return menu;
    }

    public MenuItem verkrijgVerkopenBestellingMenuItem() {
        return verkopenBestellingMenuItem;
    }

    public MenuItem verkrijgVerkopenBestellingenlijstMenuItem() {
        return verkopenBestellingenlijstMenuItem;
    }

    public MenuItem verkrijgVoorraadBestellingMenuItem() {
        return voorraadBestellingMenuItem;
    }

    public MenuItem verkrijgVoorraadBeheerMenuItem() {
        return voorraadBeheerMenuItem;
    }

    public MenuItem verkrijgDashboardMenuItem() {
        return dashboardMenuItem;
    }

    public MenuBarMaker(Gebruiker ingelogdeGebruiker, Stage window, GitaarDatabase gitaarDatabase) {
        this.window = window;
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        this.gitaarDatabase = gitaarDatabase;
        //MENU
        menu = new MenuBar();

            //Dashboard
        Menu dashboardMenu = new Menu("Algemeen");

        dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenu.getItems().add(dashboardMenuItem);

        //Sales
        Menu verkopenMenu = new Menu("Verkopen");

        verkopenBestellingMenuItem = new MenuItem("Bestelling aanmaken");
        verkopenMenu.getItems().add(verkopenBestellingMenuItem);

        verkopenBestellingenlijstMenuItem = new MenuItem("Bestellingenlijst");
        verkopenMenu.getItems().add(verkopenBestellingenlijstMenuItem);

        if (ingelogdeGebruiker.verkrijgRol() == Rol.Verkoper) {

            menu.getMenus().addAll(dashboardMenu, verkopenMenu);
        }

        //Voorraad
        Menu voorraadMenu = new Menu("Voorraad");

        voorraadBestellingMenuItem = new MenuItem("Bestellingenlijst");
        voorraadMenu.getItems().add(voorraadBestellingMenuItem);

        voorraadBeheerMenuItem = new MenuItem("Voorraadbeheer");
        voorraadMenu.getItems().add(voorraadBeheerMenuItem);


        if (ingelogdeGebruiker.verkrijgRol() == Rol.Manager) {
            menu.getMenus().addAll(dashboardMenu, voorraadMenu);
        }


        dashboardMenuItem.setOnAction(actionEvent -> {
            window.close();
            new Dashboard(ingelogdeGebruiker, gitaarDatabase);
        });



        verkopenBestellingMenuItem.setOnAction(actionEvent -> {
            window.close();
            new BestellingMaken(ingelogdeGebruiker, gitaarDatabase);
        });

        verkopenBestellingenlijstMenuItem.setOnAction(actionEvent -> {
            window.close();
            new BestellingLijst(ingelogdeGebruiker, gitaarDatabase);
        });

        voorraadBestellingMenuItem.setOnAction(actionEvent -> {
            window.close();
            new BestellingLijst(ingelogdeGebruiker, gitaarDatabase);
        });

        voorraadBeheerMenuItem.setOnAction(actionEvent -> {
            window.close();
            new Voorraadonderhoud(ingelogdeGebruiker, gitaarDatabase);
        });
    }

    public void zetMenu(MenuBar menu) {
        this.menu = menu;
    }
}
