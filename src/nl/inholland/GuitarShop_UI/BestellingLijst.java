package nl.inholland.GuitarShop_UI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BestellingLijst {
    private GitaarDatabase gitaarDatabase;
    private final Stage window;
    private List<AfgerondeBestelling> bestellingLijst;
    private TableView<AfgerondeBestelling> bestellingTableView;
    private TableView<BesteldeItem> besteldeItemTableView;
    private Gebruiker ingelogdeGebruiker;
    private AfgerondeBestelling afgerondeBestellingAangeklikt;


    public BestellingLijst(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase, MenuBar menu)
    {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        this.gitaarDatabase = gitaarDatabase;


        bestellingLijst = new ArrayList<AfgerondeBestelling>();
        vulBestellingenLijst();


        window = new Stage();

        // Set Window properties
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Bestellingenlijst");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        content.setPadding(new Insets(10));



        //TABLE VIEW BESTELLINGEN
        Label titelTabel = new Label("Afgeronde bestellingenlijst");

        bestellingTableView = new TableView<>();

        TableColumn<AfgerondeBestelling, Integer> idKolom = new TableColumn<>("Order#");
        idKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue()));

        TableColumn<AfgerondeBestelling, String> datumKolom = new TableColumn<>("Datum");
        datumKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgDatum().toString()));

        TableColumn<AfgerondeBestelling, String> klantKolom = new TableColumn<>("Klant naam");
        klantKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgVolledigeNaam()));

        TableColumn<AfgerondeBestelling, String> telefoonnummerKolom = new TableColumn<>("Telefoonnummer");
        telefoonnummerKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgStad()));

        TableColumn<AfgerondeBestelling, String> emailKolom = new TableColumn<>("Email");
        emailKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgEmail()));

        TableColumn<AfgerondeBestelling, Integer> prijsKolom = new TableColumn<>("Aantal");
        prijsKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBestelling().verkrijgBesteldeItems().get(1).verkrijgAantalBesteld()));

        TableColumn<AfgerondeBestelling, Double> totaalKolom = new TableColumn<>("Totaal");
        totaalKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBestelling().verkrijgBesteldeItems().get(1).verkrijgArtikel().verkrijgPrijs()));

        bestellingTableView.getColumns().addAll(idKolom, datumKolom, klantKolom, telefoonnummerKolom, emailKolom, prijsKolom, totaalKolom);

        if (bestellingLijst != null)
        {
            if (bestellingLijst.size() != 0)
            {
                vulTableViewBestellingen();
            }
        }



        //BESTELLING INFO TABLE VIEW
        Label titelDetailsTabel = new Label("Details bestelling");

        besteldeItemTableView = new TableView<>();


        TableColumn<BesteldeItem, String> merkColumn = new TableColumn<>("Merk");
        merkColumn.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgMerk()));

        TableColumn<BesteldeItem, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgModel()));

        TableColumn<BesteldeItem, Boolean> akoestischColumn = new TableColumn<>("Akoestisch");
        akoestischColumn.setCellValueFactory(besteldeItem -> new SimpleBooleanProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgAkoestisch()));

        TableColumn<BesteldeItem, TypeGitaar> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgType()));

        TableColumn<BesteldeItem, Double> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(Double.parseDouble(besteldeItem.getValue().verkrijgAantalBesteld().toString()) * besteldeItem.getValue().verkrijgArtikel().verkrijgPrijs()));

        TableColumn<BesteldeItem, Integer> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgAantalBesteld()));

        //noinspection unchecked
        besteldeItemTableView.getColumns().addAll(merkColumn, modelColumn, akoestischColumn, typeColumn, prijsColumn, aantalColumn);

        bestellingTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    afgerondeBestellingAangeklikt = bestellingTableView.getSelectionModel().getSelectedItem();
                    vulTableViewBesteldeItems();
                }
                catch(Exception ex)
                {
                    String st = "Er zijn geen bestellingen gevonden.";
                    JOptionPane.showMessageDialog(null, st);
                }
            }
        });




        //NEW aanmaken
        BorderPane borderPaneBestellingenDisplay = new BorderPane();
        GridPane gridPaneBestellingen = new GridPane();
        gridPaneBestellingen.setHgap(10);
        gridPaneBestellingen.setVgap(10);
        //toevoegen
        gridPaneBestellingen.add(new Label("Bestellingenlijst"), 0,0);
        gridPaneBestellingen.add(bestellingTableView,4 ,0);

        //toevoegen aan grid
        borderPaneBestellingenDisplay.setTop(gridPaneBestellingen);
        borderPaneBestellingenDisplay.setCenter(bestellingTableView);

        //NEW aanmaken
        BorderPane borderPaneBesteldeItemsDisplay = new BorderPane();
        GridPane gridPaneBesteldeItems = new GridPane();
        gridPaneBesteldeItems.setHgap(10);
        gridPaneBesteldeItems.setVgap(10);
        //toevoegen
        gridPaneBesteldeItems.add(new Label("Bestelling details"), 0,0);
        gridPaneBesteldeItems.add(besteldeItemTableView,4 ,0);

        //toevoegen aan grid
        borderPaneBesteldeItemsDisplay.setTop(gridPaneBesteldeItems);
        borderPaneBesteldeItemsDisplay.setCenter(besteldeItemTableView);

        content.getChildren().addAll(borderPaneBestellingenDisplay, borderPaneBesteldeItemsDisplay);
        content.getChildren().addAll();
        container.setTop(menu);
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.show();
    }


    public List<AfgerondeBestelling> verkrijgBestellingLijst() {
        return bestellingLijst;
    }

    public void zetBestellingLijst(List<AfgerondeBestelling> bestellingLijst) {
        this.bestellingLijst = bestellingLijst;
    }

    public void voegToeAanBestellingLijst(AfgerondeBestelling afgerondeBestelling) {
        this.bestellingLijst.add(afgerondeBestelling);
    }

    private void vulTableViewBestellingen()
    {
        ObservableList<AfgerondeBestelling> bestellingLijstObserv = FXCollections.observableArrayList(bestellingLijst);
        bestellingTableView.setItems(bestellingLijstObserv);
    }

    private void vulBestellingenLijst()
    {
        bestellingLijst = gitaarDatabase.verkrijgAfgerondeBestellingen();
    }

    private void vulTableViewBesteldeItems()
    {
        ObservableList<BesteldeItem> besteldeItemsLijstObserv = FXCollections.observableArrayList(afgerondeBestellingAangeklikt.verkrijgBestelling().verkrijgBesteldeItems());
        besteldeItemTableView.setItems(besteldeItemsLijstObserv);
    }


}
