package nl.inholland.GuitarShop_UI;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.*;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    Stage window;

    @Override
    public void start(Stage window) {
        GitaarDatabase gitaarDatabase = new GitaarDatabase();
        new LoginScherm(gitaarDatabase);

    }
/*
    public void Dashboard(Gebruiker user) {
        window = new Stage();


    }*/
}
