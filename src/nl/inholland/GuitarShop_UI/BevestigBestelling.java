package nl.inholland.GuitarShop_UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_Models.BesteldeItem;
import nl.inholland.GuitarShop_Models.Bestelling;
import java.util.ArrayList;
import java.util.List;

public class BevestigBestelling {
    private Bestelling bestelling;
    private final Stage window;

    public BevestigBestelling(Bestelling bestelling)
    {
        window = new Stage();
        window.setTitle("Klanten opzoeken");
        this.bestelling = bestelling;



        // Set Window properties
        window.setHeight(400);
        window.setWidth(600);
        window.setTitle("Selecteer een klant");

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
        gridPaneBestelTitel.add(lblBesteldeItemTitelMerk, 0,2);
        gridPaneBestelTitel.add(lblBesteldeItemTitelModel, 0,4);
        gridPaneBestelTitel.add(lblBesteldeItemTitelType, 0,6);
        gridPaneBestelTitel.add(lblBesteldeItemTitelPrijs, 0,8);
        borderPaneBestellingDisplay.setTop(gridKlant);


        GridPane gridPaneBesteldeItemTitel = new GridPane();
        gridKlant.setHgap(10);
        gridKlant.setVgap(10);

        Double totalePrijs = 0.0;
        List<Label> listlabels = new ArrayList<Label>();
        List<Label> listLabelsBestellingen;
        List<BesteldeItem> besteldeItems = bestelling.verkrijgBesteldeItems();
        for (int i = 0; i < besteldeItems.size(); i++)
        {

            Label lblBesteldeItemTitelAantalUitvoer = new Label(besteldeItems.get(i).verkrijgAantalBesteld().toString());
            Label lblBesteldeItemTitelMerkUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgMerk());
            Label lblBesteldeItemTitelModelUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgModel());
            Label lblBesteldeItemTitelTypeUitvoer = new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgType().toString());
            String prijs = String.valueOf(besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs());
            Label lblBesteldeItemTitelPrijsUitvoer = new Label(prijs);

            gridPaneBestelTitel.add(lblBesteldeItemTitelAantalUitvoer, i+1,0);
            gridPaneBestelTitel.add(lblBesteldeItemTitelMerkUitvoer, i+1,2);
            gridPaneBestelTitel.add(lblBesteldeItemTitelModelUitvoer, i+1,4);
            gridPaneBestelTitel.add(lblBesteldeItemTitelTypeUitvoer, i+1,6);
            gridPaneBestelTitel.add(lblBesteldeItemTitelPrijsUitvoer, i+1,8);



            listLabelsBestellingen = new ArrayList<Label>();
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgAantalBesteld().toString()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgMerk()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgModel()));
            listLabelsBestellingen.add(new Label(besteldeItems.get(i).verkrijgArtikel().verkrijgType().toString()));
            String prijs2 = String.valueOf(besteldeItems.get(i).verkrijgArtikel().verkrijgPrijs());
            listLabelsBestellingen.add(new Label(prijs2));
            listlabels.add((Label) listLabelsBestellingen);

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
        gridPaneOnder.add(btnBevestigBestelling, 2,0);

        content.getChildren().addAll(borderPaneKlantDisplay, gridPaneBestelTitel, gridPaneOnder);


        container.setCenter(content);

        // Set scene
        Scene scene = new Scene(container);
        window.setScene(scene);

        // Show window
        window.showAndWait();
    }

}
