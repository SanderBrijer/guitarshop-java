package nl.inholland.GuitarShop_UI;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import nl.inholland.GuitarShop_Models.*;

import javax.swing.*;

public class KlantenZoekScherm {
    private final Stage window;
    private ObservableList<Klant> klanten;

    public Klant getKlantAangeklikt() {
        return klantAangeklikt;
    }

    private Klant klantAangeklikt;
    TableView<Klant> klantenZoekTableView;

    public KlantenZoekScherm(ObservableList<Klant> klanten) {
        window = new Stage();
        this.klanten = klanten;

        start();

    }

    private void start()
    {

        Label titelTabel = new Label("Selecteer een klant");
        titelTabel.setFont(new Font(30));

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        content.setPadding(new Insets(10));


        Label titelTableView = new Label("Klanten");
        titelTableView.setFont(new Font(15));


        //KLANTENTABLEVIEW
        klantenZoekTableViewMaker();
        klantenZoekTableViewAction();



        content.getChildren().addAll(klantenZoekTableView, grid);


        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        windowMaker(scene);
    }


    private void windowMaker(Scene scene)
    {
        //WINDOW titel
        window.setTitle("Klanten opzoeken");

        //WINDOW info
        window.setHeight(400);
        window.setWidth(600);

        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);

        window.showAndWait();
    }

    private void klantenZoekTableViewAction()
    {
        klantenZoekTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    klantAangeklikt = klantenZoekTableView.getSelectionModel().getSelectedItem();
                    window.close();
                }
                catch(Exception exception)
                {
                    String bericht = "Er zijn geen klanten beschikbaar.";
                    JOptionPane.showMessageDialog(null, bericht);
                }
            }
        });
    }

    private void klantenZoekTableViewMaker()
    {
        klantenZoekTableView = new TableView<>();

        TableColumn<Klant, String> voornaamColumn = new TableColumn<>("Voornaam");
        voornaamColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgVoornaam()));

        TableColumn<Klant, String> achternaamColumn = new TableColumn<>("Achternaam");
        achternaamColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgAchternaam()));

        TableColumn<Klant, String> adresColumn = new TableColumn<>("Adres");
        adresColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgAdres()));

        TableColumn<Klant, String> stadColumn = new TableColumn<>("Stad");
        stadColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgStad()));

        TableColumn<Klant, Integer> telefoonnummerColumn = new TableColumn<>("Telefoonnummer");
        telefoonnummerColumn.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgTelefoonnummer()));

        TableColumn<Klant, String> emailadresColumn = new TableColumn<>("Emailadres");
        emailadresColumn.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgEmail()));

        klantenZoekTableView.getColumns().addAll(voornaamColumn, achternaamColumn, adresColumn, stadColumn, telefoonnummerColumn, emailadresColumn);
        klantenZoekTableView.setItems(klanten);

    }
}
