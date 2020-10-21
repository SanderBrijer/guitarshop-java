package nl.inholland.GuitarShop_UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Artikel;
import nl.inholland.GuitarShop_Models.BesteldeItem;
import nl.inholland.GuitarShop_Models.Bestelling;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BevestigBestelling {
    private Bestelling bestelling;
    private final Stage window;
    private GitaarDatabase gitaarDatabase;

    public BevestigBestelling(Bestelling bestelling, GitaarDatabase gitaarDatabase)
    {
        this.gitaarDatabase = gitaarDatabase;
        window = new Stage();
        this.bestelling = bestelling;



        // Set Window properties
        window.setHeight(400);
        window.setWidth(600);
        window.setTitle("Bevestig bestelling");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        BorderPane borderPaneKlantDisplay = new BorderPane();
        GridPane gridKlant = new GridPane();
        gridKlant.setHgap(10);
        gridKlant.setVgap(10);

        //content.setPadding(new Insets(10));
        //KLANT

        Label lblKlantTitel = new Label("Klant");
        Label lblKlantNaam = new Label(bestelling.verkrijgKlant().verkrijgVolledigeNaam());
        Label lblKlantAdres = new Label(bestelling.verkrijgKlant().verkrijgAdres());
        Label lblKlantTelefoonnummer = new Label(bestelling.verkrijgKlant().verkrijgTelefoonnummer());
        Label lblKlantEmail = new Label(bestelling.verkrijgKlant().verkrijgEmail());

        gridKlant.add(lblKlantTitel, 0,0);
        gridKlant.add(lblKlantNaam, 0,2);
        gridKlant.add(lblKlantAdres, 0,4);
        gridKlant.add(lblKlantTelefoonnummer, 0,6);
        gridKlant.add(lblKlantEmail, 0,8);
        borderPaneKlantDisplay.setTop(gridKlant);

        //BESTELDE ITEM

        BorderPane borderPaneBestellingDisplay = new BorderPane();
        GridPane gridPaneBestelTitel = new GridPane();
        gridKlant.setHgap(10);
        gridKlant.setVgap(10);

        Label lblBesteldeItemTitelAantal = new Label("Aantal");
        Label lblBesteldeItemTitelMerk = new Label("Merk");
        Label lblBesteldeItemTitelModel = new Label("Model");
        Label lblBesteldeItemTitelType = new Label("Type");
        Label lblBesteldeItemTitelPrijs = new Label("Prijs");

        gridPaneBestelTitel.add(lblBesteldeItemTitelAantal, 0,0);
        gridPaneBestelTitel.add(lblBesteldeItemTitelMerk, 3,0);
        gridPaneBestelTitel.add(lblBesteldeItemTitelModel, 6,0);
        gridPaneBestelTitel.add(lblBesteldeItemTitelType, 9,0);
        gridPaneBestelTitel.add(lblBesteldeItemTitelPrijs, 12,0);
        borderPaneBestellingDisplay.setTop(gridKlant);


        GridPane gridPaneBesteldeItemTitel = new GridPane();
        gridKlant.setHgap(10);
        gridKlant.setVgap(10);

        Double totalePrijs = 0.0;
        List<List<Label>> listlabels = new ArrayList<>();
        List<Label> listLabelsBestellingen;
        List<BesteldeItem> besteldeItems = bestelling.verkrijgBesteldeItems();
        for (int i = 0; i < besteldeItems.size(); i++)
        {

            Label lblBesteldeItemTitelAantalUitvoer = new Label(besteldeItems.get(i).verkrijgAantalBesteld().toString());
            Label lblBesteldeItemTitelMerkUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgMerk());
            Label lblBesteldeItemTitelModelUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgModel());
            Label lblBesteldeItemTitelTypeUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgType().toString());
            String prijs = String.valueOf(besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs()*Double.parseDouble(besteldeItems.get(i).verkrijgAantalBesteld().toString()));
            Label lblBesteldeItemTitelPrijsUitvoer = new Label(prijs);

            gridPaneBestelTitel.add(lblBesteldeItemTitelAantalUitvoer, 0,i+1);
            gridPaneBestelTitel.add(lblBesteldeItemTitelMerkUitvoer, 3,i+1);
            gridPaneBestelTitel.add(lblBesteldeItemTitelModelUitvoer, 6,i+1);
            gridPaneBestelTitel.add(lblBesteldeItemTitelTypeUitvoer, 9,i+1);
            gridPaneBestelTitel.add(lblBesteldeItemTitelPrijsUitvoer, 12,i+1);



            listLabelsBestellingen = new ArrayList<Label>();
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgAantalBesteld().toString()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgMerk()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgModel()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgType().toString()));
            String prijs2 = String.valueOf(besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs());
            listLabelsBestellingen.add(new Label(prijs2));
            listlabels.add(listLabelsBestellingen);

            Double aantalBesteld = Double.valueOf(besteldeItems.get(i).verkrijgAantalBesteld());
            Double prijsPerBesteldeItem = Double.valueOf(besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs());
            totalePrijs += aantalBesteld*prijsPerBesteldeItem;
        }

        borderPaneBestellingDisplay.setCenter(gridPaneBestelTitel);


        //ONDER
        GridPane gridPaneOnder = new GridPane();
        gridKlant.setHgap(10);
        gridKlant.setVgap(10);



        Label lblTotalePrijs = new Label("Totale prijs: " + totalePrijs);
        gridPaneOnder.add(lblTotalePrijs, 0,0);

        Button btnBevestigBestelling = new Button("Bevestig");
        gridPaneOnder.add(btnBevestigBestelling, 0,2);

        content.getChildren().addAll(borderPaneKlantDisplay, gridPaneBestelTitel, gridPaneOnder);


        container.setCenter(content);


        btnBevestigBestelling.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gitaarDatabase.voegToeAanAfgerondeBestellingen(bestelling);
                window.close();
            }
        });

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);

        window.initModality(Modality.APPLICATION_MODAL);

        // Show window
        window.showAndWait();
    }

}
