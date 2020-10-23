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
import nl.inholland.GuitarShop_Service.MenuBarMaker;

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


    public BestellingLijst(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase) {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        this.gitaarDatabase = gitaarDatabase;
        bestellingLijst = new ArrayList<AfgerondeBestelling>();
        window = new Stage();


        start();
    }

    //GETTER SETTER
    public List<AfgerondeBestelling> verkrijgBestellingLijst() {
        return bestellingLijst;
    }

    public void zetBestellingLijst(List<AfgerondeBestelling> bestellingLijst) {
        this.bestellingLijst = bestellingLijst;
    }

    public void voegToeAanBestellingLijst(AfgerondeBestelling afgerondeBestelling) {
        this.bestellingLijst.add(afgerondeBestelling);
    }

    private void vulTableViewBestellingen() {
        if (bestellingLijst != null) {
            if (bestellingLijst.size() != 0) {
                ObservableList<AfgerondeBestelling> bestellingLijstObserv = FXCollections.observableArrayList(bestellingLijst);
                bestellingTableView.setItems(bestellingLijstObserv);
            }
        }
    }

    private void vulBestellingenLijst() {
        bestellingLijst = gitaarDatabase.verkrijgAfgerondeBestellingen();
    }

    private void vulTableViewBesteldeItems() {
        ObservableList<BesteldeItem> besteldeItemsLijstObserv = FXCollections.observableArrayList(afgerondeBestellingAangeklikt.verkrijgBestelling().verkrijgBesteldeItems());
        besteldeItemTableView.setItems(besteldeItemsLijstObserv);
    }

    private Integer verkrijgAantalArtikelen(AfgerondeBestelling afgerondeBestelling) {
        List<BesteldeItem> besteldeItems = afgerondeBestelling.verkrijgBestelling().verkrijgBesteldeItems();
        Integer aantal = 0;
        for(int i=0;i<besteldeItems.size();i++){
            aantal += besteldeItems.get(i).verkrijgAantalBesteld();
        }
        return aantal;
    }

    private Double verkrijgTotalePrijsBesteldeItems(AfgerondeBestelling afgerondeBestelling) {
        List<BesteldeItem> besteldeItems = afgerondeBestelling.verkrijgBestelling().verkrijgBesteldeItems();
        Double totaalPrijs = 0.0;
        for(int i=0;i<besteldeItems.size();i++){
            Integer aantal = besteldeItems.get(i).verkrijgAantalBesteld();
            Double prijsPerArtikel = besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs();
            Double prijsTotaal = prijsPerArtikel*Double.valueOf(aantal);
            totaalPrijs += prijsTotaal;
        }
        return totaalPrijs;
    }


    //METHODEN

    private void start()
    {
        vulBestellingenLijst();


        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        content.setPadding(new Insets(10));


        //TABLE VIEW BESTELLINGEN
        Label titelTabel = new Label("Afgeronde bestellingenlijst");
        titelTabel.setFont(new Font(30));

        bestellingTableViewMaker();
        vulTableViewBestellingen();
        besteldeItemTableViewMaker();
        bestellingTableViewAction();



        //NEW aanmaken
        BorderPane borderPaneBestellingenDisplay = new BorderPane();
        GridPane gridPaneBestellingen = new GridPane();
        gridPaneBestellingen.setHgap(10);
        gridPaneBestellingen.setVgap(10);
        //toevoegen
        Label titelBestellingviewLabel = new Label("Afgeronde bestellingen");
        titelBestellingviewLabel.setFont(new Font(30));

        Label titelBestellinglijstLabel = new Label("Bestellingenlijst");
        titelBestellinglijstLabel.setFont(new Font(20));

        gridPaneBestellingen.add(titelBestellingviewLabel, 0, 0);
        gridPaneBestellingen.add(titelBestellinglijstLabel, 0, 2);
        gridPaneBestellingen.add(bestellingTableView, 0, 0);

        //toevoegen aan grid
        borderPaneBestellingenDisplay.setTop(gridPaneBestellingen);
        borderPaneBestellingenDisplay.setCenter(bestellingTableView);

        //NEW aanmaken
        BorderPane borderPaneBesteldeItemsDisplay = new BorderPane();
        GridPane gridPaneBesteldeItems = new GridPane();
        gridPaneBesteldeItems.setHgap(10);
        gridPaneBesteldeItems.setVgap(10);
        //toevoegen
        Label titelDetailsTabel = new Label("Details bestelling");
        titelDetailsTabel.setFont(new Font(20));

        gridPaneBesteldeItems.add(titelDetailsTabel, 0, 0);
        gridPaneBesteldeItems.add(besteldeItemTableView, 4, 0);

        //toevoegen aan grid
        borderPaneBesteldeItemsDisplay.setTop(gridPaneBesteldeItems);
        borderPaneBesteldeItemsDisplay.setCenter(besteldeItemTableView);

        content.getChildren().addAll(borderPaneBestellingenDisplay, borderPaneBesteldeItemsDisplay);
        content.getChildren().addAll();
        MenuBarMaker menuBarMaker = new MenuBarMaker(ingelogdeGebruiker, window, gitaarDatabase);
        container.setTop(menuBarMaker.verkrijgMenu());
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        windowMaker(scene);
    }

    private void windowMaker(Scene scene)
    {
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Bestellingenlijst");


        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.show();
    }

    private void bestellingTableViewMaker()
    {
        bestellingTableView = new TableView<>();

        TableColumn<AfgerondeBestelling, Integer> idKolom = new TableColumn<>("Order#");
        idKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue()));

        TableColumn<AfgerondeBestelling, String> datumKolom = new TableColumn<>("Datum");
        datumKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgDatum().toString()));

        TableColumn<AfgerondeBestelling, String> klantKolom = new TableColumn<>("Klant naam");
        klantKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgVolledigeNaam()));

        TableColumn<AfgerondeBestelling, String> telefoonnummerKolom = new TableColumn<>("Telefoonnummer");
        telefoonnummerKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgTelefoonnummer()));

        TableColumn<AfgerondeBestelling, String> emailKolom = new TableColumn<>("Email");
        emailKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBestelling().verkrijgKlant().verkrijgEmail()));

        TableColumn<AfgerondeBestelling, Integer> prijsKolom = new TableColumn<>("Aantal");
        prijsKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(verkrijgAantalArtikelen(bestelling.getValue())));

        TableColumn<AfgerondeBestelling, String> totaalKolom = new TableColumn<>("Totaal");
        totaalKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(String.format("%.2f", verkrijgTotalePrijsBesteldeItems(bestelling.getValue()))));

        bestellingTableView.getColumns().addAll(idKolom, datumKolom, klantKolom, telefoonnummerKolom, emailKolom, prijsKolom, totaalKolom);
    }

    private void besteldeItemTableViewMaker()
    {
        besteldeItemTableView = new TableView<>();


        TableColumn<BesteldeItem, String> merkKolom = new TableColumn<>("Merk");
        merkKolom.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgMerk()));

        TableColumn<BesteldeItem, String> modelKolom = new TableColumn<>("Model");
        modelKolom.setCellValueFactory(besteldeItem -> new SimpleStringProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgModel()));

        TableColumn<BesteldeItem, Boolean> akoestischKolom = new TableColumn<>("Akoestisch");
        akoestischKolom.setCellValueFactory(besteldeItem -> new SimpleBooleanProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgAkoestisch()));

        TableColumn<BesteldeItem, TypeGitaar> typeKolom = new TableColumn<>("Type");
        typeKolom.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgArtikel().verkrijgType()));

        TableColumn<BesteldeItem, Double> prijs2Kolom = new TableColumn<>("Prijs");
        prijs2Kolom.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(String.format("%.2f", Double.parseDouble(besteldeItem.getValue().verkrijgAantalBesteld().toString()) * besteldeItem.getValue().verkrijgArtikel().verkrijgPrijs())));

        TableColumn<BesteldeItem, Integer> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setCellValueFactory(besteldeItem -> new SimpleObjectProperty(besteldeItem.getValue().verkrijgAantalBesteld()));

        //noinspection unchecked
        besteldeItemTableView.getColumns().addAll(merkKolom, modelKolom, akoestischKolom, typeKolom, prijs2Kolom, aantalColumn);

    }

    private void bestellingTableViewAction()
    {
        bestellingTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    afgerondeBestellingAangeklikt = bestellingTableView.getSelectionModel().getSelectedItem();
                    vulTableViewBesteldeItems();
                } catch (Exception ex) {
                    String st = "Er zijn geen bestellingen gevonden.";
                    JOptionPane.showMessageDialog(null, st);
                }
            }
        });
    }
}
