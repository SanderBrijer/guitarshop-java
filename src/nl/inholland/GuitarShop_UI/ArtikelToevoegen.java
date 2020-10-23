package nl.inholland.GuitarShop_UI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import nl.inholland.GuitarShop_Service.Artikelen_Service;

import javax.swing.*;

public class ArtikelToevoegen {
    private final Stage window;
    private ObservableList<Artikel> artikelen;
    TableView<Artikel> artikelenTableView;
    private BesteldeItem bestelling;
    Artikel artikelAangeklikt;
    GitaarDatabase database;
    Artikelen_Service artikelen_service;


    public ArtikelToevoegen(GitaarDatabase database) {
        this.database = database;
        window = new Stage();
        this.artikelen = FXCollections.observableList(database.verkrijgArtikelen());
        start();

    }

    private void start()
    {
        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);
        content.setPadding(new Insets(10));


        Label titelTabel = new Label("Artikelen");
        titelTabel.setFont(new Font(30));


        //TABLE VIEW
        artikelenTableView = artikelTableViewMaker();
        artikelenTableView.setItems(artikelen);

        //KNOPPEN AANMAKEN
        BorderPane borderPaneArtikelenTableView = new BorderPane();
        borderPaneArtikelenTableView.setTop(artikelenTableView);
        GridPane gridKnoppen = new GridPane();
        gridKnoppen.setHgap(10);
        gridKnoppen.setVgap(10);

        TextField aantalArtikelenTextField = new TextField();
        aantalArtikelenTextField.setPromptText("Aantal");
        Button toevoegenArtikelKnop = new Button("Toevoegen");
        Button annulerenToevoegenArtikelKnop = new Button("Annuleren");
        Label foutmeldingLabel = new Label();

        gridKnoppen.add(aantalArtikelenTextField, 0, 0);
        gridKnoppen.add(toevoegenArtikelKnop, 1, 0);
        gridKnoppen.add(annulerenToevoegenArtikelKnop, 2, 0);
        gridKnoppen.add(foutmeldingLabel, 0, 1);

        borderPaneArtikelenTableView.setBottom(gridKnoppen);

        //KNOPPEN ACTIES
        anullerenKnopAction(annulerenToevoegenArtikelKnop);
        toevoegenArtikelKnopAction(toevoegenArtikelKnop, aantalArtikelenTextField, foutmeldingLabel);
        artikelenTableViewAction();
        content.getChildren().addAll(borderPaneArtikelenTableView);


        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        windowMaker(scene);
    }

    private void windowMaker(Scene scene)
    {
        window.setHeight(500);
        window.setWidth(600);
        window.setTitle("Voeg een artikel toe.");

        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.showAndWait();
    }

    private void artikelenTableViewAction()
    {
        artikelenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    artikelAangeklikt = artikelenTableView.getSelectionModel().getSelectedItem();
                } catch (Exception ex) {
                    String bericht = "Er zijn geen artikelen beschikbaar.";
                    JOptionPane.showMessageDialog(null, bericht);
                }
            }
        });
    }

    private void toevoegenArtikelKnopAction(Button toevoegenArtikelKnop, TextField aantalArtikelenTextField, Label foutmeldingLabel) {
        toevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (artikelAangeklikt != null) {
                    Integer aantalBesteld = 0;
                    try {
                        aantalBesteld = Integer.parseInt(aantalArtikelenTextField.getText());
                        Integer aantalInVoorraad = database.verkrijgVoorraadVanArtikel(artikelAangeklikt);

                        if (aantalInVoorraad >= aantalBesteld) {
                            if (aantalBesteld > 0) {
                                database.verlaagVoorraadArtikel(artikelAangeklikt, aantalBesteld);
                                bestelling = new BesteldeItem(aantalBesteld, artikelAangeklikt);
                                window.close();
                            } else {
                                foutmeldingLabel.setText("Het opgegeven aantal is onder 0.");
                            }
                        } else {
                            foutmeldingLabel.setText("Er is niet genoeg in voorraad. Slechts " + aantalInVoorraad + " in voorraad.");
                        }
                    } catch (Exception e) {
                        foutmeldingLabel.setText("Er is geen getal ingevoerd.");
                    }
                } else {
                    String bericht = "Selecteer een artikel.";
                    JOptionPane.showMessageDialog(null, bericht);
                }
            }
        });
    }

    private void anullerenKnopAction(Button annulerenToevoegenArtikelKnop) {

        annulerenToevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });
    }

    private TableView<Artikel> artikelTableViewMaker() {
        TableView<Artikel> tableView = new TableView<>();
        TableColumn<Artikel, String> merkKolom = new TableColumn<>("Merk");
        merkKolom.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgMerk()));

        TableColumn<Artikel, String> modelKolom = new TableColumn<>("Model");
        modelKolom.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgModel()));

        TableColumn<Artikel, Boolean> akoestischKolom = new TableColumn<>("Akoestisch");
        akoestischKolom.setCellValueFactory(artikel -> new SimpleBooleanProperty(artikel.getValue().verkrijgAkoestisch()));

        TableColumn<Artikel, TypeGitaar> typeKolom = new TableColumn<>("Type");
        typeKolom.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgType()));

        TableColumn<Artikel, Double> prijsKolom = new TableColumn<>("Prijs");
        prijsKolom.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgPrijs()));

        //noinspection unchecked
        tableView.getColumns().addAll(merkKolom, modelKolom, akoestischKolom, typeKolom, prijsKolom);

        return tableView;
    }



    //GETTERS EN SETTERS

    public BesteldeItem verkrijgBestelling() {
        return bestelling;
    }

    public Artikel verkrijgArtikelAangeklikt() {
        return artikelAangeklikt;
    }
}
