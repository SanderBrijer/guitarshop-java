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
import javafx.scene.text.Font;
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
        window.setHeight(600);
        window.setWidth(800);
        window.setTitle("Bevestig bestelling");

        // Set containers
        BorderPane container = new BorderPane();
        VBox content = new VBox(10);
        HBox buttons = new HBox(10);

        BorderPane borderPaneKlantDisplay = new BorderPane();
        GridPane gridKlant = new GridPane();
        gridKlant.setHgap(5);
        gridKlant.setVgap(5);

        //content.setPadding(new Insets(10));
        //KLANT
        Label lblTitelScherm = new Label("Bevestig de bestelling");
        lblTitelScherm.setFont(new Font(30));
        Label lblKlantTitel = new Label("Klant");
        lblKlantTitel.setFont(new Font(20));
        Label lblKlantNaam = new Label(bestelling.verkrijgKlant().verkrijgVolledigeNaam());
        Label lblKlantAdres = new Label(bestelling.verkrijgKlant().verkrijgAdres());
        Label lblKlantTelefoonnummer = new Label(bestelling.verkrijgKlant().verkrijgTelefoonnummer());
        Label lblKlantEmail = new Label(bestelling.verkrijgKlant().verkrijgEmail());


        gridKlant.add(lblTitelScherm, 1,0);
        gridKlant.add(lblKlantTitel, 1,2);
        gridKlant.add(lblKlantNaam, 1,3);
        gridKlant.add(lblKlantAdres, 1,4);
        gridKlant.add(lblKlantTelefoonnummer, 1,5);
        gridKlant.add(lblKlantEmail, 1,6);
        borderPaneKlantDisplay.setTop(gridKlant);

        //BESTELDE ITEM

        BorderPane borderPaneBestellingDisplay = new BorderPane();
        GridPane gridPaneBestelTitel = new GridPane();
        gridPaneBestelTitel.setHgap(10);
        gridPaneBestelTitel.setVgap(10);

        Label lblBestellingTitel = new Label("Bestelling");
        lblBestellingTitel.setFont(new Font(20));
        Label lblBesteldeItemTitelAantal = new Label("Aantal");
        Label lblBesteldeItemTitelMerk = new Label("Merk");
        Label lblBesteldeItemTitelModel = new Label("Model");
        Label lblBesteldeItemTitelType = new Label("Type");
        Label lblBesteldeItemTitelPrijs = new Label("Prijs");


        /*gridPaneBestelTitel.add(lblBestellingTitel, 1,0);
        gridPaneBestelTitel.add(lblBesteldeItemTitelAantal, 1,1);
        gridPaneBestelTitel.add(lblBesteldeItemTitelMerk, 9,1);
        gridPaneBestelTitel.add(lblBesteldeItemTitelModel, 17,1);
        gridPaneBestelTitel.add(lblBesteldeItemTitelType, 25,1);
        gridPaneBestelTitel.add(lblBesteldeItemTitelPrijs, 33,1);
        borderPaneBestellingDisplay.setTop(gridPaneBestelTitel);*/


        GridPane gridPaneBesteldeItemTitel = new GridPane();
        gridPaneBesteldeItemTitel.setHgap(10);
        gridPaneBesteldeItemTitel.setVgap(10);

        Double totalePrijs = 0.0;
        List<List<Label>> listlabels = new ArrayList<>();
        List<Label> listLabelsBestellingen;
        List<BesteldeItem> besteldeItems = bestelling.verkrijgBesteldeItems();

        GridPane gridPaneBestelInfo = new GridPane();
        gridPaneBestelInfo.setHgap(10);
        gridPaneBestelInfo.setVgap(10);

        gridPaneBestelInfo.add(lblBestellingTitel, 1,0);
        gridPaneBestelInfo.add(lblBesteldeItemTitelAantal, 1,1);
        gridPaneBestelInfo.add(lblBesteldeItemTitelMerk, 9,1);
        gridPaneBestelInfo.add(lblBesteldeItemTitelModel, 17,1);
        gridPaneBestelInfo.add(lblBesteldeItemTitelType, 25,1);
        gridPaneBestelInfo.add(lblBesteldeItemTitelPrijs, 33,1);

        for (int i = 0; i < besteldeItems.size(); i++)
        {

            Label lblBesteldeItemTitelAantalUitvoer = new Label(besteldeItems.get(i).verkrijgAantalBesteld().toString());
            Label lblBesteldeItemTitelMerkUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgMerk());
            Label lblBesteldeItemTitelModelUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgModel());
            Label lblBesteldeItemTitelTypeUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgType().toString());
            Double prijsje = besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs()*Double.parseDouble(besteldeItems.get(i).verkrijgAantalBesteld().toString());
            String prijs = String.format("%.2f", prijsje);
            Label lblBesteldeItemTitelPrijsUitvoer = new Label(prijs);

            gridPaneBestelInfo.add(lblBesteldeItemTitelAantalUitvoer, 1,i+2);
            gridPaneBestelInfo.add(lblBesteldeItemTitelMerkUitvoer, 9,i+2);
            gridPaneBestelInfo.add(lblBesteldeItemTitelModelUitvoer, 17,i+2);
            gridPaneBestelInfo.add(lblBesteldeItemTitelTypeUitvoer, 25,i+2);
            gridPaneBestelInfo.add(lblBesteldeItemTitelPrijsUitvoer, 33,i+2);



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

        borderPaneBestellingDisplay.setBottom(gridPaneBestelInfo);


        //ONDER
        GridPane gridPaneOnder = new GridPane();
        gridPaneOnder.setHgap(10);
        gridPaneOnder.setVgap(10);


        String totalePrijsToString = String.format("%.2f", totalePrijs);
        Label lblTotalePrijs = new Label("Totale prijs: € " + totalePrijsToString);
        gridPaneOnder.add(lblTotalePrijs, 1,0);

        Button btnBevestigBestelling = new Button("Bevestig");
        gridPaneOnder.add(btnBevestigBestelling, 1,2);

        content.getChildren().addAll(borderPaneKlantDisplay, borderPaneBestellingDisplay, gridPaneOnder);


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
