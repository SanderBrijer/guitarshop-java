package nl.inholland.GuitarShop_UI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Artikel;
import nl.inholland.GuitarShop_Models.BesteldeItem;
import nl.inholland.GuitarShop_Models.Bestelling;
import nl.inholland.GuitarShop_Models.TypeGitaar;

public class BestellingLijst {
    private final Stage window;


    public BestellingLijst()
    {
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




        Label titelTabel = new Label("Bestellingenlijst");

        TableView<Bestelling> bestellingTableView = new TableView<>();

        TableColumn<Bestelling, Integer> idKolom = new TableColumn<>("Order#");
        idKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgAantalBesteld()));

        TableColumn<Bestelling, String> datumKolom = new TableColumn<>("Datum");
        datumKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgArtikel().verkrijgMerk()));

        TableColumn<Bestelling, String> klantKolom = new TableColumn<>("Klant naam");
        klantKolom.setCellValueFactory(bestelling -> new SimpleStringProperty(bestelling.getValue().verkrijgKlant().verkrijgVolledigeNaam()));

        TableColumn<Bestelling, String> telefoonnummerKolom = new TableColumn<>("Telefoonnummer");
        telefoonnummerKolom.setCellValueFactory(bestelling -> new SimpleBooleanProperty(bestelling.getValue().verkrijgKlant().verkrijgTelefoonnummer()));

        TableColumn<Bestelling, String> emailKolom = new TableColumn<>("Email");
        emailKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgKlant().verkrijgEmail()));

        TableColumn<Bestelling, Integer> prijsKolom = new TableColumn<>("Aantal");
        prijsKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBesteldeItems().get().verkrijgAantalBesteld()));

        TableColumn<Bestelling, Double> totaalKolom = new TableColumn<>("Totaal");
        totaalKolom.setCellValueFactory(bestelling -> new SimpleObjectProperty(bestelling.getValue().verkrijgBesteldeItems().get().verkrijgArtikel().verkrijgPrijs()));

        //noinspection unchecked
        bestellingTableView.getColumns().addAll(idKolom, datumKolom, klantKolom, telefoonnummerKolom, emailKolom, prijsKolom, totaalKolom);








        content.getChildren().addAll();

        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);

        // Show window
        window.show();
    }

}