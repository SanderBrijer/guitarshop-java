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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.AfgerondeBestelling;
import nl.inholland.GuitarShop_Models.Artikel;
import nl.inholland.GuitarShop_Models.Gebruiker;
import nl.inholland.GuitarShop_Models.TypeGitaar;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Voorraadonderhoud {
    private List<Artikel> artikelen;
    private Artikel artikelAangeklikt;
    private final Stage window;
    private TableView<Artikel> artikelenTableView;

    public Voorraadonderhoud(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase, MenuBar menu) {
        artikelenTableView = new TableView<>();
        artikelen = new ArrayList<>();
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


        Label titelTabel = new Label("Artikelen");


        TableColumn<Artikel, Integer> aantalKolom = new TableColumn<>("Aantal");
        aantalKolom.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgAantal()));

        TableColumn<Artikel, String> merkKolom = new TableColumn<>("Merk");
        merkKolom.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgMerk()));

        TableColumn<Artikel, String> modelKolom = new TableColumn<>("Model");
        modelKolom.setCellValueFactory(artikel -> new SimpleStringProperty(artikel.getValue().verkrijgModel()));

        TableColumn<Artikel, Boolean> akoestischKolom = new TableColumn<>("Akoestisch");
        akoestischKolom.setCellValueFactory(artikel -> new SimpleBooleanProperty(artikel.getValue().verkrijgAkoestisch()));

        TableColumn<Artikel, Object> typeKolom = new TableColumn<>("Type");
        typeKolom.setCellValueFactory(artikel -> new SimpleObjectProperty(artikel.getValue().verkrijgType()));

        artikelenTableView.getColumns().addAll(aantalKolom, merkKolom, modelKolom, akoestischKolom, typeKolom);
        artikelen = gitaarDatabase.verkrijgArtikelen();

        vulArtikelenTableView();

        //ITEMS ONDERAAN

        TextField txtArtikelVeranderVoorraad = new TextField();
        txtArtikelVeranderVoorraad.setPromptText("Voorraad");

        CheckBox cbNegatief = new CheckBox("Negatief");

        Button btnToevoegen = new Button("Toevoegen");

        artikelenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artikelAangeklikt = artikelenTableView.getSelectionModel().getSelectedItem();
            }
        });

        btnToevoegen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Integer veranderAantal;
                if (txtArtikelVeranderVoorraad.getText() != "") {
                    try {
                        veranderAantal = Integer.parseInt(txtArtikelVeranderVoorraad.getText());

                        if (artikelAangeklikt != null) {

                            if (cbNegatief.isSelected()) {
                                gitaarDatabase.verlaagVoorraadArtikel(artikelAangeklikt, veranderAantal);
                            } else {
                                gitaarDatabase.verhoogVoorraadArtikel(artikelAangeklikt, veranderAantal);
                            }

                            cbNegatief.setSelected(false);
                            txtArtikelVeranderVoorraad.setText("");
                            artikelAangeklikt = null;
                            vulArtikelenTableView();
                        }
                        else
                        {
                            String st = "Selecteer eerst een artikel.";
                            JOptionPane.showMessageDialog(null, st);
                        }

                    } catch (Exception exception) {
                        String st = "Er is geen geldig aantal ingevoerd.";
                        JOptionPane.showMessageDialog(null, st);
                    }
                } else {
                    String st = "Er is geen aantal ingevoerd.";
                    JOptionPane.showMessageDialog(null, st);
                }
            }
        });


        BorderPane borderPaneArtikelenDisplay = new BorderPane();
        GridPane gridPaneArtikelenTableView = new GridPane();
        gridPaneArtikelenTableView.setHgap(10);
        gridPaneArtikelenTableView.setVgap(10);
        //ArtikelenTableView
        gridPaneArtikelenTableView.add(new Label("Voorraadbeheer"), 0, 0);
        gridPaneArtikelenTableView.add(artikelenTableView, 0, 3);


        GridPane gridPaneArtikelenKnoppen = new GridPane();
        gridPaneArtikelenKnoppen.setHgap(10);
        gridPaneArtikelenKnoppen.setVgap(10);

        gridPaneArtikelenKnoppen.add(txtArtikelVeranderVoorraad, 0, 0);
        gridPaneArtikelenKnoppen.add(cbNegatief, 2, 0);
        gridPaneArtikelenKnoppen.add(btnToevoegen, 4, 0);

        borderPaneArtikelenDisplay.setTop(gridPaneArtikelenTableView);
        borderPaneArtikelenDisplay.setCenter(gridPaneArtikelenKnoppen);


        content.getChildren().addAll(borderPaneArtikelenDisplay);
        content.getChildren().addAll();
        //container.setTop(menu);
        container.setTop(menu);
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        // Show window
        window.showAndWait();

    }

    private void vulArtikelenTableView() {
        ObservableList<Artikel> artikelenLijstObserv = FXCollections.observableArrayList(artikelen);

        for ( int i = 0; i<artikelenTableView.getItems().size(); i++) {
            artikelenTableView.getItems().clear();
        }

        artikelenTableView.setItems(artikelenLijstObserv);
    }
}
