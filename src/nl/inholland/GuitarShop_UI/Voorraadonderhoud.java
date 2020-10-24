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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.AfgerondeBestelling;
import nl.inholland.GuitarShop_Models.Artikel;
import nl.inholland.GuitarShop_Models.Gebruiker;
import nl.inholland.GuitarShop_Models.TypeGitaar;
import nl.inholland.GuitarShop_Service.MenuBarMaker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Voorraadonderhoud {
    private List<Artikel> artikelen;
    private GitaarDatabase gitaarDatabase;
    private Artikel artikelAangeklikt;
    private Gebruiker ingelogdeGebruiker;
    private final Stage window;
    private TableView<Artikel> artikelenTableView;

    public Voorraadonderhoud(Gebruiker ingelogdeGebruiker, GitaarDatabase gitaarDatabase) {
        this.gitaarDatabase = gitaarDatabase;
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        artikelenTableView = new TableView<>();
        artikelen = new ArrayList<>();
        window = new Stage();


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


        artikelenTableViewMaker();
        vulArtikelenTableView();
        artikelenTableViewAction();

        //ITEMS ONDERAAN

        TextField txtArtikelVeranderVoorraad = new TextField();
        txtArtikelVeranderVoorraad.setPromptText("Voorraad");

        CheckBox cbNegatief = new CheckBox("Negatief");

        Button btnToevoegen = new Button("Toevoegen");

        btnToevoegenAction(btnToevoegen, txtArtikelVeranderVoorraad, cbNegatief);


        BorderPane borderPaneArtikelenDisplay = new BorderPane();
        GridPane gridPaneArtikelenTableView = new GridPane();
        gridPaneArtikelenTableView.setHgap(10);
        gridPaneArtikelenTableView.setVgap(10);
        //ArtikelenTableView
        Label labelVoorraad = new Label("Voorraadbeheer");
        labelVoorraad.setFont(new Font(30));
        gridPaneArtikelenTableView.add(labelVoorraad, 0, 0);
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
        MenuBarMaker menuBarMaker = new MenuBarMaker(ingelogdeGebruiker, window, gitaarDatabase);
        container.setTop(menuBarMaker.verkrijgMenu());
        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);

        windowMaker(scene);
    }

    private void windowMaker(Scene scene)
    {
        //WINDOW info
        window.setHeight(800);
        window.setWidth(1024);
        window.setTitle("Voorraadbeheer");

        //SCENE
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);

        window.showAndWait();
    }


    private void vulArtikelenTableView() {
        artikelen = gitaarDatabase.verkrijgArtikelen();
        ObservableList<Artikel> artikelenLijstObserv = FXCollections.observableArrayList(artikelen);

        for ( int i = 0; i<artikelenTableView.getItems().size(); i++) {
            artikelenTableView.getItems().clear();
        }

        artikelenTableView.setItems(artikelenLijstObserv);
    }

    private void artikelenTableViewMaker()
    {
        artikelenTableView = new TableView<>();
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
    }

    private void btnToevoegenAction(Button btnToevoegen, TextField txtArtikelVeranderVoorraad, CheckBox cbNegatief)
    {
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
                                if (artikelAangeklikt.verkrijgAantal() < 0)
                                {
                                    String st = "LET OP!\nEr is een negatief aantal in de database van het artikel " + artikelAangeklikt.verkrijgModel() + ".";
                                    JOptionPane.showMessageDialog(null, st);
                                }
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
    }

    private void artikelenTableViewAction()
    {
        artikelenTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    artikelAangeklikt = artikelenTableView.getSelectionModel().getSelectedItem();
                }
                catch(Exception exception){}
            }
        });
    }

}
