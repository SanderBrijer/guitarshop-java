package nl.inholland.GuitarShop_UI;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;

import javafx.scene.control.TextField;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BestellingMaken {
    private final Stage window;
    GitaarDatabase database;

    TableView<BesteldeItem> artikelenTableView;

    KlantenZoekScherm klantenzoek;
    ArtikelToevoegen artikelToevoegen;
    List<Artikel> artikelenLijst;
    List<BesteldeItem> bestellingenLijst;
    Bestelling bestelling;

    //labels
    Label klantVoornaamLabel;
    Label klantAchternaamLabel ;
    Label klantAdresLabel;
    Label klantStadLabel;
    Label klantTelefoonnummerLabel;
    Label klantEmailadresLabel ;

    Label klantVoornaamUitvoerLabel;
    Label klantAchternaamUitvoerLabel;
    Label klantAdresUitvoerLabel;
    Label klantStadUitvoerLabel;
    Label klantTelefoonnummerUitvoerLabel;
    Label klantEmailadresUitvoerLabel;

    public BestellingMaken(Gebruiker ingelogdeGebruiker) {
        database = new GitaarDatabase();
        artikelenTableView = new TableView<>();
        artikelenLijst = new ArrayList<Artikel>();
        bestelling = new Bestelling();
        bestellingenLijst = new ArrayList<BesteldeItem>();
        //labels
        klantVoornaamLabel = new Label("Voornaam: ");
        klantAchternaamLabel = new Label("Achternaam: ");
        klantAdresLabel = new Label("Adres: ");
        klantStadLabel = new Label("Stad: ");
        klantTelefoonnummerLabel = new Label("Telefoonnummer: ");
        klantEmailadresLabel = new Label("Emailadres: ");

        klantVoornaamUitvoerLabel = new Label();
        klantAchternaamUitvoerLabel = new Label();
        klantAdresUitvoerLabel = new Label();
        klantStadUitvoerLabel = new Label();
        klantTelefoonnummerUitvoerLabel = new Label();
        klantEmailadresUitvoerLabel = new Label();



        window = new Stage();
        database = new GitaarDatabase();
        ObservableList<Artikel> artikelen = FXCollections.observableList(database.verkrijgArtikelen());

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Bestelling aanmaken");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        content.setPadding(new Insets(10));

        // Create components
        Label title = new Label("Nieuwe bestelling aanmaken: ");
        Label klantLabel = new Label("Klant");
        TextField klantNaamInvoer = new TextField();
        Button klantZoekenKnop = new Button("Zoeken");

        klantZoekenKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String naam = klantNaamInvoer.getText();
                List<Klant> klanten = verkrijgKlantViaNaam(naam);
                ObservableList<Klant> listKlanten = FXCollections.observableArrayList(klanten);
                klantenzoek = new KlantenZoekScherm(listKlanten);
                vulKlantInfo(klantenzoek.getKlantAangeklikt());
                klantNaamInvoer.clear();
            }
        });



        Label titelTabel = new Label("Artikelen");

        TableColumn<BesteldeItem, Integer> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgAantalBesteld()));

        TableColumn<BesteldeItem, String> merkColumn = new TableColumn<>("Merk");
        merkColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgArtikel().verkrijgMerk()));

        TableColumn<BesteldeItem, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgArtikel().verkrijgModel()));

        TableColumn<BesteldeItem, Boolean> akoestischColumn = new TableColumn<>("Akoestisch");
        akoestischColumn.setCellValueFactory(artikel -> new SimpleBooleanProperty(artikel.getValue().verkrijgArtikel().verkrijgAkoestisch()));

        TableColumn<BesteldeItem, TypeGitaar> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgArtikel().verkrijgType()));

        TableColumn<BesteldeItem, Double> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgArtikel().verkrijgPrijs()));

        //noinspection unchecked
        artikelenTableView.getColumns().addAll(aantalColumn, merkColumn, modelColumn, akoestischColumn, typeColumn, prijsColumn);




        Button verwijderenArtikelKnop = new Button("Verwijderen artikel");
        Button toevoegenArtikelKnop = new Button("Toevoegen artikel");
        Button bevestigArtikelenKnop = new Button("Bevestig");
        Button resetArtikelenKnop = new Button("Reset");


        toevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                artikelToevoegen = new ArtikelToevoegen();
                bestelling.toevoegenAanBesteldeItems(artikelToevoegen.verkrijgBestelling());
                vulTableViewBestellingen();
            }
        });

        verwijderenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BesteldeItem bestelling = artikelenTableView.getSelectionModel().getSelectedItem();
                Artikel artikelAangeklikt = bestelling.verkrijgArtikel();
                verwijderArtikelUitLijst(artikelAangeklikt);
                database.verhoogVoorraadArtikel(artikelAangeklikt, bestelling.verkrijgAantalBesteld());
                new BestellingMaken(ingelogdeGebruiker);
                window.close();
            }
        });

        bevestigArtikelenKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        // klant opzoeken
        BorderPane borderPaneKlantOpzoeken = new BorderPane();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(title, 0, 0);
        grid.add(klantLabel, 0, 1);
        grid.add(klantNaamInvoer, 0, 2);
        grid.add(klantZoekenKnop, 1, 2);

        borderPaneKlantOpzoeken.setTop(grid);

        // Klant displayen
        BorderPane borderPaneKlantDisplay = new BorderPane();
        GridPane gridKlantDisplay = new GridPane();

        gridKlantDisplay.setHgap(10);
        gridKlantDisplay.setVgap(10);

        gridKlantDisplay.setBackground(new Background(new BackgroundFill(Color.rgb(0, 255, 153), null, new Insets(-10))));
        //gridKlantDisplay.setBackground(Color.lightGray);

        gridKlantDisplay.add(klantVoornaamLabel, 0, 0);
        gridKlantDisplay.add(klantVoornaamUitvoerLabel, 1, 0);

        gridKlantDisplay.add(klantAchternaamLabel, 0, 1);
        gridKlantDisplay.add(klantAchternaamUitvoerLabel, 1, 1);

        gridKlantDisplay.add(klantAdresLabel, 0, 2);
        gridKlantDisplay.add(klantAdresUitvoerLabel, 1, 2);


        gridKlantDisplay.add(klantStadLabel, 5, 0);
        gridKlantDisplay.add(klantStadUitvoerLabel, 6, 0);

        gridKlantDisplay.add(klantTelefoonnummerLabel, 5, 1);
        gridKlantDisplay.add(klantTelefoonnummerUitvoerLabel, 6, 1);

        gridKlantDisplay.add(klantEmailadresLabel, 5, 2);
        gridKlantDisplay.add(klantEmailadresUitvoerLabel, 6, 2);

        borderPaneKlantOpzoeken.setTop(grid);
        borderPaneKlantOpzoeken.setRight(gridKlantDisplay);

        //Displayen van de tableview
        //Table view
        BorderPane borderPaneArtikelenTableView = new BorderPane();


        GridPane gridPaneArtikelenTableView = new GridPane();
        GridPane gridPaneArtikelenKnoppen = new GridPane();

        gridPaneArtikelenKnoppen.setPadding(new Insets(10));
        gridPaneArtikelenKnoppen.setHgap(5);
        gridPaneArtikelenKnoppen.setVgap(5);

        gridPaneArtikelenTableView.add(artikelenTableView, 0, 0);
        gridPaneArtikelenKnoppen.add(toevoegenArtikelKnop, 0, 0);
        gridPaneArtikelenKnoppen.add(verwijderenArtikelKnop, 2, 0);
        gridPaneArtikelenKnoppen.add(bevestigArtikelenKnop, 4, 0);
        gridPaneArtikelenKnoppen.add(resetArtikelenKnop, 6, 0);

        borderPaneArtikelenTableView.setTop(gridPaneArtikelenTableView);
        borderPaneArtikelenTableView.setBottom(gridPaneArtikelenKnoppen);


        content.getChildren().addAll(borderPaneKlantOpzoeken, borderPaneArtikelenTableView);


        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);

        // Show window
        window.show();
    }

    public List<Klant> verkrijgKlantViaNaam(String naam) {
        var klanten = database.verkrijgKlanten();
        return klanten.stream().filter(klant -> klant.verkrijgVoornaam().toLowerCase().contains(naam.toLowerCase())).collect(Collectors.toList());
    }

    public void vulKlantInfo(Klant klant)
    {
        if (klant != null)
        {
            klantVoornaamUitvoerLabel.setText(klant.verkrijgVoornaam());
            klantAchternaamUitvoerLabel.setText(klant.verkrijgAchternaam());
            klantAdresUitvoerLabel.setText(klant.verkrijgAdres());
            klantStadUitvoerLabel.setText(klant.verkrijgStad());
            klantTelefoonnummerUitvoerLabel.setText(klant.verkrijgTelefoonnummer());
            klantEmailadresUitvoerLabel.setText(klant.verkrijgEmail());
        }
    }

    public void vulTableViewBestellingen()
    {

        try {
            ObservableList<BesteldeItem> mapMetBestellingen = FXCollections.observableList(bestellingenLijst);

            if (mapMetBestellingen != null)
            {
                artikelenTableView.setItems(mapMetBestellingen);
            }
        }
        catch(Exception e) {

        }
    }

    public void verwijderArtikelUitLijst(Artikel artikel)
    {
        artikelenLijst.remove(artikel);
    }

}
