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
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label userLabel = new Label("Username: ");
        GridPane.setConstraints(userLabel, 0, 0);

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 1);

        TextField userInput = new TextField();
        userInput.setPromptText("Username: ");
        GridPane.setConstraints(userInput, 1, 0);

        TextField passwordInput = new PasswordField();
        userInput.setPromptText("Password: ");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 1, 2);

        StringProperty passwordFieldProperty = passwordInput.textProperty();

        Label visiblePass = new Label();
        GridPane.setConstraints(visiblePass, 0, 3);
        visiblePass.textProperty().bind(passwordFieldProperty);

        grid.getChildren().addAll(userLabel, userInput, passwordLabel,
                passwordInput, loginButton, visiblePass);

        loginButton.setVisible(true);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String wachtwoord = passwordInput.getText();
                String gebruikersnaam =userInput.getText();
                Gebruiker gebruiker = gitaarDatabase.checkInlog(gebruikersnaam, wachtwoord);

                if (gebruiker != null)
                {
                    new Dashboard(gebruiker);
                    window.close();
                }
            }
        });

        Scene scene = new Scene(grid);
        window.setScene(scene);

        window.show();
    }

    public void Dashboard(Gebruiker user) {
        window = new Stage();


    }
}
