package nl.inholland.GuitarShop_UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_Models.*;

import java.util.Calendar;
import java.util.Date;

public class Dashboard {
    Stage window;

    public Dashboard(Gebruiker ingelogdeGebruiker) {
        window = new Stage();

        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Dashboard");

        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        content.setPadding(new Insets(10));

        Label welkomsberichtLabel = new Label(String.format("Welkom %s", ingelogdeGebruiker.verkrijgVoornaam() + " " + ingelogdeGebruiker.verkrijgAchternaam()));
        Label rolLabel = new Label(String.format("Rol: %s", ingelogdeGebruiker.verkrijgRol().toString()));

        Date vandaag = Calendar.getInstance().getTime();
        Label tijdsLabel = new Label(String.format("Datum en tijd: %s", vandaag));


        //MENU
        MenuBar menu = new MenuBar();

        //Dashboard
        Menu dashboardMenu = new Menu("Dashboard");

        //Sales
        Menu verkopenMenu = new Menu("Verkopen");

        MenuItem verkopenBestellingMenuItem = new MenuItem("Bestelling");
        verkopenMenu.getItems().add(verkopenBestellingMenuItem);

        MenuItem verkopenBestellingenlijstMenuItem = new MenuItem("Bestellingenlijst");
        verkopenMenu.getItems().add(verkopenBestellingenlijstMenuItem);

        //Voorraad
        Menu voorraadMenu = new Menu("Voorraad");

        MenuItem voorraadBestellingMenuItem = new MenuItem("Bestellingenlijst");
        voorraadMenu.getItems().add(voorraadBestellingMenuItem);

        MenuItem voorraadOnderhoudMenuItem = new MenuItem("Onderhoud");
        voorraadMenu.getItems().add(voorraadOnderhoudMenuItem);

        welkomsberichtLabel.setFont(new Font(30));
        //menuAction(dashboardMenu);
        //menuAction(salesMenu);
        //menuAction(stockMenu);

        //dashboardMenu.setOnAction(actionEvent -> {new MainView(loggedInUser); window.close();});
        verkopenBestellingMenuItem.setOnAction(actionEvent -> {new BestellingMaken(ingelogdeGebruiker); window.close();});
        //stockMenu.setOnAction(actionEvent -> {new TeacherView(loggedInUser); window.close();});


        menu.getMenus().addAll(dashboardMenu, verkopenMenu, voorraadMenu);
        container.setTop(menu);
        container.setCenter(welkomsberichtLabel);
        content.getChildren().addAll(welkomsberichtLabel, rolLabel, tijdsLabel, grid);

        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.show();
    }

    public static void menuAction(Menu menu) {
        final MenuItem menuItem = new MenuItem();
        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }
}
