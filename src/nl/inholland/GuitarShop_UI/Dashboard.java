package nl.inholland.GuitarShop_UI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;
import nl.inholland.GuitarShop_Service.MenuBarMaker;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dashboard {
    Stage window;

    public Dashboard(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase) {
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
        welkomsberichtLabel.setFont(new Font(30));
        Label rolLabel = new Label(String.format("Rol: %s", ingelogdeGebruiker.verkrijgRol().toString()));

        Date vandaag = Calendar.getInstance().getTime();
        Label tijdsLabel = new Label(String.format("Datum en tijd: %s", vandaag));

        Button btnUitloggen = new Button("Uitloggen");

        btnUitloggen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
                new LoginScherm(gitaarDatabase);
            }
        });

        MenuBarMaker menuBarMaker = new MenuBarMaker(ingelogdeGebruiker, window, gitaarDatabase);

        MenuBar menu = menuBarMaker.verkrijgMenu();
        container.setTop(menu);
        container.setCenter(welkomsberichtLabel);
        content.getChildren().addAll(welkomsberichtLabel, rolLabel, tijdsLabel, grid, btnUitloggen);

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

    public void sluitAlleWindows()
    {
        //Platform.exit();
    }
}
