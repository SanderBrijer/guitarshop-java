package nl.inholland.GuitarShop_UI;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;

import javafx.scene.paint.Color;
import nl.inholland.GuitarShop_Service.MenuBarMaker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BestellingMaken {
    private final Stage window;
    private Gebruiker ingelogdeGebruiker;
    GitaarDatabase database;

    TableView<BesteldeItem> artikelenTableView;

    Klant klant;
    KlantenZoekScherm klantenzoek;
    ArtikelToevoegen artikelToevoegen;
    List<Artikel> artikelenLijst;
    List<BesteldeItem> bestellingenLijst;
    Bestelling bestelling;

    //labels
    Label klantVoornaamLabel;
    Label klantAchternaamLabel;
    Label klantAdresLabel;
    Label klantStadLabel;
    Label klantTelefoonnummerLabel;
    Label klantEmailadresLabel;

    Label klantVoornaamUitvoerLabel;
    Label klantAchternaamUitvoerLabel;
    Label klantAdresUitvoerLabel;
    Label klantStadUitvoerLabel;
    Label klantTelefoonnummerUitvoerLabel;
    Label klantEmailadresUitvoerLabel;

    public BestellingMaken(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase) {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        this.database = gitaarDatabase;
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
        start();
    }

    private void start()
    {

        ObservableList<Artikel> artikelen = FXCollections.observableList(database.verkrijgArtikelen());


        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        content.setPadding(new Insets(10));

        // Create components
        Label title = new Label("Nieuwe bestelling aanmaken");
        title.setFont(new Font(30));
        Label klantLabel = new Label("Klant");
        klantLabel.setFont(new Font(20));
        TextField klantNaamInvoer = new TextField();
        klantNaamInvoer.setPromptText("Naam klant");
        Button klantZoekenKnop = new Button("Zoeken");

        klantZoekenKnopAction(klantZoekenKnop, klantNaamInvoer);


        Label titelTabel = new Label("Artikelen");
        artikelenTableViewMaker();


        Button verwijderenArtikelKnop = new Button("Verwijderen artikel");
        Button toevoegenArtikelKnop = new Button("Toevoegen artikel");
        Button bevestigArtikelenKnop = new Button("Bevestig");
        Button resetArtikelenKnop = new Button("Reset");

        toevoegenArtikelKnopAction(toevoegenArtikelKnop);
        verwijderenArtikelKnopAction(verwijderenArtikelKnop);
        resetArtikelenKnopAction(resetArtikelenKnop);
        bevestigArtikelenKnopAction(bevestigArtikelenKnop);

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
        GridPane gridKlantDisplay = gridKlantDisplayMaker();
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


        Label lblArtikelen = new Label("Artikelen");
        lblArtikelen.setFont(new Font(20));
        gridPaneArtikelenTableView.add(lblArtikelen, 0, 0);
        gridPaneArtikelenTableView.add(artikelenTableView, 0, 3);
        gridPaneArtikelenKnoppen.add(toevoegenArtikelKnop, 0, 3);
        gridPaneArtikelenKnoppen.add(verwijderenArtikelKnop, 2, 3);
        gridPaneArtikelenKnoppen.add(bevestigArtikelenKnop, 4, 3);
        gridPaneArtikelenKnoppen.add(resetArtikelenKnop, 6, 3);

        borderPaneArtikelenTableView.setTop(gridPaneArtikelenTableView);
        borderPaneArtikelenTableView.setBottom(gridPaneArtikelenKnoppen);


        content.getChildren().addAll(borderPaneKlantOpzoeken, borderPaneArtikelenTableView);


        MenuBarMaker menuBarMaker = new MenuBarMaker(ingelogdeGebruiker, window, database);


        container.setTop(menuBarMaker.verkrijgMenu());
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        windowMaker(scene);

        menuBarAction(menuBarMaker);
    }

    private void menuBarAction(MenuBarMaker menuBarMaker)
    {
        menuBarMaker.verkrijgDashboardMenuItem().setOnAction(actionEvent -> {
            bestellingAnnuleren();
        });


        menuBarMaker.verkrijgVerkopenBestellingMenuItem().setOnAction(actionEvent -> {
            bestellingAnnuleren();
        });

        menuBarMaker.verkrijgVerkopenBestellingenlijstMenuItem().setOnAction(actionEvent -> {

            bestellingAnnuleren();
            window.close();
            new BestellingLijst(ingelogdeGebruiker, database);
        });

        menuBarMaker.verkrijgVoorraadBestellingMenuItem().setOnAction(actionEvent -> {
            bestellingAnnuleren();
        });

        menuBarMaker.verkrijgVoorraadBeheerMenuItem().setOnAction(actionEvent -> {
            bestellingAnnuleren();
        });
    }


    private GridPane gridKlantDisplayMaker()
    {
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
        return gridKlantDisplay;
    }


    private void klantZoekenKnopAction(Button klantZoekenKnop, TextField klantNaamInvoer) {
        klantZoekenKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String naam = klantNaamInvoer.getText();
                List<Klant> klanten = verkrijgKlantViaNaam(naam);
                ObservableList<Klant> listKlanten = FXCollections.observableArrayList(klanten);
                klantenzoek = new KlantenZoekScherm(listKlanten);
                klant = klantenzoek.getKlantAangeklikt();
                vulKlantInfo(klant);
                klantNaamInvoer.clear();
            }
        });

    }

    //KNOPPEN
    private void toevoegenArtikelKnopAction(Button toevoegenArtikelKnop)
    {
        toevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                artikelToevoegen = new ArtikelToevoegen(database);
                if (artikelToevoegen.verkrijgBestelling() != null) {
                    BesteldeItem besteldeItem = artikelToevoegen.verkrijgBestelling();
                    bestelling.toevoegenAanBesteldeItems(besteldeItem);
                }
                vulTableViewBestellingen();
            }
        });
    }
    private void verwijderenArtikelKnopAction(Button verwijderenArtikelKnop) {
        verwijderenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    BesteldeItem besteldeItem = artikelenTableView.getSelectionModel().getSelectedItem();
                    if (besteldeItem != null) {
                        verwijderArtikelUitLijst(besteldeItem);
                        vulTableViewBestellingen();
                    } else {
                        String bericht = "Selecteer eerst een artikel.";
                        JOptionPane.showMessageDialog(null, bericht);
                    }
                } catch (Exception exception) {
                    String bericht = "Verwijderen van het artikel is niet gelukt.";
                    JOptionPane.showMessageDialog(null, bericht);
                }
            }
        });
    }
    private void resetArtikelenKnopAction(Button resetArtikelenKnop) {
        resetArtikelenKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                while (artikelenTableView.getItems().size() != 0) {
                    BesteldeItem besteldeItem = artikelenTableView.getItems().get(0);
                    verwijderArtikelUitLijst(besteldeItem);
                    vulTableViewBestellingen();
                }
            }
        });
    }
    private void bevestigArtikelenKnopAction(Button bevestigArtikelenKnop) {
        bevestigArtikelenKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (klant != null) {
                    if (bestelling.verkrijgBesteldeItems().size() <= 0) {
                        String bericht = "Er zijn geen artikelen toegevoegd.";
                        JOptionPane.showMessageDialog(null, bericht);
                    } else {
                        bestelling.zetKlant(klant);
                        BevestigBestelling bevestigBestelling = new BevestigBestelling(bestelling, database);
                        window.close();
                        new Dashboard(ingelogdeGebruiker, database);

                    }
                } else {
                    String st = "Er is geen klant ingevoerd";
                    JOptionPane.showMessageDialog(null, st);
                }
            }
        });
    }


    private void artikelenTableViewMaker() {

        TableColumn<BesteldeItem, Integer> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgAantalBesteld()));

        TableColumn<BesteldeItem, String> merkColumn = new TableColumn<>("Merk");
        merkColumn.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgMerk()));

        TableColumn<BesteldeItem, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgModel()));

        TableColumn<BesteldeItem, Boolean> akoestischColumn = new TableColumn<>("Akoestisch");
        akoestischColumn.setCellValueFactory(besteldeItem -> new SimpleBooleanProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgAkoestisch()));

        TableColumn<BesteldeItem, TypeGitaar> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgType()));

        TableColumn<BesteldeItem, Double> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(String.format("%.2f", Double.parseDouble(besteldeItem.getValue().verkrijgAantalBesteld().toString()) * besteldeItem.getValue().verkrijgArtikel().verkrijgPrijs())));

        //noinspection unchecked
        artikelenTableView.getColumns().addAll(aantalColumn, merkColumn, modelColumn, akoestischColumn, typeColumn, prijsColumn);
    }

    public List<Klant> verkrijgKlantViaNaam(String naam) {
        var klanten = database.verkrijgKlanten();
        return klanten.stream().filter(klant -> klant.verkrijgVoornaam().toLowerCase().contains(naam.toLowerCase())).collect(Collectors.toList());
    }

    private void windowMaker(Scene scene) {
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Bestelling aanmaken");

        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();

        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent event) {
                bestellingAnnuleren();
                window.close();
            }
        });
    }

    private void vulKlantInfo(Klant klant) {
        if (klant != null) {
            klantVoornaamUitvoerLabel.setText(klant.verkrijgVoornaam());
            klantAchternaamUitvoerLabel.setText(klant.verkrijgAchternaam());
            klantAdresUitvoerLabel.setText(klant.verkrijgAdres());
            klantStadUitvoerLabel.setText(klant.verkrijgStad());
            klantTelefoonnummerUitvoerLabel.setText(klant.verkrijgTelefoonnummer());
            klantEmailadresUitvoerLabel.setText(klant.verkrijgEmail());
        }
    }

    private void vulTableViewBestellingen() {
        try {
            ObservableList<BesteldeItem> mapMetBestellingen = FXCollections.observableList(bestelling.verkrijgBesteldeItems());

            if (mapMetBestellingen != null) {
                artikelenTableView.setItems(mapMetBestellingen);
            }
        } catch (Exception e) {

        }
    }

    private void verwijderArtikelUitLijst(BesteldeItem besteldeItem) {
        bestelling.verwijderVanBesteldeItems(besteldeItem);
        database.verhoogVoorraadArtikel(besteldeItem.verkrijgArtikel(), besteldeItem.verkrijgAantalBesteld());
    }

    private void bestellingAnnuleren() {
        while (bestellingenLijst.size() != 0) {
            BesteldeItem besteldeItem = bestellingenLijst.get(0);
            verwijderArtikelUitLijst(besteldeItem);
        }
    }

}
