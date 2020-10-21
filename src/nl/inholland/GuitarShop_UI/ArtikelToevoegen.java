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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;
import nl.inholland.GuitarShop_Service.Artikelen_Service;
import nl.inholland.GuitarShop_Service.TabelWaarden;

public class ArtikelToevoegen {
    private final Stage window;
    private ObservableList<Artikel> artikelen;
    TableView<Artikel> artikelenTableView;

    public BesteldeItem verkrijgBestelling() {
        return bestelling;
    }

    private BesteldeItem bestelling;

    Artikel artikelAangeklikt;
    GitaarDatabase database;
    Artikelen_Service artikelen_service;

    public Artikel verkrijgArtikelAangeklikt() {
        return artikelAangeklikt;
    }




    public ArtikelToevoegen (GitaarDatabase database) {
        this.database = database;
        artikelen_service = new Artikelen_Service();
        ObservableList<Artikel> artikelen = FXCollections.observableList(database.verkrijgArtikelen());
        window = new Stage();
        this.artikelen = artikelen;

        TabelWaarden dataTabel = new TabelWaarden();


        // Set Window properties
        window.setHeight(500);
        window.setWidth(600);
        window.setTitle("Voeg een artikel toe.");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        content.setPadding(new Insets(10));


        Label titelTabel = new Label("Artikelen");
        TableView<Artikel> artikelenTableView = new TableView<>();

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
        artikelenTableView.getColumns().addAll(merkKolom, modelKolom, akoestischKolom, typeKolom, prijsKolom);
        artikelenTableView.setItems(artikelen);


        /*artikelenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artikelAangeklikt = artikelenTableView.getSelectionModel().getSelectedItem();
            }
        });*/

        BorderPane borderPaneArtikelenTableView = new BorderPane();
        borderPaneArtikelenTableView.setTop(artikelenTableView);
        GridPane gridKnoppen = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        borderPaneArtikelenTableView.setBottom(gridKnoppen);


        TextField aantalArtikelenTextField = new TextField();
        Button toevoegenArtikelKnop = new Button("Toevoegen");
        Button annulerenToevoegenArtikelKnop = new Button("Annuleren");
        Label foutmeldingLabel = new Label();

        gridKnoppen.add(aantalArtikelenTextField, 0, 0);
        gridKnoppen.add(toevoegenArtikelKnop, 1, 0);
        gridKnoppen.add(annulerenToevoegenArtikelKnop, 2, 0);
        gridKnoppen.add(foutmeldingLabel, 0, 1);

        toevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                artikelAangeklikt = artikelenTableView.getSelectionModel().getSelectedItem();
                if (artikelAangeklikt != null)
                {
                    Integer aantalBesteld = 0;
                    try
                    {
                        aantalBesteld = Integer.parseInt(aantalArtikelenTextField.getText());
                        Integer aantalInVoorraad = database.verkrijgVoorraadVanArtikel(artikelAangeklikt);

                        if (aantalInVoorraad >= aantalBesteld)
                        {
                            if (aantalBesteld > 0)
                            {
                                database.verlaagVoorraadArtikel(artikelAangeklikt, aantalBesteld);
                                bestelling = new BesteldeItem(aantalBesteld, artikelAangeklikt);
                                window.close();
                            }
                            else
                            {
                                foutmeldingLabel.setText("Het opgegeven aantal is onder 0.");
                            }
                        }
                        else
                        {
                            foutmeldingLabel.setText("Er is niet genoeg in voorraad. Slechts "+ aantalInVoorraad + " in voorraad.");
                        }
                    }
                    catch (Exception e) {
                        foutmeldingLabel.setText("Er is geen getal ingevoerd.");
                    }
                }
            }
        });


        annulerenToevoegenArtikelKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        content.getChildren().addAll(borderPaneArtikelenTableView);


        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.showAndWait();

    }

/*    public void muisKlik()
    {
        klantenZoekTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                klantAangeklikt = klantenZoekTableView.getSelectionModel().getSelectedItem();

                window.close();
            }
        });
    }*/
}
